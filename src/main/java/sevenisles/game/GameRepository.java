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

	@Query("SELECT game FROM Game game WHERE game.startHour IS NULL")
	List<Game> findNotStartedGames();

	@Query("SELECT game FROM Game game WHERE game.startHour IS NOT NULL AND game.endHour IS NULL")
	List<Game> findStartedGames();
	
	@Query("SELECT g FROM Game g JOIN Status s WHERE s.game = g.id AND s.player.id = ?1 AND g.endHour IS NOT NULL")
	public Optional<List<Game>> findFinishedGamesOfPlayer(int playerId);
	
}
