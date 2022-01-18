package sevenisles.achievement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Iterator;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementServiceTests {
	
	@Autowired
	private AchievementService achievementService;
	
	@Test
	public void testCountWithInitialData() {
		int count = achievementService.achievementCount();
		assertEquals(1,count);
	}
	
	@Test
	public void testFindAll() {
		Iterator<Achievement> achievements = achievementService.achievementFindAll().iterator();
        int i = 1;
        while(achievements.hasNext()) {
            Achievement achievement=achievements.next();
            assertEquals(i, achievement.getId());
            i++;
        }
	}
	
	@Test
	public void testFindById() {
		int id = 1;
		Iterator<Achievement> achievements = achievementService.achievementFindAll().iterator();
		Achievement comp = new Achievement();
		while(achievements.hasNext()) {
			comp=achievements.next();
			if(comp.getId()==id) break;
		}
		Achievement res = achievementService.findAchievementById(id).get();
		assertEquals(comp, res);
	}
	
	@Test
	@Transactional
	public void testSaveAchievement() {
		int id = 1;
		AchievementType at= AchievementType.CALICES_CONSEGUIDOS_10;
		AchievementType actual = achievementService.findAchievementById(id).get().getAchievementType();
		assertNotEquals(at,actual);
		Achievement achievement = achievementService.findAchievementById(id).get();
		achievement.setAchievementType(at);
		assertEquals(at,achievement.getAchievementType());
		achievementService.saveAchievement(achievement);
		achievement = achievementService.findAchievementById(id).get();
		assertEquals(at,achievement.getAchievementType());
	}
	
	@Test
	public void testDeleteAchievement() {
		Achievement achievement = new Achievement();
		achievement.setId(2);
		achievement.setAchievementType(AchievementType.CORONAS_CONSEGUIDAS_10);
		achievement.setAchieved(true);
		
		achievementService.saveAchievement(achievement);
		int countBefore = achievementService.achievementCount();
		
		achievementService.deleteAchievement(achievement);
		int countAfter = achievementService.achievementCount();
		
		assertEquals(countAfter,countBefore-1);
	}
	
}
