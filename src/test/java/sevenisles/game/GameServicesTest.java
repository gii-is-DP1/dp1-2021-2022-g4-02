package sevenisles.game;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.card.CardType;
import sevenisles.game.exceptions.GameControllerException;
import sevenisles.island.Island;
import sevenisles.island.IslandService;
import sevenisles.islandStatus.IslandStatus;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.statistics.StatisticsService;
import sevenisles.status.Status;
import sevenisles.status.StatusService;
import sevenisles.user.Authorities;
import sevenisles.user.AuthoritiesService;
import sevenisles.user.User;
import sevenisles.user.UserService;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServicesTest {
	

	private GameService gameService;

	private PlayerService playerService;

	private CardService cardService;

	private StatusService statusService;
	
	private IslandService islandService;
	
	private IslandService islandStatusService;
	
	private UserService userService;
	
	private AuthoritiesService authService;
	
	private StatisticsService statisticsService;
	
	Game game;
	Integer playerId;
	List<Status> status;
	
	@Autowired
	public GameServicesTest(GameService gameService, PlayerService playerService, CardService cardService, StatusService statusService,
			IslandService islandService, IslandService islandStatusService, UserService userService,AuthoritiesService authService, StatisticsService statisticsService) {
		this.gameService = gameService;
		this.playerService = playerService;
		this.cardService = cardService;
		this.statusService = statusService;
		this.islandService = islandService;
		this.islandStatusService = islandStatusService;
		this.userService=userService;
		this.authService=authService;
	}
	
	
	@BeforeEach
	public void init() {
        game = new Game();
        status = new ArrayList<Status>();
        List<Card> deck =(List<Card>) cardService.cardFindAll();
        
        User user = new User();
        user.setUsername("test");
        user.setFirstName("Perico");
        user.setLastName("El de los palotes");
        user.setPassword("prueba");
        
        User user2 = new User();
        user2.setUsername("username2");
        user2.setFirstName("periquito");
        user2.setLastName("palotes");
        user2.setPassword("prueba2");
        
        
        Authorities auth = new Authorities();
        auth.setAuthority("player");
        auth.setUser(user);
        authService.saveAuthorities(auth);
        user.setAuthorities(auth);
        
        Player player = new Player();
        player.setUser(user);
        playerId=player.getId();
        playerService.savePlayer(player);
        user.setPlayer(player);
        
        Player player2 = new Player();
        player2.setUser(user2);
        playerService.savePlayer(player2);
        user2.setPlayer(player2);
        
        userService.saveUser(user);
        userService.saveUser(user2);
        
        Status status1 = new Status();
        status1.setPlayer(player);
        status1.setGame(game);
        status1.setScore(10);
        statusService.saveStatus(status1);
        status.add(status1);
        
        Status status2 = new Status();
        status2.setPlayer(player2);
        status2.setGame(game);
        status2.setScore(15);
        statusService.saveStatus(status2);
        status.add(status2);
        
        game.setStatus(status);
        game.setCards(deck);
        gameService.saveGame(game);
	}
	
	@Test
	public void testCountWithInitialData() {
		Iterator<Game> games = gameService.gameFindAll().iterator();
		
		List<Game> lgames = StreamSupport.stream(Spliterators.spliteratorUnknownSize(games,Spliterator.ORDERED), false).collect(Collectors.toList());
		
        int count = gameService.gameCount();
        assertEquals(lgames.size(),count);
    }
	
	
	@Test
	public void testGameFindAll() {
		int cuenta = gameService.gameCount();
		Iterator<Game> games = gameService.gameFindAll().iterator();
		
		List<Game> lgames = StreamSupport.stream(Spliterators.spliteratorUnknownSize(games,Spliterator.ORDERED), false).collect(Collectors.toList());
		assertEquals(cuenta,lgames.size());
	}
	
	
	@Test
	public void testFindGameById(){
		Optional<Game> findgame = gameService.findGameById(game.getId());
		if(findgame.isPresent()) {
			assertEquals(game.getId(),findgame.get().getId());
		}
	}
	
	@Test
	public void testFindGameByCode() {
		Game game = new Game();
		game.setStartHour(LocalTime.now());
		game.setCode("GTHYUIL");
		gameService.saveGame(game);
		assertEquals(game.getId(),gameService.findGameByCode("GTHYUIL").get().getId());

	}
	
	@Test
	public void testFindUnfinishedGames() {
		List<Game> unfinishedGames = gameService.findUnfinishedGames();
		for(Integer i=0;i<unfinishedGames.size();i++) {
			assertEquals(unfinishedGames.get(i).getEndHour(),null);
		}
	}
/*	
	@BeforeEach
	public void init() {
		Game game = new Game();
		gameService.saveGame(game);
		System.out.println("Id del juego " + game.getId());
	}
	*/
	
	@Test
	public void testFindFinishedGames() {
		List<Game> finishedGames = gameService.findFinishedGames();
		for(Integer i=0;i<finishedGames.size();i++) {
			assertNotEquals(finishedGames.get(i).getEndHour(),null);
		}
	}
	
	@Test
	public void testSaveGame() {
		int count = gameService.gameCount();
		Game g2 = new Game();
		gameService.saveGame(g2);
		assertEquals(count+1,gameService.gameCount());
	}
	
	@Test
	public void testFindStartedGames() {
		List<Game> startedGames = gameService.findStartedGames();
		for(Integer i=0;i<startedGames.size();i++) {
			assertNotEquals(startedGames.get(i).getStartHour(),null);
			assertEquals(startedGames.get(i).getEndHour(),null);
		}
	}
	
	
	@Test
	public void testFindNotStartedGames() {
		List<Game> notStartedGames = gameService.findNotStartedGames();
		for(Integer i=0;i<notStartedGames.size();i++) {
			assertEquals(notStartedGames.get(i).getStartHour(),null);
			assertEquals(notStartedGames.get(i).getEndHour(),null);
		}
	}
	
	@Test
	public void testFindAvailableGames() {
		List<Game> availableGames = gameService.findAvailableGames();
		for(Integer i=0;i<availableGames.size();i++) {
			assertEquals(availableGames.get(i).getStartHour(),null);
			assertEquals(availableGames.get(i).getEndHour(),null);
			assertTrue(availableGames.get(i).getStatus()==null||availableGames.get(i).getStatus().size()<4);
		}
	}
	
	@Test
	public void testNextPlayer() {
		Game game = new Game();
		Player player1 = new Player();
		Player player2 = new Player();
		gameService.enterGame(game, player1);
		gameService.enterGame(game, player2);
		
		game.setCurrentPlayer(0);
		gameService.nextPlayer(game);
		assertEquals(game.getCurrentPlayer(), 1);
	}
	
	@Test
	public void testEnterGame() {
		Game game = new Game();
		Player player = new Player();
		gameService.enterGame(game, player);
		assertEquals(game.getStatus().size(), 1);
	}
	
	@Test
	public void testDeleteCardFromDeckWithIds() {
		int deckBefore = game.getCards().size();
		Card cardToDelete = game.getCards().get(0);
		gameService.deleteCardFromDeck(game.getId(), cardToDelete.getId());
		System.out.println(game.getCards().get(0).getId());
		gameService.saveGame(game);
		assertEquals(game.getCards().size(),deckBefore-1);
		for(Card c:game.getCards()) {
            assertFalse(c.getId()==cardToDelete.getId());
        }	
	}
	
	@Test
	public void testDeleteCardFromDeck() {
		int deckBefore = game.getCards().size();
		Card cardToDelete = cardService.findCardById(1).get();
		System.out.println("mazo antes "+deckBefore);
		gameService.deleteCardFromDeck(game, cardToDelete);
		assertEquals(game.getCards().size(),deckBefore-1);
		System.out.println("contiene la carta eliminada???"+game.getCards().contains(cardToDelete));
		for(Card c:game.getCards()) {
            assertFalse(c.getId()==cardToDelete.getId());
        }	
	}
	
	@Test
	@WithMockUser(username="test")
	public void testLoggedUserBelongsToGame() {
		Boolean cond = gameService.loggedUserBelongsToGame(game);
		assertEquals(true, cond);
	}
	
	@Test
	@WithMockUser(username="test2", authorities="admin")
	public void testLoggedUserBelongsToGameError() {
		Boolean cond = gameService.loggedUserBelongsToGame(game);
		assertEquals(false, cond);
	}

	@Test
	public void testCreateGame() {
		Player player = new Player();
		playerService.savePlayer(player);
		Game game1 = new Game();
		gameService.createGame(game1, player);
		Optional<Status> status = statusService.findStatusByGameAndPlayer(game1.getId(), player.getId());
		assertTrue(gameService.findNotStartedGames().contains(game1));
		assertFalse(status.get().equals(null));
	}
	
	@Test
	@WithMockUser(username="test")
	public void testEnterGameUtil()  {
		try {
			Player player = gameService.enterGameUtil(game);
			assertEquals("test", player.getUser().getUsername());
		} catch (GameControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@WithMockUser(username="test")
	public void testEnterGameUtilFullGame()  {
		Status status1 = new Status();
		Status status2 = new Status();
		Status status3 = new Status();
		status1.setGame(game);status2.setGame(game);status3.setGame(game);
		status.add(status1);status.add(status2);status.add(status3);
		game.setStatus(status);
		assertThrows(GameControllerException.class,()->{
			gameService.enterGameUtil(game);
		});
	}
	
	@Test
	public void testEnterGameUtilNotLogged()  {
		assertThrows(GameControllerException.class,()->{
			gameService.enterGameUtil(game);
		});
	}
	
	
	@Test
	public void testStartGame() {
		Player p1 = new Player();
		
		playerService.savePlayer(p1);
		Player p2 = new Player();
		playerService.savePlayer(p2);

		
		gameService.enterGame(game, p1);
		gameService.enterGame(game, p2);
		gameService.startGame(game);
		assertTrue(game.getStartHour()!=null);
		assertTrue(game.getCurrentRound()==1);
		List<Island> li = (List<Island>) islandService.islandFindAll();
		for(int i = 0;i<li.size();i++) {
			assertTrue(game.getIslandStatus().get(i).getCard()!=null);
		}
		assertTrue(game.getCurrentRound()==1);
		
	}
	
	@Test
	public void testMaxTurns() {
		Game g2 = new Game();
		Player p1 = new Player();
		Player p2 = new Player();
		gameService.enterGame(g2, p1);
		gameService.enterGame(g2, p2);
		gameService.maxTurns(g2);
		assertEquals(g2.getMaxRounds(), 27);
	}
	
	@Test
	public void testPlayerThrowDice() {
		Game g2 = new Game();
		Player p1 = new Player();
		Player p2 = new Player();
		gameService.enterGame(g2, p1);
		gameService.enterGame(g2, p2);
		Integer currentPlayer = 0;
		g2.setCurrentPlayer(currentPlayer);
		gameService.playerThrowDice(g2);
		Integer diceNumber = g2.getStatus().get(currentPlayer).getDiceNumber();
		assertTrue(diceNumber>=1 && diceNumber<=6);
	}
	
	@Test
	public void testNextTurn1() {
		Game g2 = new Game();
		Player p1 = new Player();
		Player p2 = new Player();
		gameService.enterGame(g2, p1);
		gameService.enterGame(g2, p2);
		g2.setInitialPlayer(1);
		g2.setCurrentRound(3);
		Integer roundBefore = g2.getCurrentRound();
		Integer currentPlayer = 0;
		g2.setCurrentPlayer(currentPlayer);
		
		gameService.nextTurn(g2);
		assertEquals(g2.getStatus().get(currentPlayer).getDiceNumber(), null);
		assertEquals(g2.getStatus().get(currentPlayer).getChosenIsland(), null);
		assertEquals(g2.getCurrentPlayer(),(currentPlayer+1)%g2.getStatus().size());
		assertEquals(g2.getCurrentRound(), roundBefore+1);
		assertEquals(g2.getFinishedTurn(),0);
	}
	
	@Test
	public void testNextTurn2() {
		Game g2 = new Game();
		Player p1 = new Player();
		Player p2 = new Player();
		gameService.enterGame(g2, p1);
		gameService.enterGame(g2, p2);
		g2.setInitialPlayer(0);
		g2.setCurrentRound(3);
		Integer roundBefore = g2.getCurrentRound();
		Integer currentPlayer = 0;
		g2.setCurrentPlayer(currentPlayer);
		
		gameService.nextTurn(g2);
		assertEquals(g2.getStatus().get(currentPlayer).getDiceNumber(), null);
		assertEquals(g2.getStatus().get(currentPlayer).getChosenIsland(), null);
		assertEquals(g2.getCurrentPlayer(),(currentPlayer+1)%g2.getStatus().size());
		assertEquals(g2.getCurrentRound(), roundBefore);
		assertEquals(g2.getFinishedTurn(),0);
	}
	
	@Test
	public void testLlenarIsla1() {
		gameService.asignacionInicialIslas(game);
		IslandStatus islSt = game.getIslandStatus().get(0);
		gameService.llenarIsla(game, islSt);
		Card islandCard = game.getIslandStatus().get(0).getCard();
		assertNotEquals(islandCard, null);
		for(Card c:game.getCards()) {
            assertFalse(c.getId()==islandCard.getId());
        }	
	}
	
	@Test
	public void testLlenarIsla2() {
		Game g2 = new Game();
		g2.setCards(new ArrayList<Card>());
		gameService.asignacionInicialIslas(g2);
		IslandStatus islSt = g2.getIslandStatus().get(0);
		gameService.llenarIsla(g2, islSt);
		assertEquals(g2.getIslandStatus().get(0).getCard(), null);
	}
	
	@Test
	public void testAsignacionInicialIslas(){
		gameService.asignacionInicialIslas(game);
		assertEquals(game.getIslandStatus().size(), 6);
		Boolean emptyIslands = true;
		for(IslandStatus islSt: game.getIslandStatus()) {
			if(islSt.getCard()!=null) emptyIslands = false;
		}
		assertTrue(emptyIslands);
	}
	
	@Test
	public void testRepartoInicial() {
		//Todavia no tiene la cobertura total del método del servicio
		//Hace falta retocar
		gameService.asignacionInicialIslas(game);
		gameService.repartoInicial(game);
		
		Boolean filledIslands = true;
		for(IslandStatus islSt: game.getIslandStatus()) {
			if(islSt.getCard()==null) filledIslands = false;
		}
		assertTrue(filledIslands);
		
		Boolean numCardsInHand = true;
		Boolean doubloonsInHand = true;
		for(Status st: game.getStatus()) {
			Integer countDoubloons = 0;
			for(Card card: st.getCards()) {
				if(card.getCardType()==CardType.DOBLON) countDoubloons++;
			}
			if(st.getCards().size()!=3) numCardsInHand = false;
			if(countDoubloons != 3) doubloonsInHand = false;
		}
		
		assertTrue(numCardsInHand);
		assertTrue(doubloonsInHand);
	}
	
	@Test
	public void testOrderStatusByScore() {
		
		Status statuscheckone = new Status();
		Status statuschecktwo = new Status();
		Status statuscheckthree = new Status();
		
		statuscheckone.setScore(20);
		statuschecktwo.setScore(40);
		statuscheckthree.setScore(25);
		
		statusService.saveStatus(statuscheckone);
		statusService.saveStatus(statuschecktwo);
		statusService.saveStatus(statuscheckthree);
		
		status.add(statuscheckone);
		status.add(statuschecktwo);
		status.add(statuscheckthree);
		
		List<Status> statusordered = gameService.orderStatusByScore(status);
		assertEquals(statuschecktwo.getScore(),statusordered.get(0).getScore());
	}
	
	@Test
	@WithMockUser(value="test",authorities="player")
	public void testUtilAttributes() {
		this.game.setCurrentPlayer(0);
		
		ModelMap model = new ModelMap();
		gameService.utilAttributes(game, model);
		assertTrue(model.containsAttribute("currentPlayerStatus") && model.containsAttribute("playerUserId")
				&& model.containsAttribute("loggedUserId"));
	}
	
	
	@Test
	@WithMockUser(value="test",authorities="player")
	public void testloggedPlayerCheckTurn() {
		this.game.setCurrentPlayer(0);
		this.game.setCurrentRound(1);
		this.game.setMaxRounds(66);;
		
		gameService.loggedPlayerCheckTurn(game);
		Status st = status.get(0);
		assertTrue(st.getPlayer().getId() == playerService.findCurrentPlayer().get().getId());
	}
	
	@Test
	@WithMockUser(value="test",authorities="player")
	public void testloggedPlayerCheckTurnEndGame() throws GameControllerException{
		this.game.setCurrentPlayer(0);
		this.game.setCurrentRound(67);
		this.game.setMaxRounds(66);;
		try {
			gameService.loggedPlayerCheckTurn(game);
			Status st = status.get(0);
			assertTrue(st.getPlayer().getId() == playerService.findCurrentPlayer().get().getId());
		}catch (GameControllerException e){
			assertTrue(e.getMessage().contains("La partida ha terminado."));
		}
		
	}
	
	@Test
	@WithMockUser(value="test2",authorities="player")
	public void testloggedPlayerCheckTurnNotBelongsGame() throws GameControllerException{
		this.game.setCurrentPlayer(0);
		this.game.setCurrentRound(67);
		this.game.setMaxRounds(66);;
		try {
			gameService.loggedPlayerCheckTurn(game);
			Status st = status.get(0);
			assertTrue(st.getPlayer().getId() == playerService.findCurrentPlayer().get().getId());
		}catch (GameControllerException e){
			assertTrue(e.getMessage().contains("No perteneces a esta partida."));
		}
		
	}
	
	
	@Test
	@WithMockUser(value="test",authorities="player")
	public void testloggedPlayerCheckTurnNotTurn() throws GameControllerException{
		this.game.setCurrentPlayer(1);
		this.game.setCurrentRound(2);
		this.game.setMaxRounds(66);;
		try {
			gameService.loggedPlayerCheckTurn(game);
			Status st = status.get(0);
			assertTrue(st.getPlayer().getId() == playerService.findCurrentPlayer().get().getId());
		}catch (GameControllerException e){
			assertTrue(e.getMessage().contains("No es tu turno."));
		}
		
	}
	
	//Tests de chooseIslandCondition
	@Test
	public void testChooseIslandCondition() throws GameControllerException{
		status.get(0).setDiceNumber(3);
		gameService.asignacionInicialIslas(game);
		gameService.repartoInicial(game);
		assertTrue(gameService.chooseIslandCondition(game, 3, status.get(0)));
	}
	
	@Test
	public void testChooseIslandConditionNoCardOnIsland() throws GameControllerException{
		status.get(0).setDiceNumber(3);
		gameService.asignacionInicialIslas(game);
		assertThrows(GameControllerException.class,()->{
			gameService.chooseIslandCondition(game, 3, status.get(0));
		});
	}
	
	@Test
	public void testChooseIslandConditionNoIsland() throws GameControllerException{
		status.get(0).setDiceNumber(3);
		assertThrows(GameControllerException.class,()->{
			gameService.chooseIslandCondition(game, 3, status.get(0));
		});
	}
	
	@Test
	public void testChooseIslandConditionOtherIslandAlreadyChosen() throws GameControllerException{
		status.get(0).setDiceNumber(3);
		status.get(0).setChosenIsland(2);
		assertThrows(GameControllerException.class,()->{
			gameService.chooseIslandCondition(game, 3, status.get(0));
		});
	}
	
	@Test
	public void testChooseIslandConditionCorrectIslandChosen() throws GameControllerException{
		status.get(0).setDiceNumber(3);
		status.get(0).setChosenIsland(3);
		assertThrows(GameControllerException.class,()->{
			gameService.chooseIslandCondition(game, 3, status.get(0));
		});
	}
	
	@Test
	public void testChooseIslandConditionNoDice() throws GameControllerException{
		assertThrows(GameControllerException.class,()->{
			gameService.chooseIslandCondition(game, 3, status.get(0));
		});
	}
	
	@Test
	public void testChooseIslandConditionAlreadyFinishedTurn() throws GameControllerException{
		game.setFinishedTurn(1);
		assertThrows(GameControllerException.class,()->{
			gameService.chooseIslandCondition(game, 3, status.get(0));
		});
	}
	
	@Test
	public void testChooseIsland() throws GameControllerException{
		Integer dice = 3;
		Integer island = 3;
		status.get(0).setDiceNumber(dice);
		gameService.asignacionInicialIslas(game);
		gameService.repartoInicial(game);
		Integer difference = Math.abs(dice-island);
		gameService.chooseIsland(game, island, status.get(0));
		assertEquals(game.getStatus().get(0).getNumberOfCardsToPay(),difference);
		assertEquals(game.getStatus().get(0).getChosenIsland(),island);	
	}
	
	@Test
	public void testChooseIslandNotEnoughCards() throws GameControllerException{
		Integer dice = 6;
		Integer island = 1;
		status.get(0).setDiceNumber(dice);
		gameService.asignacionInicialIslas(game);
		gameService.repartoInicial(game);
		assertThrows(GameControllerException.class,()->{
			gameService.chooseIsland(game, island, status.get(0));
		});
	}
	
	@Test
	public void testRobIsland() throws GameControllerException{
		Integer dice = 3;
		Integer island = 3;
		status.get(0).setDiceNumber(dice);
		gameService.asignacionInicialIslas(game);
		gameService.repartoInicial(game);
		gameService.chooseIsland(game, island, status.get(0));
		Card before = game.getIslandStatus().get(island-1).getCard();
		gameService.robIsland(game, island, status.get(0));
		Card after = game.getIslandStatus().get(island-1).getCard();
		assertNotEquals(before, after);
		assertEquals(1,game.getFinishedTurn());
		assertNull(status.get(0).getNumberOfCardsToPay());
	}
	
	@Test
	public void testRobIslandNoChosenIsland() throws GameControllerException{
		Integer dice = 3;
		Integer island = 3;
		status.get(0).setDiceNumber(dice);
		gameService.asignacionInicialIslas(game);
		gameService.repartoInicial(game);
		assertThrows(GameControllerException.class, ()->{
			gameService.robIsland(game, island, status.get(0));
		});
	}
	
	@Test
	public void testRobIslandAnotherIslandChosen() throws GameControllerException{
		Integer dice = 3;
		Integer island = 3;
		status.get(0).setDiceNumber(dice);
		gameService.asignacionInicialIslas(game);
		gameService.repartoInicial(game);
		gameService.chooseIsland(game, island, status.get(0));
		assertThrows(GameControllerException.class, ()->{
			gameService.robIsland(game, 2, status.get(0)); //Habiendo elegido saquear la 3, intentamos saquear la 2
		});
	}
}
