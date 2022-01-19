package sevenisles.achievement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sevenisles.player.Player;

public interface AchievementRepository extends CrudRepository<Achievement, Integer>{

	//@Query("SELECT a.achieved FROM Achievement a JOIN statistics_achievement sa WHERE a.id = sa.achievement.id JOIN Statistics s WHERE sa.statistics.id = s.id")
	//public boolean checkAchievement(AchievementType at, Player p);

  }
