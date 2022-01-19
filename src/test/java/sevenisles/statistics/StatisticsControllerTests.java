package sevenisles.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import sevenisles.configuration.SecurityConfiguration;
import sevenisles.game.exceptions.GameControllerException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=StatisticsController.class)
@WebMvcTest(value = StatisticsController.class, 
	excludeFilters = @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,
	classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration = SecurityConfiguration.class)
public class StatisticsControllerTests {
	
	private static final Integer TEST_STATISTICS_ID = 1;
	
	@Autowired
	private MockMvc mockMvc; 
	
	@Autowired
	private StatisticsController statisticsController;
	
	@MockBean
	private StatisticsService statisticsService;
	
	@BeforeEach
	public void setup() { 

		Statistics st = new Statistics();
		st.setId(TEST_STATISTICS_ID);
		
		Mockito.when(this.statisticsService.findStatisticsById(TEST_STATISTICS_ID)).thenReturn(Optional.of(st));
	
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
	void statisticsFoundByIdTest() throws Exception{
		mockMvc.perform(get("/statistics/{statisticsId}", TEST_STATISTICS_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("statistics"))
			.andExpect(view().name("statistics/statisticsDetails"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities={"player","admin"})
	void statisticsNotFoundByIdTest() throws Exception{
		Mockito.when(statisticsService.findStatisticsById(TEST_STATISTICS_ID)).thenReturn(Optional.ofNullable(null));
		mockMvc.perform(get("/statistics/{statisticsId}", TEST_STATISTICS_ID))
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
			.andExpect(result -> assertEquals("Estadísticas no encontradas", result.getResolvedException().getMessage()));;
	}
	
	
}
