package sevenisles.statistics;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface StatisticsRepository extends CrudRepository<Statistics, Integer>{

	@Query("SELECT st FROM Statistics st WHERE st.player.id = :playerId")
    Statistics getStatsByPlayer(Integer playerId);
}
