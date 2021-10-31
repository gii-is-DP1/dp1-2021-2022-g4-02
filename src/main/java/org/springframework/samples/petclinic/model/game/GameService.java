package org.springframework.samples.petclinic.model.game;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {
	@Autowired
	private GameRepository gameRepository;
	
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
	
	@Transactional
	public void saveGame(Game gameToUpdate) throws DataAccessException {
		gameRepository.save(gameToUpdate);
	}
}
