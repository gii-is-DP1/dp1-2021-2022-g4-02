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

import sevenisles.configuration.SecurityConfiguration;
import sevenisles.game.GameService;
import sevenisles.player.PlayerService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=AuthoritiesController.class)
@WebMvcTest(value = AuthoritiesController.class, 
	excludeFilters = @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,
	classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration = SecurityConfiguration.class)

public class AuthoritiesControllerTests {

	private static final Integer TEST_USER_ID = 1;
	private static final Integer TEST_USERADMIN_ID = 2;
	private static final Integer TEST_USERNOAUTH_ID = 3;
	
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
	
	@Autowired
	private WebApplicationContext context;
	
	@BeforeEach
	public void setup() { 
		//mockMvc = MockMvcBuilders.webAppContextSetup(context) 
			//	.apply(SecurityMockMvcConfigurers.springSecurity()).build();

		User user = new User();
		user.setId(TEST_USER_ID);
		user.setFirstName("Prueba");
		user.setLastName("Uno");
		user.setPassword("123");
		user.setCreatedDate(LocalDateTime.now());
		
		Authorities auth = new Authorities();
		auth.setAuthority("player");
		user.setAuthorities(auth);
		
		Mockito.when(this.userService.findUserById(TEST_USER_ID)).thenReturn(Optional.of(user));
		Mockito.when(this.userService.findCurrentUser()).thenReturn(Optional.of(user));
		
		
		User useradmin = new User();
		user.setId(TEST_USERADMIN_ID);
		user.setUsername("useradmin");
		user.setFirstName("AdminPrueba");
		user.setLastName("Uno");
		user.setPassword("123");
		user.setCreatedDate(LocalDateTime.now());
		
		Authorities authadmin = new Authorities();
		auth.setAuthority("admin");
		user.setAuthorities(authadmin);
		
		User userNoAuth = new User();
		userNoAuth.setId(TEST_USERNOAUTH_ID);
		userNoAuth.setUsername("userNoAuth");
		userNoAuth.setFirstName("PruebaAuth");
		userNoAuth.setLastName("Uno");
		userNoAuth.setPassword("123");
		userNoAuth.setCreatedDate(LocalDateTime.now());
		
		Mockito.when(this.userService.findUserById(TEST_USERNOAUTH_ID)).thenReturn(Optional.of(userNoAuth));
		Mockito.when(this.userService.findUserById(TEST_USER_ID)).thenReturn(Optional.of(user));
		Mockito.when(this.userService.findUserById(TEST_USERADMIN_ID)).thenReturn(Optional.of(useradmin));
		Mockito.when(this.userService.findCurrentUser()).thenReturn(Optional.of(useradmin));
		//Mockito.when(this.userService.deleteUser(useradmin);
		
	
	}
	
	@Test
	@WithMockUser(value="spring", authorities=("admin"))
	void usersListTest() throws Exception{
		mockMvc.perform(get("/admin/users")).andExpect(status().isOk())
			.andExpect(model().attributeExists("users"))
			.andExpect(view().name("authorities/usersList"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities=("admin"))
	void usersListPageableTest() throws Exception{
		mockMvc.perform(get("/admin/page/users")).andExpect(status().isOk())
			.andExpect(model().attributeExists("users"))
			.andExpect(model().attributeExists("paginas"))
			.andExpect(view().name("authorities/usersList"));
	}
	
	
	@Test
	@WithMockUser(value="spring", authorities=("player"))
	void gamesFinishedTest() throws Exception{
		//System.out.println("AUTHHHHHHHHH"+SecurityContextHolder.getContext().getAuthentication());
		mockMvc.perform(get("/games/finished")).andExpect(status().isOk())
			.andExpect(model().attributeDoesNotExist("attr"))
			.andExpect(model().attributeExists("games"))
			.andExpect(view().name("games/gamesList"));
		
	}
	
	@Test
	void gamesFinishedNoUserTest() throws Exception{
		mockMvc.perform(get("/games/finished")).andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser(value="spring", authorities=("player"))
	void gamesUnfinishedTest() throws Exception{
		mockMvc.perform(get("/games/unfinished")).andExpect(status().isOk())
			.andExpect(model().attributeDoesNotExist("attr"))
			.andExpect(model().attributeExists("games"))
			.andExpect(view().name("games/gamesList"));
		
	}
	
	@Test
	void gamesUnfinishedNoUserTest() throws Exception{
		mockMvc.perform(get("/games/unfinished")).andExpect(status().isUnauthorized());
	}
	
	
	
	@Test
	@WithMockUser(value="spring", authorities=("admin"))
	void initUpdateFormTest() throws Exception{
		mockMvc.perform(get("/admin/{id}/edit",TEST_USER_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("message"))
				.andExpect(view().name("authorities/editUser"));
	}
	
	
	@Test
	@WithMockUser(value="spring", authorities=("admin"))
	void initUpdateFormNoUserTest() throws Exception{
		mockMvc.perform(get("/admin/{id}/edit",20)).andExpect(status().isOk())
		.andExpect(model().attributeExists("message"))
		.andExpect(view().name("error"));

	}
	
	@Test
	@WithMockUser(value="spring", authorities=("player"))
	void processUpdateOtherUserForm() throws Exception{
		mockMvc.perform(post("/admin/{id}/edit",TEST_USER_ID)
						.with(csrf())
						.param("firstName", "Juan")
						.param("lastName", "Palomo")
						.param("username", "Juanito")
						.param("password", "123"))
				.andExpect(status().is3xxRedirection());
	}
	
	 /*NO COGE BIEN EL USERNAME DEL USUARIO ACTUAL Y DEL USER QUE LE PASAMOS
	@Test
	@WithMockUser(username="useradmin", authorities=("admin"))
	void processUpdateOwnForm() throws Exception{
		mockMvc.perform(post("/admin/{id}/edit",TEST_USERADMIN_ID)
						.with(csrf())
						.param("firstName", "Juan")
						.param("lastName", "Palomo")
						.param("username", "username")
						.param("password", "123"))
				.andExpect(status().is3xxRedirection());
	}
	*/
	
	@Test
	@WithMockUser(value="spring", authorities=("admin"))
	void processDeleteUserTest() throws Exception{
		mockMvc.perform(get("/admin/users/{id}/delete",TEST_USER_ID)).andExpect(status().is3xxRedirection());
	    	
	}
	
	@Test
	@WithMockUser(value="spring", authorities=("admin"))
	void processDeleteNoUserTest() throws Exception{
		mockMvc.perform(get("/admin/users/{id}/delete",20)).andExpect(status().is3xxRedirection());
	    	
	}
	
	/* NO COGE BIEN EL USERNAME DEL USUARIO ACTUAL Y DEL USER QUE LE PASAMOS
	@Test
	@WithMockUser(value="spring", authorities=("admin"))
	void processDeleteUserOwnTest() throws Exception{
		mockMvc.perform(get("/admin/users/{id}/delete",TEST_USERADMIN_ID)).andExpect(status().is3xxRedirection());
	    	
	}
	 */
	
	@Test
	@WithMockUser(value="spring", authorities=("admin"))
	void initUpdateAuthCreationFormTest() throws Exception{
		mockMvc.perform(get("/admin/authorities/{user}/new",TEST_USERNOAUTH_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("authorities"))
				.andExpect(view().name("authorities/editAuth"));
	}
	
	
	@Test
	@WithMockUser(value="spring", authorities=("player"))
	void processAuthCreationFormTest() throws Exception{
		mockMvc.perform(post("/admin/authorities/{user}/new",TEST_USERNOAUTH_ID)
				.with(csrf())
				.param("authority", "player"))
				.andExpect(status().isOk())
				.andExpect(view().name("authorities/editAuth"));
	}
	
}
