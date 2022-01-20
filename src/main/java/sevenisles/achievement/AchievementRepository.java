package sevenisles.achievement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AchievementRepository extends CrudRepository<Achievement, Integer>{

	@Query("SELECT a FROM Achievement a WHERE a.achievementType = ?1")
	public Achievement getAchievementByType(AchievementType at);

  }
