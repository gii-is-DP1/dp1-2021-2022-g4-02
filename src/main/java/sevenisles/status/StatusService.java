package sevenisles.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sevenisles.card.Card;
import sevenisles.game.Game;
import sevenisles.game.GameService;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;

@Service
public class StatusService {
	
	@Autowired
	StatusRepository statusRepo;
	
	@Autowired
	GameService gameService;
	
	@Autowired
	PlayerService playerService;
	
	@Transactional
	public Integer countPlayers(Integer gameId) {
		return this.statusRepo.countPlayers(gameId);
	}
	
	@Transactional
	public Integer countPlayers(Game game) {
		return this.statusRepo.countPlayers(game.getId());
	}
	
	@Transactional
	public boolean isNotFull(Integer gameId) {
		return countPlayers(gameId)<4;
	}
	
	@Transactional
	public boolean isReadyToStart(Integer gameId) {
		return countPlayers(gameId)>=2 && countPlayers(gameId)<=4;
	}
	
	@Transactional 
	public boolean isInAnotherGame(Player player){
		Optional<List<Status>> status = statusRepo.findStatusOfPlayer(player.getId());
		System.out.println(statusRepo.findStatusOfPlayer(player.getId()));
		if(status.isPresent()) {
			List<Status> ls = status.get();
			return ls.stream()
					.filter(g->g.getGame().getEndHour()==null || g.getGame().getStartHour()==null)
					.findAny().isPresent();
		}else return false;
	}
	
	//Establece el game y player al status
	@Transactional
	public void addGamePlayerToStatus(Status status, Game game, Player player) {
		status.setGame(game);
		status.setPlayer(player);
		saveStatus(status);
	}
	
	//Añade un nuevo status al game
	@Transactional
	public void addStatusToGame(Status status, Game game) {
		List<Status> ls = new ArrayList<Status>();
		if(game.getStatus()!=null) {
			ls.addAll(game.getStatus());
		}
		ls.add(status);
		game.setStatus(ls);
		gameService.saveGame(game);
 	}
	
	//Añade un nuevo status al player
	@Transactional
	public void addStatusToPlayer(Status status, Player player) {
		List<Status> ls = new ArrayList<Status>();
		if(player.getStatus()!=null) {
			ls.addAll(player.getStatus());
		}
		ls.add(status);
		player.setStatus(ls);
		playerService.savePlayer(player);
 	}
	
	@Transactional(readOnly = true)
	public Iterable<Status> statusrFindAll() {
		return statusRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<List<Status>> findStatusByGame(int id) throws IllegalArgumentException { 
		return statusRepo.findStatusByGame(id);
	}
	
	@Transactional(readOnly = true)
	public Optional<Status> findStatusByGameAndPlayer(int gameId, int playerId) throws IllegalArgumentException { 
		return statusRepo.findStatusByGameAndPlayer(gameId, playerId);
	}
	
	@Transactional(readOnly = true)
	public Optional<List<Status>> findStatusByGameAndScore(int gameId, Integer score) throws IllegalArgumentException { 
		return statusRepo.findStatusByGameAndScore(gameId, score);
	}
	
	@Transactional(readOnly = true)
	public Optional<List<Status>> findWinnerStatusByGame(int gameId) throws IllegalArgumentException { 
		return statusRepo.findWinnerStatusByGame(gameId);
	}
	
	@Transactional(readOnly = true)
	public Optional<List<Status>> findStatusOfPlayer(int playerId) throws IllegalArgumentException { 
		return statusRepo.findStatusOfPlayer(playerId);
	}
	
	@Transactional
	public void saveStatus(Status statusToUpdate) throws DataAccessException {
		statusRepo.save(statusToUpdate);
	}
	
	@Transactional
	public void deleteStatus(Integer id) {
		statusRepo.deleteById(id);
	}
	
	@Transactional
	public void deleteStatus(Status status) {
		statusRepo.delete(status);
	}
	
	@Transactional
	public void deleteCardFromHand(Game game, Integer cardId) {
		Optional<Player> playeropt = playerService.findCurrentPlayer();
		if(playeropt.isPresent()) {
			Optional<Status> statusopt = findStatusByGameAndPlayer(game.getId(), playeropt.get().getId());
			if(statusopt.isPresent()) {
				Status status = statusopt.get();
				List<Card> ls = status.getCards();
				status.setCards(ls.stream().filter(c->c.getId()!=cardId).collect(Collectors.toList()));
				Integer diff = status.getNumberOfCardsToPay()-1;
				status.setNumberOfCardsToPay(diff);
				saveStatus(status);
			}		
		}
	}
	
	@Transactional
	public void deleteCardFromHand(Game game, Card card) {
		deleteCardFromHand(game,card.getId());
	}
	
	@Transactional
	public Boolean cardInHand(Status status, Card card) {
		List<Card> hand = status.getCards();
		Optional<Card> cardopt = hand.stream().filter(c->c.getId()==card.getId()).findAny();
		return cardopt.isPresent();
	}
	
}
