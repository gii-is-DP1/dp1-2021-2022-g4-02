package sevenisles.player;

import java.util.Optional; 

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface PlayerRepository extends CrudRepository<Player, Integer>{
	
	@Query("SELECT p FROM Player p WHERE p.user.username = ?1")
	Optional<Player> findCurrentPlayer(String currentUsername) throws DataAccessException;
}
