package sevenisles.game;

import java.util.List; 
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface GameRepository extends CrudRepository<Game, Integer>{

	@Query("SELECT game FROM Game game WHERE game.endHour IS NOT NULL")
	List<Game> findFinishedGames();
	
	@Query("SELECT game FROM Game game WHERE game.endHour IS NULL")
	List<Game> findUnfinishedGames();
	
	@Query("SELECT game FROM Game game WHERE game.code = ?1")
	Optional<Game> findByCode(String code);
	
	/*@Query("SELECT player.id FROM Game g1 NATURAL JOIN games_players g2 WHERE g2 = ?1")
	List<Integer> findPlayersIdByGameId(Integer id);*/
	
}
