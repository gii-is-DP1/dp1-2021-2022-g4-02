package org.springframework.samples.petclinic.model.player;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {
	@Autowired
	private PlayerRepository playerRepository;
	
	@Transactional(readOnly = true)
	public Integer player() {
		return (int) playerRepository.count();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Player> playerFindAll() {
		return playerRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Player findPlayerById(int id) throws IllegalArgumentException { 
		return playerRepository.findById(id).get();
	}
	
	@Transactional
	public void savePlayer(Player playerToUpdate) throws DataAccessException {
		playerRepository.save(playerToUpdate);
	}
	
//	@Transactional(readOnly = true)
//	public Player findCurrentUser() throws DataAccessException {
//		User user = playerRepository.findCurrentUser(User.getCurrentUser());
//		Player player = new Player();
//		player.setUser(user);
//		return player;
//	}
}
