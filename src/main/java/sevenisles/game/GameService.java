package sevenisles.game;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.island.IslandService;
import sevenisles.islandStatus.IslandStatus;
import sevenisles.islandStatus.IslandStatusService;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.status.Status;
import sevenisles.status.StatusService;
import sevenisles.user.UserService;
import sevenisles.util.ThrowDice;

@Service
public class GameService {
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private IslandService islandService;
	
	@Autowired
	private IslandStatusService islandStatusService;
	
	@Transactional(readOnly = true)
	public Integer gameCount() {
		return (int) gameRepository.count();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Game> gameFindAll() {
		return gameRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Game findGameById(int id) throws IllegalArgumentException { 
		return gameRepository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public Optional<Game> findGameByCode(String code) throws IllegalArgumentException { 
		return gameRepository.findByCode(code);
	}
	
	@Transactional(readOnly = true)
	public List<Game> findFinishedGames(){ 
		return gameRepository.findFinishedGames();
	}
	
	@Transactional(readOnly = true)
	public List<Game> findUnfinishedGames(){ 
		return gameRepository.findUnfinishedGames();
	}
	
	@Transactional(readOnly = true)
	public List<Game> findNotStartedGames(){ 
		return gameRepository.findNotStartedGames();
	}
	
	@Transactional(readOnly = true)
	public List<Game> findAvailableGames(){ 
		List<Game> ls = gameRepository.findNotStartedGames();
		return ls.stream().filter(g->statusService.countPlayers(g.getId())<4).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<Game> findStartedGames(){ 
		return gameRepository.findStartedGames();
	}
	
	@Transactional
	public void saveGame(Game gameToUpdate) throws DataAccessException {
		gameRepository.save(gameToUpdate);
	}
	
	@Transactional
	public void nextPlayer(Game game) {
		Integer next = (game.getCurrentPlayer()+1)%statusService.countPlayers(game);
		game.setCurrentPlayer(next);
	}
	
	@Transactional
	public void deleteCard(Integer gameId, Integer cardId) {
		Optional<Game> gameopt = gameRepository.findById(gameId);
		if(gameopt.isPresent()) {
			Game game = gameopt.get();
			List<Card> ls = game.getCards();
			game.setCards(ls.stream().filter(c->c.getId()!=cardId).collect(Collectors.toList()));
		}
	}
	
	@Transactional
	public void deleteCard(Game game, Card card) {
		List<Card> ls = game.getCards();
		game.setCards(ls.stream().filter(c->c.getId()!=card.getId()).collect(Collectors.toList()));
	}
	
	@Transactional
	public Boolean loggedUserBelongsToGame(Game game) {
		Optional<Player> loggedPlayer = playerService.findCurrentPlayer();
		if(loggedPlayer.isPresent()) {
			Optional<Status> ls = statusService.findStatusByGameAndPlayer(game.getId(), loggedPlayer.get().getId());
			if(ls.isPresent()) return true;
		}return false;
	}
	
	@Transactional
	public void createGame(Game game, Player player) {
		Status status = new Status();
		statusService.addPlayer(status, game, player);
		statusService.addStatus(status, game);
		statusService.addStatus(status, player);
		game.setCards(cardService.llenarMazo());
		saveGame(game);
	}
	
	@Transactional
	public void enterGame(Game game, Player player) {
		Status status = new Status();
		statusService.addPlayer(status, game, player);
		statusService.addStatus(status, game);
		statusService.addStatus(status, player);
		saveGame(game);
	}
	
	@Transactional
	public void utilAttributes(Game game, ModelMap model){
		Integer pn = game.getCurrentPlayer();
		Status status = game.getStatus().get(pn);
		Integer playerUserId = status.getPlayer().getUser().getId();
		Integer loggedUserId = userService.findCurrentUser().get().getId();
		model.addAttribute("currentPlayerStatus", status);
		model.addAttribute("playerUserId", playerUserId);
		model.addAttribute("loggedUserId", loggedUserId);
		System.out.println(playerUserId);
		System.out.println(loggedUserId);
	}
	
	@Transactional
	public void startGame(Game game) {
		game.setStartHour(LocalTime.now());
		game.setCurrentTurn(1);
		islandService.rellenoInicialIslas(game);
		cardService.repartoInicial(game);
		Integer playersnumber = statusService.countPlayers(game.getId());
		game.setCurrentPlayer(ThreadLocalRandom.current().nextInt(0, playersnumber));
		game.setInitialPlayer(game.getCurrentPlayer());
		maxTurns(game);
		saveGame(game);
	}
	
	private void maxTurns (Game game) {
		Integer playerNumber = statusService.countPlayers(game.getId());
		Integer cardNumber = cardService.cardCount();
		Integer maxTurns = (cardNumber-(playerNumber*3)-islandService.islandCount())/playerNumber;
		game.setMaxTurns(maxTurns);
	}
	
	@Transactional
	public void playerThrowDice(Game game) {
		Integer number = ThrowDice.throwDice(6);
		List<Status> status = game.getStatus();
		Status playerstatus = status.get(game.getCurrentPlayer());
		playerstatus.setDiceNumber(number);
		//status.set(game.getCurrentPlayer(), playerstatus);
		statusService.saveStatus(playerstatus);
		//game.setStatus(status);
		//saveGame(game);
	}
	
	@Transactional
	public void nextTurn(Game game) {
		List<Status> status = game.getStatus();
		Status playerstatus = status.get(game.getCurrentPlayer());
		playerstatus.setDiceNumber(null);
//		status.set(game.getCurrentPlayer(), playerstatus);
		statusService.saveStatus(playerstatus);
//		game.setStatus(status);
		nextPlayer(game);
		if(game.getCurrentPlayer()==game.getInitialPlayer()) {
			game.setCurrentTurn(game.getCurrentTurn()+1);
		}
		saveGame(game);
	}
	
	@Transactional
	public void robIsland(Game game, IslandStatus is, Status status) {
			Card card = is.getCard();
			status.getCards().add(card);
			statusService.saveStatus(status);
			is.setCard(null);
			islandStatusService.saveIslandStatus(is);
	}
	
	
}
