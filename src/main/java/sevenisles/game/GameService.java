package sevenisles.game;

import java.util.List; 
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sevenisles.status.StatusService;

@Service
public class GameService {
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private StatusService statusService;
	
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

}
