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

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.island.IslandService;
import sevenisles.player.Player;
import sevenisles.status.Status;
import sevenisles.status.StatusService;

@Service
public class GameService {
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private IslandService islandService;
	
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
	public void nextPlayer(Integer gameId) {
		Optional<Game> gameopt = gameRepository.findById(gameId);
		if(gameopt.isPresent()) {
			Game game = gameopt.get();
			Integer next = (game.getCurrentPlayer()+1)%statusService.countPlayers(gameId);
			game.setCurrentPlayer(next);
		}
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
	public void createGame(Game game, Player player) {
		Status status = new Status();
		statusService.addPlayer(status, game, player);
		statusService.addStatus(status, game);
		statusService.addStatus(status, player);
		game.setCards(cardService.llenarMazo());
		saveGame(game);
	}
	
	@Transactional
	public void startGame(Game game) {
		game.setStartHour(LocalTime.now());
		islandService.rellenoInicialIslas(game);
		cardService.repartoInicial(game);
		game.setCurrentPlayer(ThreadLocalRandom.current().nextInt(0, 4));		
		saveGame(game);
	}
	
	
}
