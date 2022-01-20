package sevenisles.achievementStatus;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import sevenisles.achievement.Achievement;
import sevenisles.achievement.AchievementService;
import sevenisles.achievement.AchievementType;
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
		player.setId(1);
		player.setUser(user);
		this.playerService.savePlayer(player);
		
		Authorities auth = new Authorities();
        auth.setAuthority("player");
        auth.setUser(user);
        authoritiesService.saveAuthorities(auth);
		achievementStatusService.asignacionInicialDeLogros(statistics);
		
	}
	
	@Test
	public void testFindAchievementStatusByStatsAndAchievement() {
		Optional<Achievement> a = achievementService.findAchievementById(1);
		if(a.isPresent()) {
			AchievementStatus as = achievementStatusService.findAchievementStatusByStatsAndAchievement(statistics, a.get());
			assertFalse(as.getAchieved());
		}
	}
	
	@Test
	public void testFindAchievementStatusByStats() {
		List<AchievementStatus> as= achievementStatusService.findAchievementStatusByStats(statistics);
		Integer n = achievementService.achievementCount();
		assertTrue(as.size() == n);
	}
	
	@Test
	public void testPlayedGames0() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setGamesPlayed(0);
		statisticsService.saveStatistic(s);
		achievementStatusService.PlayedGamesAchievement(player);
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PARTIDAS_JUGADAS_1)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PARTIDAS_JUGADAS_5)).getAchieved());
	}
	
	@Test
	public void testPlayedGames1Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setGamesPlayed(1);
		statisticsService.saveStatistic(s);
		achievementStatusService.PlayedGamesAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PARTIDAS_JUGADAS_1)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PARTIDAS_JUGADAS_5)).getAchieved());
	}
	
	@Test
	public void testPlayedGames5Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setGamesPlayed(5);
		statisticsService.saveStatistic(s);
		achievementStatusService.PlayedGamesAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PARTIDAS_JUGADAS_5)).getAchieved());
	}
	
	@Test
	public void testWonGames0() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setGamesWon(0);
		statisticsService.saveStatistic(s);
		achievementStatusService.WonGamesAchievement(player);;
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PARTIDAS_GANADAS_1)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PARTIDAS_GANADAS_5)).getAchieved());

	}
	
	@Test
	public void testWonGames1Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setGamesWon(1);
		statisticsService.saveStatistic(s);
		achievementStatusService.WonGamesAchievement(player);;
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PARTIDAS_GANADAS_1)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PARTIDAS_GANADAS_5)).getAchieved());

	}
	
	@Test
	public void testWonGames5Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setGamesWon(5);;
		statisticsService.saveStatistic(s);
		achievementStatusService.WonGamesAchievement(player);;
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PARTIDAS_GANADAS_5)).getAchieved());
	}
	
	@Test
	public void testScore20() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		Integer score = 20;
		achievementStatusService.ScoreAchievement(player, score);
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PUNTOS_CONSEGUIDOS_40)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PUNTOS_CONSEGUIDOS_60)).getAchieved());
	}
	
	@Test
	public void testScore40Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		Integer score = 45;
		achievementStatusService.ScoreAchievement(player, score);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PUNTOS_CONSEGUIDOS_40)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PUNTOS_CONSEGUIDOS_60)).getAchieved());

	}
	
	@Test
	public void testScore60Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		Integer score = 60;
		achievementStatusService.ScoreAchievement(player, score);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PUNTOS_CONSEGUIDOS_40)).getAchieved());
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PUNTOS_CONSEGUIDOS_60)).getAchieved());
	}
	
	@Test
	public void testChalice0() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setChaliceCount(0);
		statisticsService.saveStatistic(s);
		achievementStatusService.ChaliceAchievement(player);
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.CALICES_CONSEGUIDOS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.CALICES_CONSEGUIDOS_10)).getAchieved());
	}
	
	@Test
	public void testChalice5Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setChaliceCount(5);
		statisticsService.saveStatistic(s);
		achievementStatusService.ChaliceAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.CALICES_CONSEGUIDOS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.CALICES_CONSEGUIDOS_10)).getAchieved());

	}
	
	@Test
	public void testChalice10Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setChaliceCount(10);
		statisticsService.saveStatistic(s);
		achievementStatusService.ChaliceAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.CALICES_CONSEGUIDOS_10)).getAchieved());
	}
	
	@Test
	public void testRuby0() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setRubyCount(0);
		statisticsService.saveStatistic(s);
		achievementStatusService.RubiAchievement(player);
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.RUBIES_CONSEGUIDOS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.RUBIES_CONSEGUIDOS_10)).getAchieved());
	}
	
	@Test
	public void testRuby5Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setRubyCount(5);
		statisticsService.saveStatistic(s);
		achievementStatusService.RubiAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.RUBIES_CONSEGUIDOS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.RUBIES_CONSEGUIDOS_10)).getAchieved());
	}
	
	@Test
	public void testRuby10Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setRubyCount(10);
		statisticsService.saveStatistic(s);
		achievementStatusService.RubiAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.RUBIES_CONSEGUIDOS_10)).getAchieved());
	}
	
	@Test
	public void testDiamond0() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setDiamondCount(0);
		statisticsService.saveStatistic(s);
		achievementStatusService.DiamondAchievement(player);
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.DIAMANTES_CONSEGUIDOS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.DIAMANTES_CONSEGUIDOS_10)).getAchieved());

	}
	
	@Test
	public void testDiamond5Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setDiamondCount(5);
		statisticsService.saveStatistic(s);
		achievementStatusService.DiamondAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.DIAMANTES_CONSEGUIDOS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.DIAMANTES_CONSEGUIDOS_10)).getAchieved());
	}
	
	@Test
	public void testDiamond10Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setDiamondCount(10);
		statisticsService.saveStatistic(s);
		achievementStatusService.DiamondAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.DIAMANTES_CONSEGUIDOS_10)).getAchieved());
	}
	
	@Test
	public void testNecklace0() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setNecklaceCount(0);
		statisticsService.saveStatistic(s);
		achievementStatusService.NecklaceAchievement(player);
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.COLLARES_CONSEGUIDOS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.COLLARES_CONSEGUIDOS_10)).getAchieved());

	}
	
	@Test
	public void testNecklace5Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setNecklaceCount(5);
		statisticsService.saveStatistic(s);
		achievementStatusService.NecklaceAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.COLLARES_CONSEGUIDOS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.COLLARES_CONSEGUIDOS_10)).getAchieved());

	}
	
	@Test
	public void testNecklace10Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setNecklaceCount(10);
		statisticsService.saveStatistic(s);
		achievementStatusService.NecklaceAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.COLLARES_CONSEGUIDOS_10)).getAchieved());
	}
	
	@Test
	public void testMap0() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setMapCount(0);
		statisticsService.saveStatistic(s);
		achievementStatusService.MapAchievement(player);
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.MAPAS_CONSEGUIDOS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.MAPAS_CONSEGUIDOS_10)).getAchieved());
	}
	
	@Test
	public void testMap5Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setMapCount(5);
		statisticsService.saveStatistic(s);
		achievementStatusService.MapAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.MAPAS_CONSEGUIDOS_5)).getAchieved());
	}
	
	@Test
	public void testMap10Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setMapCount(10);
		statisticsService.saveStatistic(s);
		achievementStatusService.MapAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.MAPAS_CONSEGUIDOS_10)).getAchieved());
	}
	
	@Test
	public void testCrown0() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setCrownCount(0);
		statisticsService.saveStatistic(s);
		achievementStatusService.CrownAchievement(player);
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.CORONAS_CONSEGUIDAS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.CORONAS_CONSEGUIDAS_10)).getAchieved());
	}
	
	@Test
	public void testCrown5Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setCrownCount(5);
		statisticsService.saveStatistic(s);
		achievementStatusService.CrownAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.CORONAS_CONSEGUIDAS_5)).getAchieved());
	}
	
	@Test
	public void testCrown10Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setCrownCount(10);
		statisticsService.saveStatistic(s);
		achievementStatusService.CrownAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.CORONAS_CONSEGUIDAS_10)).getAchieved());
	}
	
	@Test
	public void testGun0() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setGunCount(0);
		statisticsService.saveStatistic(s);
		achievementStatusService.GunAchievement(player);
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PISTOLAS_CONSEGUIDAS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PISTOLAS_CONSEGUIDAS_10)).getAchieved());
	}
	
	@Test
	public void testGun5Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setGunCount(5);
		statisticsService.saveStatistic(s);
		achievementStatusService.GunAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PISTOLAS_CONSEGUIDAS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PISTOLAS_CONSEGUIDAS_10)).getAchieved());
	}
	
	@Test
	public void testGun10Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setGunCount(10);
		statisticsService.saveStatistic(s);
		achievementStatusService.GunAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PISTOLAS_CONSEGUIDAS_10)).getAchieved());
	}
	
	@Test
	public void testSword0() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setSwordCount(0);
		statisticsService.saveStatistic(s);
		achievementStatusService.SwordAchievement(player);
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.ESPADAS_CONSEGUIDAS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.ESPADAS_CONSEGUIDAS_10)).getAchieved());
	}
	
	@Test
	public void testSword5Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setSwordCount(5);
		statisticsService.saveStatistic(s);
		achievementStatusService.SwordAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.ESPADAS_CONSEGUIDAS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.ESPADAS_CONSEGUIDAS_10)).getAchieved());
	}
	
	@Test
	public void testSword10Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setSwordCount(10);
		statisticsService.saveStatistic(s);
		achievementStatusService.SwordAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.ESPADAS_CONSEGUIDAS_10)).getAchieved());
	}
	
	@Test
	public void testRum0() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setRumCount(0);
		statisticsService.saveStatistic(s);
		achievementStatusService.RumAchievement(player);
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.RONES_CONSEGUIDOS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.RONES_CONSEGUIDOS_10)).getAchieved());
	}
	
	@Test
	public void testRum5Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setRumCount(5);
		statisticsService.saveStatistic(s);
		achievementStatusService.RumAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.RONES_CONSEGUIDOS_5)).getAchieved());
		assertFalse(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.RONES_CONSEGUIDOS_10)).getAchieved());
	}
	
	@Test
	public void testRum10Achievement() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		s.setRumCount(10);
		statisticsService.saveStatistic(s);
		achievementStatusService.RumAchievement(player);
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.RONES_CONSEGUIDOS_10)).getAchieved());
	}
	
	@Test
	public void testSetAchievements() {
		Statistics s = statisticsService.getStatsByPlayer(player.getId()).get();
		
		s.setGamesPlayed(5);
		s.setChaliceCount(5);
		s.setRubyCount(10);
		s.setDiamondCount(5);
		s.setNecklaceCount(10);
		s.setMapCount(5);
		s.setCrownCount(10);
		s.setGunCount(5);
		s.setSwordCount(10);
		s.setRumCount(5);
		
		statisticsService.saveStatistic(s);
		achievementStatusService.setAchievements(player);
		
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PARTIDAS_JUGADAS_5)).getAchieved());
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.CALICES_CONSEGUIDOS_5)).getAchieved());
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.RUBIES_CONSEGUIDOS_10)).getAchieved());
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.DIAMANTES_CONSEGUIDOS_5)).getAchieved());
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.COLLARES_CONSEGUIDOS_10)).getAchieved());
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.MAPAS_CONSEGUIDOS_5)).getAchieved());
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.CORONAS_CONSEGUIDAS_10)).getAchieved());
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.PISTOLAS_CONSEGUIDAS_5)).getAchieved());
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.ESPADAS_CONSEGUIDAS_10)).getAchieved());
		assertTrue(achievementStatusService.findAchievementStatusByStatsAndAchievement(s, achievementService.getAchievementByType(AchievementType.RONES_CONSEGUIDOS_5)).getAchieved());
		
	}
	
}
