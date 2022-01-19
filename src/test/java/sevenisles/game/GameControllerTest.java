package sevenisles.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
import sevenisles.game.exceptions.GameControllerException;
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
	private static final Integer TEST_CARD_ID = 1;
	private static final String TEST_GAME_CODE = "B3KLRM";
	
	private Game game;
	private Player p1;
	private List<Status> sts;
	
	@BeforeEach
	public void setup() { 
		
		//Creación del game
		game = new Game();
		game.setId(TEST_GAME_ID);
		game.setCode(TEST_GAME_CODE);
		game.setCurrentPlayer(0);
		game.setCards((List<Card>) cardService.cardFindAll());
		List<Status> status = new ArrayList<Status>();
		game.setStatus(status);
		
		Card card = new Card();
		card.setId(TEST_CARD_ID);
		
		List<Card> cards = new ArrayList<Card>();
		cards.add(card);
		
		
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
		p1 = new Player();
		p1.setId(1);
		p1.setUser(user);
		
		user.setPlayer(p1);
		/*
		Player p2 = new Player();
		p2.setId(2);
		
		gameService.enterGame(game, p1);
		gameService.enterGame(game, p2);
		gameService.startGame(game);*/
		
		sts = new ArrayList<>();
		
		Status st = new Status();
		st.setId(1);
		st.setPlayer(p1);
		st.setGame(game);
		st.setCards(new ArrayList<>());	
		sts.add(st);
		
		Status st2 = new Status();
		st2.setId(2);
		st2.setPlayer(p1);
		st2.setGame(game);
		st2.setCards(new ArrayList<>());	
		sts.add(st2);
		
		game.setStatus(sts);
		
		Mockito.when(cardService.findCardById(TEST_CARD_ID)).thenReturn(Optional.of(card));
		
		//Devuelve una partida por código de partida
		Mockito.when(this.gameService.findGameByCode(TEST_GAME_CODE)).thenReturn(Optional.of(game));
		Mockito.when(this.gameService.findGameById(TEST_GAME_ID)).thenReturn(Optional.of(game));
		
		//Hacer que el usuario de la prueba pertenezca a la partida
		Mockito.when(this.gameService.loggedUserBelongsToGame(game)).thenReturn(true);
		Mockito.when(playerService.findCurrentPlayer()).thenReturn(Optional.of(p1));
		Mockito.when(this.userService.findUserById(TEST_USER_ID)).thenReturn(Optional.of(user));
		Mockito.when(this.userService.findCurrentUser()).thenReturn(Optional.of(user));
		
		
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
	void unfinishedGamesListTest() throws Exception{
		mockMvc.perform(get("/games/unfinishedGames")).andExpect(status().isOk())
				.andExpect(model().attributeExists("games"))
				.andExpect(view().name("games/gamesList"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities={"player","admin"})
	void finishedGamesListTest() throws Exception{
		mockMvc.perform(get("/games/finishedGames")).andExpect(status().isOk())
				.andExpect(model().attributeExists("games"))
				.andExpect(view().name("games/playedGameList"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities={"player","admin"})
	void historyGamesListTest() throws Exception{
		mockMvc.perform(get("/games/playerHistory")).andExpect(status().isOk())
				.andExpect(model().attributeExists("games"))
				.andExpect(view().name("games/playedGameList"));
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
	@Test
	@WithMockUser(value="spring", authorities=("player"))
	void startedGameTest() throws Exception{
		
		Mockito.when(this.statusService.findStatusOfPlayer(playerService.findCurrentPlayer().get().getId())).thenReturn(Optional.of(sts));
		
		mockMvc.perform(get("/games/startedGame")).andExpect(status().isOk())
				.andExpect(model().attributeExists("game"))
				.andExpect(view().name("games/startedGame"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities=("player"))
	void startedGameNotPlayedYetTest() throws Exception{
		
		
		Mockito.when(this.statusService.findStatusOfPlayer(playerService.findCurrentPlayer().get().getId())).thenReturn(Optional.ofNullable(null));
		
		mockMvc.perform(get("/games/startedGame"))
		.andExpect(status().isBadRequest())
	      .andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("No has jugado ninguna partida.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities=("player"))
	void startedGameNotStartedGameTest() throws Exception{
		List<Status> sts = new ArrayList<Status>();
		Status st = new Status();
		st.setScore(30);
		sts.add(st);
		
		
		Mockito.when(this.statusService.findStatusOfPlayer(playerService.findCurrentPlayer().get().getId())).thenReturn(Optional.of(sts));
		mockMvc.perform(get("/games/startedGame")).andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("No tienes ninguna partida empezada.", result.getResolvedException().getMessage()));
	}
	
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
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void gameDetailsByCodeTest() throws Exception{
		mockMvc.perform(get("/games/{code}",TEST_GAME_CODE))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("game"))
			.andExpect(view().name("games/gameDetails"));
			
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void gameDetailsByCodeNotFoundTest() throws Exception{
		Mockito.when(gameService.findGameByCode(TEST_GAME_CODE)).thenReturn(Optional.ofNullable(null));
		mockMvc.perform(get("/games/{code}",TEST_GAME_CODE))
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
		      .andExpect(result -> assertEquals("Partida no encontrada", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void gameBoardByCodeTest() throws Exception{
		mockMvc.perform(get("/games/{code}/board",TEST_GAME_CODE))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("game"))
			.andExpect(view().name("games/board"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void gameBoardByCodeNotFoundTest() throws Exception{
		Mockito.when(gameService.findGameByCode(TEST_GAME_CODE)).thenReturn(Optional.ofNullable(null));
		mockMvc.perform(get("/games/{code}/board",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Partida no encontrada", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void gameBoardByCodeNotBelongingTest() throws Exception{
		Mockito.when(gameService.loggedUserBelongsToGame(game)).thenReturn(false);
		mockMvc.perform(get("/games/{code}/board",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("No perteneces a esta partida.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void initCreateGameTest() throws Exception{
		Mockito.when(statusService.isInAnotherGame(p1)).thenReturn(false);
		mockMvc.perform(get("/games/create"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("game"))
			.andExpect(view().name("games/gameMode"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void initCreateGameNotLoggedTest() throws Exception{
		Mockito.when(playerService.findCurrentPlayer()).thenReturn(Optional.ofNullable(null));
		mockMvc.perform(get("/games/create"))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Necesitas estar logueado como jugador para crear una partida.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void initCreateGameAlreadyInOtherGameTest() throws Exception{
		Mockito.when(statusService.isInAnotherGame(p1)).thenReturn(true);
		mockMvc.perform(get("/games/create"))
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
		      .andExpect(result -> assertEquals("Ya estás dentro de una partida.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void processCreateGameTest() throws Exception{
		mockMvc.perform(post("/games/create")
				.with(csrf())
				.param("id", TEST_GAME_ID.toString())
				.param("gameMode", "0"))
			.andExpect(status().is3xxRedirection());
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void processCreateGameWithErrorTest() throws Exception{
		mockMvc.perform(post("/games/create")
				.with(csrf())
				.param("id", TEST_GAME_ID.toString())
				.param("gameMode", "notNum"))
			.andExpect(status().isOk())
			.andExpect(view().name("games/gameMode"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void enterGameTest() throws Exception{
		Mockito.when(gameService.enterGameUtil(game)).thenReturn(p1);
		Mockito.when(statusService.isInAnotherGame(p1)).thenReturn(false);
		mockMvc.perform(get("/games/{code}/enter",TEST_GAME_CODE))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/games/{code}"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void enterGameNotExistingGameTest() throws Exception{
		Mockito.when(gameService.findGameByCode(TEST_GAME_CODE)).thenReturn(Optional.ofNullable(null));
		mockMvc.perform(get("/games/{code}/enter",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Lo sentimos, pero dicha partida no existe.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void enterGameAlreadyInOtherGameTest() throws Exception{
		Mockito.when(gameService.enterGameUtil(game)).thenReturn(p1);
		Mockito.when(statusService.isInAnotherGame(p1)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/enter",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Ya estás dentro de una partida.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void enterGameAlreadyStartedTest() throws Exception{
		game.setStartHour(LocalTime.now());
		Mockito.when(gameService.enterGameUtil(game)).thenReturn(p1);
		Mockito.when(statusService.isInAnotherGame(p1)).thenReturn(false);
		mockMvc.perform(get("/games/{code}/enter",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("La partida ya ha empezado.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void startGameTest() throws Exception{
		Mockito.when(gameService.loggedUserBelongsToGame(game)).thenReturn(true);
		Mockito.when(statusService.isReadyToStart(TEST_GAME_ID)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/start",TEST_GAME_CODE))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/games/{code}/board"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void startGameNotExistingGameTest() throws Exception{
		Mockito.when(gameService.findGameByCode(TEST_GAME_CODE)).thenReturn(Optional.ofNullable(null));
		mockMvc.perform(get("/games/{code}/start",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Partida no encontrada", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void startGameNotBelongingToGameTest() throws Exception{
		Mockito.when(gameService.loggedUserBelongsToGame(game)).thenReturn(false);
		mockMvc.perform(get("/games/{code}/start",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("No perteneces a esta partida.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void startGameNotReadyTest() throws Exception{
		Mockito.when(gameService.loggedUserBelongsToGame(game)).thenReturn(true);
		Mockito.when(statusService.isReadyToStart(TEST_GAME_ID)).thenReturn(false);
		mockMvc.perform(get("/games/{code}/start",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Necesitas al menos 2 jugadores para empezar.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void playerThrowDiceTest() throws Exception{
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/dice",TEST_GAME_CODE))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/games/{code}/board"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void playerThrowDiceNotExistingGameTest() throws Exception{
		Mockito.when(gameService.findGameByCode(TEST_GAME_CODE)).thenReturn(Optional.ofNullable(null));
		mockMvc.perform(get("/games/{code}/dice",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Partida no encontrada", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void playerThrowDiceNotYourTurnTest() throws Exception{
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(false);
		mockMvc.perform(get("/games/{code}/dice",TEST_GAME_CODE))
		.andExpect(status().isOk())
		.andExpect(view().name("exception"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	void playerThrowDiceAlreadyThrownTest() throws Exception{
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		game.getStatus().get(0).setDiceNumber(3);
		mockMvc.perform(get("/games/{code}/dice",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Ya has tirado el dado este turno.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void robIslandTest() throws Exception{
		Integer islandId=3;
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		Mockito.when(gameService.chooseIslandCondition(game,islandId,sts.get(0))).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}",TEST_GAME_CODE, islandId))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/games/{code}/robIsland/{islandId}/payCard"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void robIslandNotExistingGameTest() throws Exception{
		Mockito.when(gameService.findGameByCode(TEST_GAME_CODE)).thenReturn(Optional.ofNullable(null));
		Integer islandId=3;
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}",TEST_GAME_CODE, islandId))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Partida no encontrada", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void robIslandNotYourTurnTest() throws Exception{
		Integer islandId=3;
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(false);
		Mockito.when(gameService.chooseIslandCondition(game,islandId,sts.get(0))).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}",TEST_GAME_CODE, islandId))
		.andExpect(status().isOk())
		.andExpect(view().name("exception"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void robIslandNotCorrectIslandConditionTest() throws Exception{
		Integer islandId=3;
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		Mockito.when(gameService.chooseIslandCondition(game,islandId,sts.get(0))).thenReturn(false);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}",TEST_GAME_CODE, islandId))
		.andExpect(status().isOk())
		.andExpect(view().name("exception"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void robIslandAlreadyChosenTest() throws Exception{
		Integer islandId=3;
		sts.get(0).setNumberOfCardsToPay(3);
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		Mockito.when(gameService.chooseIslandCondition(game,islandId,sts.get(0))).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}",TEST_GAME_CODE, islandId))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/games/{code}/robIsland/{islandId}/payCard"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void initPayCardTest() throws Exception{
		Integer islandId=3;
		sts.get(0).setChosenIsland(islandId);
		sts.get(0).setNumberOfCardsToPay(0);
		game.setMaxRounds(10);
		game.setCurrentRound(11);
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard",TEST_GAME_CODE, islandId))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/games/{code}/endGame"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void initPayCardNotLastRoundTest() throws Exception{
		Integer islandId=3;
		sts.get(0).setChosenIsland(islandId);
		sts.get(0).setNumberOfCardsToPay(0);
		game.setMaxRounds(10);
		game.setCurrentRound(1);
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard",TEST_GAME_CODE, islandId))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/games/{code}/board"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void initPayCardNotLastTurnTest() throws Exception{
		Integer islandId=3;
		sts.get(0).setChosenIsland(islandId);
		sts.get(0).setNumberOfCardsToPay(0);
		game.setMaxRounds(10);
		game.setCurrentRound(11);
		List<Card> cards = new ArrayList<Card>();
		Card card = new Card(); cards.add(card);
		game.setCards(cards);
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard",TEST_GAME_CODE, islandId))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/games/{code}/board"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void initPayCardNotFinishedPayingTest() throws Exception{
		Integer islandId=3;
		sts.get(0).setChosenIsland(islandId);
		sts.get(0).setNumberOfCardsToPay(1);
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard",TEST_GAME_CODE, islandId))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("game"))
		.andExpect(model().attributeExists("status"))
		.andExpect(view().name("games/payCard"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void initPayCardAnotherIslandChosenTest() throws Exception{
		Integer islandId=3;
		sts.get(0).setChosenIsland(islandId+1);
		sts.get(0).setNumberOfCardsToPay(1);
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard",TEST_GAME_CODE, islandId))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Ya has elegido saquear la isla " + (islandId+1) + ". No puedes cambiarla.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void initPayCardNotYourTurnTest() throws Exception{
		Integer islandId=3;
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(false);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard",TEST_GAME_CODE, islandId))
		.andExpect(status().isOk())
		.andExpect(view().name("exception"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void initPayCardNotExistingGameTest() throws Exception{
		Integer islandId=3;
		Mockito.when(gameService.findGameByCode(TEST_GAME_CODE)).thenReturn(Optional.ofNullable(null));
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard",TEST_GAME_CODE, islandId))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Partida no encontrada!", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void processPayCardNotFinishedPayingTest() throws Exception{
		Integer islandId=3;
		sts.get(0).setChosenIsland(islandId);
		sts.get(0).setNumberOfCardsToPay(1);
		game.setMaxRounds(10);
		game.setCurrentRound(1);
		Mockito.when(statusService.cardInHand(sts.get(0), cardService.findCardById(TEST_CARD_ID).get())).thenReturn(true);
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard/{cardId}",TEST_GAME_CODE, islandId, TEST_CARD_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void processPayCardAllPayedLastTurnTest() throws Exception{
		Integer islandId=3;
		sts.get(0).setChosenIsland(islandId);
		sts.get(0).setNumberOfCardsToPay(0);
		game.setMaxRounds(10);
		game.setCurrentRound(11);
		Mockito.when(statusService.cardInHand(sts.get(0), cardService.findCardById(TEST_CARD_ID).get())).thenReturn(true);
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard/{cardId}",TEST_GAME_CODE, islandId, TEST_CARD_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/games/{code}/endGame"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void processPayCardNotLastRoundTest() throws Exception{
		Integer islandId=3;
		sts.get(0).setChosenIsland(islandId);
		sts.get(0).setNumberOfCardsToPay(0);
		game.setMaxRounds(10);
		game.setCurrentRound(1);
		Mockito.when(statusService.cardInHand(sts.get(0), cardService.findCardById(TEST_CARD_ID).get())).thenReturn(true);
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard/{cardId}",TEST_GAME_CODE, islandId, TEST_CARD_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/games/{code}/board"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void processPayCardNotLastTurnTest() throws Exception{
		Integer islandId=3;
		sts.get(0).setChosenIsland(islandId);
		sts.get(0).setNumberOfCardsToPay(0);
		game.setMaxRounds(10);
		game.setCurrentRound(11);
		List<Card> cards = new ArrayList<Card>();
		Card card = new Card(); cards.add(card);
		game.setCards(cards);
		Mockito.when(statusService.cardInHand(sts.get(0), cardService.findCardById(TEST_CARD_ID).get())).thenReturn(true);
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard/{cardId}",TEST_GAME_CODE, islandId, TEST_CARD_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/games/{code}/board"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void processPayCardNotOwningCardTest() throws Exception{
		Integer islandId=3;		
		Mockito.when(statusService.cardInHand(sts.get(0), cardService.findCardById(TEST_CARD_ID).get())).thenReturn(false);
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard/{cardId}",TEST_GAME_CODE, islandId, TEST_CARD_ID))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("No posees esa carta. Elige otra", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void processPayCardNotExistingCardTest() throws Exception{
		Integer islandId=3;		
		Mockito.when(cardService.findCardById(TEST_CARD_ID)).thenReturn(Optional.ofNullable(null));
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard/{cardId}",TEST_GAME_CODE, islandId, TEST_CARD_ID))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Carta no encontrada.", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void processPayCardNotYourTurnTest() throws Exception{
		Integer islandId=3;		
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(false);
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard/{cardId}",TEST_GAME_CODE, islandId, TEST_CARD_ID))
		.andExpect(status().isOk())
		.andExpect(view().name("exception"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void processPayCardNotExistingGameTest() throws Exception{
		Integer islandId=3;		
		Mockito.when(gameService.findGameByCode(TEST_GAME_CODE)).thenReturn(Optional.ofNullable(null));
		mockMvc.perform(get("/games/{code}/robIsland/{islandId}/payCard/{cardId}",TEST_GAME_CODE, islandId, TEST_CARD_ID))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Partida no encontrada!", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void finishGameTest() throws Exception{
		Mockito.when(statusService.findWinnerStatusByGame(game.getId())).thenReturn(Optional.of(sts));
		game.setMaxRounds(10);
		game.setCurrentRound(11);
		sts.get(0).setWinner(1);
		mockMvc.perform(get("/games/{code}/endGame",TEST_GAME_CODE))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("game","number","ranking","winners"))
		.andExpect(view().name("games/scoreBoard"));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void finishGameNotLastTurnTest() throws Exception{
		Mockito.when(statusService.findWinnerStatusByGame(game.getId())).thenReturn(Optional.of(sts));
		game.setMaxRounds(10);
		game.setCurrentRound(11);
		List<Card> cards = new ArrayList<Card>();
		Card card = new Card(); cards.add(card);
		game.setCards(cards);
		sts.get(0).setWinner(1);
		mockMvc.perform(get("/games/{code}/endGame",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Aún no se puede terminar la partida", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void finishGameNotLastRoundTest() throws Exception{
		Mockito.when(gameService.loggedPlayerCheckTurn(game)).thenReturn(true);
		Mockito.when(statusService.findWinnerStatusByGame(game.getId())).thenReturn(Optional.of(sts));
		game.setMaxRounds(10);
		game.setCurrentRound(1);
		sts.get(0).setWinner(1);
		mockMvc.perform(get("/games/{code}/endGame",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Aún no se puede terminar la partida", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void finishGameNotBelongingToGameTest() throws Exception{
		Mockito.when(gameService.loggedUserBelongsToGame(game)).thenReturn(false);
		mockMvc.perform(get("/games/{code}/endGame",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("No perteneces a la partida", result.getResolvedException().getMessage()));
	}
	
	@Test
	@WithMockUser(value="spring", authorities= {"player"})
	public void finishGameNotExistingGameTest() throws Exception{
		Mockito.when(gameService.findGameByCode(TEST_GAME_CODE)).thenReturn(Optional.ofNullable(null));
		mockMvc.perform(get("/games/{code}/endGame",TEST_GAME_CODE))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof GameControllerException))
	      .andExpect(result -> assertEquals("Partida no encontrada", result.getResolvedException().getMessage()));
	}
	
}
