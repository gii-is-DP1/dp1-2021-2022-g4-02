package sevenisles.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	public void addPlayer(Status status, Game game, Player player) {
		status.setGame(game);
		status.setPlayer(player);
		saveStatus(status);
	}
	
	@Transactional
	public void addStatus(Status status, Game game) {
		List<Status> ls = new ArrayList<Status>();
		if(game.getStatus()!=null) {
			ls.addAll(game.getStatus());
		}
		ls.add(status);
		game.setStatus(ls);
 	}
	
	@Transactional(readOnly = true)
	public Iterable<Status> playerFindAll() {
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

}
