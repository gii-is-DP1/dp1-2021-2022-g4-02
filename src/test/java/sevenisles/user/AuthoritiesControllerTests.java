package sevenisles.user;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sevenisles.configuration.SecurityConfiguration;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/jetty-web.xml"})
@WebMvcTest(controllers = AuthoritiesController.class, excludeFilters = @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,
classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class AuthoritiesControllerTests {

	private static final Integer TEST_USER_ID = 1;

	@Autowired
	private MockMvc mockMvc; 
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	
	@MockBean
	private AuthoritiesController authoritiesController;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private WebApplicationContext context;
	
	@BeforeEach
	public void setup() { 
		mockMvc = MockMvcBuilders.webAppContextSetup(context) 
				.apply(SecurityMockMvcConfigurers.springSecurity()).build();

		User user = new User();
		user.setId(TEST_USER_ID);
		user.setFirstName("Prueba");
		user.setLastName("Uno");
		user.setPassword("123");
		user.setCreatedDate(LocalDateTime.now());
		
		Authorities auth = new Authorities();
		auth.setAuthority("player");
		user.setAuthorities(auth);
		
		when(this.userService.findUserById(TEST_USER_ID)).thenReturn(Optional.of(user));
	}
	
	@Test
	@WithMockUser(value="spring")
	void gamesFinishedTest() throws Exception{
		mockMvc.perform(get("/games/finished")).andExpect(status().isOk()).andExpect(model().attributeDoesNotExist("attr")).andExpect(model().attributeExists("game"))
				.andExpect(view().name("games/gamesList"));
		
	}
	
	@Test
	@WithMockUser(value="spring")
	void gamesUnfinishedTest() throws Exception{
		mockMvc.perform(get("/games/unfinished")).andExpect(status().isOk()).andExpect(model().attributeDoesNotExist("attr")).andExpect(model().attributeExists("games"))
				.andExpect(view().name("games/gamesList"));
		
	}
	
	
}
