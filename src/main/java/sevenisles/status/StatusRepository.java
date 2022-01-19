package sevenisles.status;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import sevenisles.game.Game;

public interface StatusRepository extends CrudRepository<Status, Integer>{
	
	@Query("SELECT COUNT(s) FROM Status s where s.game.id = ?1")
	public Integer countPlayers(Integer game_id);

	@Query("SELECT s FROM Status s where s.game.id = ?1")
	public Optional<List<Status>> findStatusByGame(int id);

	@Query("SELECT s FROM Status s WHERE s.game.id = ?1 AND s.player.id=?2")
	public Optional<Status> findStatusByGameAndPlayer(int gameId, int playerId);
	
	@Query("SELECT s FROM Status s WHERE s.game.id = ?1 AND s.score=?2")
	public Optional<List<Status>> findStatusByGameAndScore(int gameId, Integer score);
	
	@Query("SELECT s FROM Status s WHERE s.game.id = ?1 AND s.winner=1")
	public Optional<List<Status>> findWinnerStatusByGame(int gameId);
	
	@Query("SELECT s FROM Status s WHERE s.player.id = ?1")
	public Optional<List<Status>> findStatusOfPlayer(int playerId);
	

}
