package sevenisles.player;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sevenisles.user.User;

@Service
public class PlayerService {
	@Autowired
	private PlayerRepository playerRepository;

	
	@Transactional(readOnly = true)
	public Integer playerCount() {
		return (int) playerRepository.count();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Player> playerFindAll() {
		return playerRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Player> findPlayerById(int id) throws IllegalArgumentException { 
		return playerRepository.findById(id);
	}
	
	@Transactional
	public void savePlayer(Player playerToUpdate) throws DataAccessException {
		playerRepository.save(playerToUpdate);
	}
	
	@Transactional
	public void deletePlayer(Integer id) {
		playerRepository.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Optional<Player> findCurrentPlayer() throws DataAccessException {
		Optional<Player> player = playerRepository.findCurrentPlayer(User.getCurrentUser());
		return player;
	}
}
