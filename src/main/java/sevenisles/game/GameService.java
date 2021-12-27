package sevenisles.game;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
import sevenisles.game.exceptions.GameControllerException;
import sevenisles.island.IslandService;
import sevenisles.islandStatus.IslandStatus;
import sevenisles.islandStatus.IslandStatusService;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.status.Status;
import sevenisles.status.StatusService;
import sevenisles.user.UserService;
import sevenisles.util.ScoreCountImpl;
import sevenisles.util.ThrowDice;

@Service
public class GameService extends ScoreCountImpl{
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
	public Optional<Game> findGameById(int id) throws IllegalArgumentException { 
		return gameRepository.findById(id);
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
	public void deleteCardFromDeck(Integer gameId, Integer cardId) {
		Optional<Game> gameopt = gameRepository.findById(gameId);
		if(gameopt.isPresent()) {
			Game game = gameopt.get();
			List<Card> ls = game.getCards();
			game.setCards(ls.stream().filter(c->c.getId()!=cardId).collect(Collectors.toList()));
		}
	}
	
	@Transactional
	public void deleteCardFromDeck(Game game, Card card) {
		List<Card> ls = game.getCards();
		game.setCards(ls.stream().filter(c->c.getId()!=card.getId()).collect(Collectors.toList()));
	}
	
	@Transactional
	public Boolean loggedUserBelongsToGame(Game game) {
		Optional<Player> loggedPlayer = playerService.findCurrentPlayer();
		if(loggedPlayer.isPresent()) {
			Optional<Status> ls = statusService.findStatusByGameAndPlayer(game.getId(), loggedPlayer.get().getId());
			return ls.isPresent();
		}
		return false;
	}
	
	@Transactional
	public void createGame(Game game, Player player) {
		Status status = new Status();
		statusService.addGamePlayerToStatus(status, game, player);
		statusService.addStatusToGame(status, game);
		statusService.addStatusToPlayer(status, player);
		game.setCards(cardService.llenarMazo());
		saveGame(game);
	}
	
	@Transactional
	public Player enterGameUtil(Game game) throws GameControllerException {
		if(statusService.isNotFull(game.getId())) {
			Optional<Player> optPlayer = playerService.findCurrentPlayer();
			if(optPlayer.isPresent()) {
				return optPlayer.get();
			}else throw new GameControllerException("Necesitas iniciar sesión antes de unirte a una partida.");
		}else throw new GameControllerException("Lo sentimos, esta partida ya está llena");
	}
	
	@Transactional
	public void enterGame(Game game, Player player) {
		Status status = new Status();
		statusService.addGamePlayerToStatus(status, game, player);
		statusService.addStatusToGame(status, game);
		statusService.addStatusToPlayer(status, player);
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
	}
	
	@Transactional
	public void startGame(Game game) {
		game.setStartHour(LocalTime.now());
		game.setCurrentRound(1);
		islandService.asignacionInicialIslas(game);
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
		game.setMaxRounds(maxTurns);
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
		playerstatus.setChosenIsland(null);
//		status.set(game.getCurrentPlayer(), playerstatus);
		statusService.saveStatus(playerstatus);
//		game.setStatus(status);
		nextPlayer(game);
		if(game.getCurrentPlayer()==game.getInitialPlayer()) {
			game.setCurrentRound(game.getCurrentRound()+1);
		}
		game.setFinishedTurn(0);
		saveGame(game);
	}
	
	@Transactional
	public Boolean chooseIslandCondition(Game game, Integer islandId, Status status) throws GameControllerException {
		if(game.getFinishedTurn()==0) {
			if(status.getDiceNumber()!=null) {
				Optional<IslandStatus> opt = islandStatusService.findIslandStatusByGameAndIsland(game.getId(), islandId);
				if(status.getChosenIsland()==null || status.getChosenIsland()==islandId) {
					if(opt.isPresent()) {
    					IslandStatus is = opt.get();
    					if(is.getCard()!=null) {
        						return true;
        				}else {
    						throw new GameControllerException("No puedes saquear una isla vacía");
    					}			
    				}else {
    					throw new GameControllerException("No hay isla.");
            		}
				}else {
					throw new GameControllerException("Ya has elegido saquear la isla " + status.getChosenIsland() + ". No puedes cambiarla.");
				}
			}else {
				throw new GameControllerException("Primero tienes que tirar el dado.");
			}

		}else {
			throw new GameControllerException("Ya has saqueado una isla.");
		}
	}
	
	@Transactional
	public void chooseIsland(Game game, Integer islandId, Status status) throws GameControllerException {
		Integer cardsNumber = status.getCards().size();
		Integer difference = Math.abs(status.getDiceNumber()-islandId);
		if(difference<=cardsNumber) {
			status.setChosenIsland(islandId);
			status.setNumberOfCardsToPay(difference);
			statusService.saveStatus(status);
		}else {
			throw new GameControllerException("No tienes suficientes cartas para pagar el saqueo de esta isla");
		}
	}
		
	//arreglar
	@Transactional
	public void robIsland(Game game, Integer islandId, Status status) throws GameControllerException{
		if(status.getChosenIsland()!=null) {
			if(status.getChosenIsland()==islandId) {
				IslandStatus is = islandStatusService.findIslandStatusByGameAndIsland(game.getId(), islandId).get();
				Card card = is.getCard();
				status.getCards().add(card);
				statusService.saveStatus(status);
				cardService.llenarIsla(game, is);
				game.setFinishedTurn(1);
				saveGame(game);
				status.setNumberOfCardsToPay(null);
				statusService.saveStatus(status);
			}else {
				throw new GameControllerException("Ya has elegido saquear la isla " + status.getChosenIsland() + ". No puedes cambiarla.");
			}
		}else {
			throw new GameControllerException("Primero debes elegir una isla que saquear.");
		}
	}
	
	@Transactional
	public Boolean loggedPlayerCheckTurn(Game game) throws GameControllerException{
		if(loggedUserBelongsToGame(game)) {
			Integer pn = game.getCurrentPlayer();
			Status status = game.getStatus().get(pn);
			if(status.getPlayer().getId()==playerService.findCurrentPlayer().get().getId()) {
				return true;		
			}else {
				throw new GameControllerException("No es tu turno.");
    		}
			
		}else {
			throw new GameControllerException("No perteneces a esta partida.");	
		}
	}

	public void endGame(Game game) {
		game.setEndHour(LocalTime.now());
		saveGame(game);
		Integer max = 0;
		for(int i=0;i<game.getStatus().size();i++) {
			Status s = game.getStatus().get(i);
			Map<String, List<Card>> map = normalGameMode(s);
			System.out.println(map);
			Integer score = countPoints(map);
			s.setScore(score);
			if(score>max) max=score;
			statusService.saveStatus(s);
		}
		List<Status> ls = statusService.findStatusByGameAndScore(game.getId(), max).get();
		if(ls.size()>1) {
			ls = tiebreaker(ls);
		}
		for(Status st:ls) {
			st.setWinner(1);
			statusService.saveStatus(st);
		}
		
	}
	
	public List<Status> orderStatusByScore(List<Status> statuses){
		return statuses.stream().sorted(Comparator.comparing(Status::getScore).reversed()).collect(Collectors.toList());
	}
	
	
}
