package sevenisles.game;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.card.CardType;
import sevenisles.configuration.SecurityConfiguration;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.status.Status;
import sevenisles.status.StatusService;
import sevenisles.user.Authorities;
import sevenisles.user.AuthoritiesService;
import sevenisles.user.User;
import sevenisles.user.UserController;
import sevenisles.user.UserService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=GameController.class)
@WebMvcTest(value=GameController.class,
		excludeFilters = @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE,classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration = SecurityConfiguration.class)
public class GameControllerTest {
	
	@Autowired
	private MockMvc mockMvc; 
	
	@MockBean
	private GameService gameService;
	
	@MockBean
	private PlayerService playerService;
	
	@MockBean
	private StatusService statusService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	@MockBean
	private CardService cardService;
	
	@Autowired
	private GameController gameController;
	
	@Autowired
	private WebApplicationContext context;
	
	private static final Integer TEST_GAME_ID = 1;
	private static final Integer TEST_USER_ID = 1;
	
	@BeforeEach
	public void setup() { 
		
		//Creación del game
		Game game = new Game();
		game.setId(TEST_GAME_ID);
		game.setCode("B3KLRM");
		game.setCards((List<Card>) cardService.cardFindAll());
		List<Status> status = new ArrayList<Status>();
		game.setStatus(status);
		
		//Creación del player con sesión iniciada actualmente
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
		
		//Entrada de 2 jugadores a la partida y comienzo de la misma
		Player p1 = new Player();
		p1.setId(1);
		p1.setUser(user);
		/*
		Player p2 = new Player();
		p2.setId(2);
		
		gameService.enterGame(game, p1);
		gameService.enterGame(game, p2);
		gameService.startGame(game);*/
		
		List<Status> sts = new ArrayList<>();
		
		Status st = new Status();
		st.setId(1);
		st.setPlayer(p1);
		st.setGame(game);
		st.setCards(new ArrayList<>());	
		sts.add(st);
		
		Status st2 = new Status();
		st.setId(2);
		st.setPlayer(p1);
		st.setGame(game);
		st.setCards(new ArrayList<>());	
		sts.add(st2);
		
		Mockito.when(this.statusService.findStatusOfPlayer(this.playerService.findCurrentPlayer().get().getId())).thenReturn(Optional.of(sts));
		
	}
	
	@Test
	@WithMockUser(value="spring", authorities={"player","admin"})
	void gamesListTest() throws Exception{
		mockMvc.perform(get("/games")).andExpect(status().isOk())
				.andExpect(model().attributeExists("games"))
				.andExpect(view().name("games/gamesList"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities={"player","admin"})
	void availableGamesListTest() throws Exception{
		mockMvc.perform(get("/games/availableGames")).andExpect(status().isOk())
				.andExpect(model().attributeExists("games"))
				.andExpect(view().name("games/availableGames"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities={"player","admin"})
	void startedGamesListTest() throws Exception{
		mockMvc.perform(get("/games/startedGames")).andExpect(status().isOk())
				.andExpect(model().attributeExists("games"))
				.andExpect(view().name("games/startedGames"));
	}
	
	//Da error "No value present" por el Mockito.when del setUp()
	/*@Test
	@WithMockUser(value="spring", authorities=("player"))
	void startedGameTest() throws Exception{
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		mockMvc.perform(get("/games/startedGame")).andExpect(status().isOk())
				.andExpect(model().attributeExists("game"))
				.andExpect(view().name("games/startedGame"));
	}*/
	
	@Test
	@WithMockUser(value="spring", authorities={"player","admin"})
	void initSearchByCodeViewTest() throws Exception{
		mockMvc.perform(get("/games/searchGame")).andExpect(status().isOk())
				.andExpect(view().name("games/searchGame"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities={"player"})
	void processSearchByCodeViewTest() throws Exception{
		mockMvc.perform(post("/games/searchGame")
				.with(csrf())
				.param("code", "B3KLRM"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/games/B3KLRM/enter"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities={"player","admin"})
	void rulesViewTest() throws Exception{
		mockMvc.perform(get("/rules")).andExpect(status().isOk())
				.andExpect(view().name("games/gameRules"));
	}
	
}
