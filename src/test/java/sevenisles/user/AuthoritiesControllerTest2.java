package sevenisles.user;



import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.context.WebApplicationContext;

import sevenisles.achievementStatus.AchievementStatusService;
import sevenisles.configuration.SecurityConfiguration;
import sevenisles.game.GameService;
import sevenisles.player.PlayerService;
import sevenisles.statistics.StatisticsService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=AuthoritiesController.class)
@WebMvcTest(value = AuthoritiesController.class, 
	excludeFilters = @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,
	classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration = SecurityConfiguration.class)

public class AuthoritiesControllerTest2 {

	private static final Integer TEST_USER_ID = 1;
	private static final Integer TEST_USERADMIN_ID = 22;
	private static final Integer TEST_USERNOAUTH_ID = 3;
	private static final Integer TEST_AUTH_ID = 1;
	
	@Autowired
	private MockMvc mockMvc; 
	
	@MockBean
	private AuthoritiesService authoritiesService;
	

	@Autowired
	private AuthoritiesController authoritiesController;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private GameService gameService;
	
	@MockBean
	private PlayerService playerService;
	
	@MockBean
	private StatisticsService statsService;
	
	@MockBean
	private AchievementStatusService achievementStatusService;
	
	@Autowired
	private WebApplicationContext context;
	
	@BeforeEach
	public void setup() { 
		//mockMvc = MockMvcBuilders.webAppContextSetup(context) 
			//	.apply(SecurityMockMvcConfigurers.springSecurity()).build();
		
		List<User> lusers = new ArrayList<User>();
		User user = new User();
		user.setId(TEST_USER_ID);
		user.setFirstName("Prueba");
		user.setLastName("Uno");
		user.setPassword("123");
		user.setCreatedDate(LocalDateTime.now());
		lusers.add(user);
		Authorities auth = new Authorities();
		auth.setId(TEST_AUTH_ID);
		auth.setAuthority("player");
		user.setAuthorities(auth);
		
		Mockito.when(this.userService.findUserById(TEST_USER_ID)).thenReturn(Optional.of(user));
		Mockito.when(this.userService.findCurrentUser()).thenReturn(Optional.of(user));
		
		
		User useradmin = new User();
		useradmin.setId(TEST_USERADMIN_ID);
		useradmin.setUsername("useradmin");
		useradmin.setFirstName("AdminPrueba");
		useradmin.setLastName("Uno");
		useradmin.setPassword("123");
		useradmin.setCreatedDate(LocalDateTime.now());
		lusers.add(useradmin);
		
		Authorities authadmin = new Authorities();
		authadmin.setAuthority("admin");
		useradmin.setAuthorities(authadmin);
		
		User userNoAuth = new User();
		userNoAuth.setId(TEST_USERNOAUTH_ID);
		userNoAuth.setUsername("userNoAuth");
		userNoAuth.setFirstName("PruebaAuth");
		userNoAuth.setLastName("Uno");
		userNoAuth.setPassword("123");
		userNoAuth.setCreatedDate(LocalDateTime.now());
		lusers.add(userNoAuth);
		
		Mockito.when(this.userService.findUserById(TEST_USERNOAUTH_ID)).thenReturn(Optional.of(userNoAuth));
		Mockito.when(this.authoritiesService.findAuthByUser(TEST_USER_ID)).thenReturn(Optional.of(auth));
		Mockito.when(this.authoritiesService.findAuthByUser(TEST_USERADMIN_ID)).thenReturn(Optional.of(authadmin));
		Mockito.when(this.userService.findUserById(TEST_USER_ID)).thenReturn(Optional.of(user));
		Mockito.when(this.userService.findUserById(TEST_USERADMIN_ID)).thenReturn(Optional.of(useradmin));
		Mockito.when(this.userService.findCurrentUser()).thenReturn(Optional.of(useradmin));
		
		
		
		
	}
	
	@Test
	@WithMockUser(value="spring", authorities=("admin"))
	void processAuthCreateFormTestError() throws Exception{
		
		mockMvc.perform(post("/admin/authorities/{user}/new",TEST_USER_ID)
				.with(csrf())
				.param("user","1")
				.param("authority", "player"))
				.andExpect(status().isOk())
				.andExpect(view().name("authorities/editAuth"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities=("admin"))
	void processAuthUpdateFormTestError() throws Exception{
		
		mockMvc.perform(post("/admin/authorities/{user}/edit",TEST_USER_ID)
				.with(csrf())
				.param("user","1")
				.param("authority", "player"))
				.andExpect(status().isOk())
				.andExpect(view().name("authorities/editAuth"));
	}
	
	
	
	
}
