package sevenisles.statistics;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import sevenisles.achievementStatus.AchievementStatusService;
import sevenisles.configuration.SecurityConfiguration;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.user.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=StatisticsController.class)
@WebMvcTest(value = StatisticsController.class, 
	excludeFilters = @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,
	classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration = SecurityConfiguration.class)
public class StatisticsControllerTests {
	
	private static final Integer TEST_STATISTICS_ID = 1;
	private static final Integer TEST_USER_ID = 1;
	
	@Autowired
	private MockMvc mockMvc; 
	
	@Autowired
	private StatisticsController statisticsController;
	
	@MockBean
	private StatisticsService statisticsService;
	
	@MockBean
	private PlayerService playerService;
	
	@MockBean
	private AchievementStatusService achievementStatusService;
	
	@BeforeEach
	public void setup() { 
		User user = new User();
		user.setId(TEST_USER_ID);
		user.setUsername("spring");
		
		

		Statistics st = new Statistics();
		st.setId(TEST_STATISTICS_ID);
		Player player = new Player();
		player.setId(TEST_USER_ID);
		player.setStatistics(st);
		player.setUser(user);
		user.setPlayer(player);
		
		Mockito.when(this.statisticsService.findStatisticsById(TEST_STATISTICS_ID)).thenReturn(Optional.of(st));
		Mockito.when(playerService.findCurrentPlayer()).thenReturn(Optional.of(player));
		Mockito.when(statisticsService.getStatsByPlayer(TEST_USER_ID)).thenReturn(Optional.of(st));
	}
	
	@Test
	@WithMockUser(value="spring", authorities={"player","admin"})
	void statisticsListTest() throws Exception{
		mockMvc.perform(get("/statistics")).andExpect(status().isOk())
			.andExpect(model().attributeExists("statistics"))
			.andExpect(view().name("statistics/statisticsList"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities={"player","admin"})
	void statisticsRankingTest() throws Exception{
		mockMvc.perform(get("/statistics/ranking")).andExpect(status().isOk())
			.andExpect(model().attributeExists("statistics"))
			.andExpect(view().name("statistics/ranking"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities={"player","admin"})
	void statisticDetailsTest() throws Exception{
		mockMvc.perform(get("/statistics/details"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("statistics","achievements"))
			.andExpect(view().name("statistics/statisticsDetails"));
	}
	
	
}
