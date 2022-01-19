package sevenisles.player;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import sevenisles.configuration.SecurityConfiguration;
import sevenisles.game.GameService;
import sevenisles.player.PlayerService;
import sevenisles.user.Authorities;
import sevenisles.user.User;
import sevenisles.user.UserController;
import sevenisles.user.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=PlayerController.class)
@WebMvcTest(value = PlayerController.class, 
	excludeFilters = @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,
	classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration = SecurityConfiguration.class)
public class PlayerControllerTest {
	
	private static final Integer TEST_USER_ID = 1;
	private static final Integer TEST_PLAYER_ID = 1;
	
	@Autowired
	private MockMvc mockMvc; 
	
	@Autowired
	private PlayerController playerController;
	
	@Autowired
	private WebApplicationContext context;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private PlayerService playerService;
	
	@BeforeEach
	public void setup() { 

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
	
		
		Player player = new Player();
		player.setId(TEST_PLAYER_ID);
		player.setUser(user);
		Mockito.when(this.playerService.findPlayerById(TEST_PLAYER_ID)).thenReturn(Optional.of(player));
	
	}
	

	@Test
	@WithMockUser(value="spring", authorities=("player"))
	void playerListTest() throws Exception{
		mockMvc.perform(get("/players")).andExpect(status().isOk())
			.andExpect(model().attributeExists("players"))
			.andExpect(view().name("players/playersList"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities=("player"))
	void playerListByIdTest() throws Exception{
		mockMvc.perform(get("/players/{playerId}",TEST_PLAYER_ID)).andExpect(status().isOk())
			.andExpect(model().attributeExists("player"))
			.andExpect(view().name("player/playerDetails"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities=("player"))
	void playerListByIdSecondTest() throws Exception{
		mockMvc.perform(get("/players/{playerId}",90)).andExpect(status().isOk())
			.andExpect(model().attributeExists("message"))
			.andExpect(view().name("player/playerDetails"));
	}

}
