package sevenisles.user;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sevenisles.achievementStatus.AchievementStatusService;
import sevenisles.configuration.SecurityConfiguration;
import sevenisles.player.PlayerService;
import sevenisles.statistics.StatisticsService;

//locations={"file:src/main/webapp/WEB-INF/jetty-web.xml"}
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=UserController.class)
@WebMvcTest(value=UserController.class,
		excludeFilters = @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)
public class UserControllerTest {

	private static final Integer TEST_USER_ID = 1;

	@Autowired
	private MockMvc mockMvc; 
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private PlayerService playerService;
	
	@Autowired
	private UserController userController;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	@MockBean
	private StatisticsService statisticsService;
	
	@MockBean
	private AchievementStatusService achievementStatusService;
	
	
	
	@Autowired
	private WebApplicationContext context;
	
	@BeforeEach
	public void setup() { 
		mockMvc = MockMvcBuilders.webAppContextSetup(context) 
				.apply(SecurityMockMvcConfigurers.springSecurity()).build();

		User user = new User();
		user.setId(TEST_USER_ID);
		user.setUsername("spring");
		user.setFirstName("Prueba");
		user.setLastName("Uno");
		user.setPassword("123");
		user.setCreatedDate(LocalDateTime.now());
		
		Authorities auth = new Authorities();
		auth.setAuthority("player");
		user.setAuthorities(auth);
		
		Mockito.when(this.userService.findUserById(TEST_USER_ID)).thenReturn(Optional.of(user));
		Mockito.when(this.userService.findCurrentUser()).thenReturn(Optional.of(user));
	}
	
	
	
	@Test
	@WithMockUser(value="spring", authorities=("player"))
	public void userDetailsTest() throws Exception{
		mockMvc.perform(get("/profile")).andExpect(status().isOk())
				.andExpect(model().attributeExists("user"))
				.andExpect(view().name("users/userDetails"));
	}
	
	@Test
	public void userDetailsTestNoUser() throws Exception{
		mockMvc.perform(get("/profile")).andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser(value="spring", authorities=("player"))
	public void initUpdateForm() throws Exception{
		mockMvc.perform(get("/profile/edit")).andExpect(status().isOk())
				.andExpect(model().attributeExists("user"))
				.andExpect(view().name("users/createOrUpdateUserForm"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities=("player"))
	public void processUpdateForm() throws Exception{
		mockMvc.perform(post("/profile/edit")
						.with(csrf())
						.param("firstName", "Juan")
						.param("lastName", "Palomo")
						.param("username", "Juanito")
						.param("password", "123"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("user"))
				.andExpect(view().name("users/userDetails"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities=("player"))
	public void processUpdateFormError() throws Exception{
		mockMvc.perform(post("/profile/edit")
						.with(csrf())
						.param("firstName", "Ju")
						.param("lastName", "Palomo")
						.param("username", "Juanito")
						.param("password", "123"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("user"))
				.andExpect(view().name("users/createOrUpdateUserForm"));
	}
	
	@Test
	@WithMockUser(value="anonymous")
	public void initCreationUserForm() throws Exception{
		mockMvc.perform(get("/users/new"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("user"))
				.andExpect(view().name("users/createOrUpdateUserForm"));
	}
	
	@Test
	@WithMockUser(value="anonymous")
	public void processCreationUserForm() throws Exception{
		mockMvc.perform(post("/users/new")
						.with(csrf())
						.param("firstName", "Juan")
                        .param("lastName", "Palomo")
                        .param("username", "Juanito")
                        .param("password", "123"))
		.andExpect(status().is(302))
		.andExpect(view().name("redirect:/"));
	}
	
	@Test
	@WithMockUser(value="anonymous")
	public void processCreationUserFormError() throws Exception{
		mockMvc.perform(post("/users/new")
						.with(csrf())
                        .param("lastName", "Palomo")
                        .param("username", "Juanito")
                        .param("password", "123"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("user"))
		.andExpect(view().name("users/createOrUpdateUserForm"));
	}
	
	
	
}
