package sevenisles.achievementStatus;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import sevenisles.achievement.Achievement;
import sevenisles.statistics.Statistics;

public interface AchievementStatusRepository extends CrudRepository<AchievementStatus, Integer>{
	
	@Query(value="SELECT a FROM AchievementStatus a WHERE a.statistics = ?1 AND a.achievement = ?2")
	public AchievementStatus getAchievementStatusOfStatisticsAndAchievement(Statistics stats, Achievement achievement);
	
	@Query(value="SELECT a FROM AchievementStatus a WHERE a.statistics = ?1")
	public List<AchievementStatus> getAchievementStatusOfStatistics(Statistics stats);

}
