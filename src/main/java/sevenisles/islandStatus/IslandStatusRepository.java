package sevenisles.islandStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface IslandStatusRepository extends CrudRepository<IslandStatus, Integer>{
	
	@Query("SELECT s FROM IslandStatus s where s.game.id = ?1")
	public Optional<List<IslandStatus>> findIslandStatusByGame(int id);

	@Query("SELECT s FROM IslandStatus s WHERE s.game.id = ?1 AND s.island.id=?2")
	public Optional<IslandStatus> findIslandStatusByGameAndIsland(int gameId, int islandId);

}
