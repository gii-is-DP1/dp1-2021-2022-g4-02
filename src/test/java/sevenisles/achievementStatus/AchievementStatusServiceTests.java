package sevenisles.achievementStatus;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import sevenisles.achievement.Achievement;
import sevenisles.achievement.AchievementService;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.statistics.Statistics;
import sevenisles.statistics.StatisticsService;
import sevenisles.user.Authorities;
import sevenisles.user.AuthoritiesService;
import sevenisles.user.User;
import sevenisles.user.UserService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementStatusServiceTests {

	private AchievementStatusService achievementStatusService;

	private AchievementService achievementService;

	private UserService userService;

	private PlayerService playerService;

	private AuthoritiesService authoritiesService;

	private StatisticsService statisticsService;
	
	@Autowired
	public AchievementStatusServiceTests(AchievementStatusService achievementStatusService, AchievementService achievementService,
			UserService userService, PlayerService playerService, AuthoritiesService authoritiesService, StatisticsService statisticsService) {
		this.achievementStatusService = achievementStatusService;
		this.achievementService = achievementService;
		this.userService = userService;
		this.playerService = playerService;
		this.authoritiesService = authoritiesService;
		this.statisticsService =  statisticsService;
	}
	
	Player player = new Player();
	Statistics statistics = new Statistics();
	
	@BeforeEach
	public void init(){
		
		User user = new User();
		user.setFirstName("Prueba");
		user.setUsername("Prueba");
		user.setLastName("Dos");
		user.setPassword("123");
		this.userService.saveUser(user);
		Player player = new Player();
		player.setUser(user);
		this.playerService.savePlayer(player);

		statistics.setPlayer(player);
		achievementStatusService.asignacionInicialDeLogros(statistics);
		this.statisticsService.saveStatistic(statistics);
		
		
		Authorities auth = new Authorities();
        auth.setAuthority("player");
        auth.setUser(user);
        authoritiesService.saveAuthorities(auth);
		achievementStatusService.asignacionInicialDeLogros(statistics);
		
	}
	
	@Test
	public void testFindAchievementStatusByStatsAndAchievement() {
		Optional<Achievement> a = achievementService.findAchievementById(0);
		if(a.isPresent()) {
			AchievementStatus as = achievementStatusService.findAchievementStatusByStatsAndAchievement(statistics, a.get());
			assertFalse(as.getAchieved());
		}
		
	}
}
