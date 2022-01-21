package sevenisles.status;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.game.Game;
import sevenisles.game.GameService;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.user.User;
import sevenisles.user.UserService;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class StatusServicesTest {
	
	private StatusService statusServices;

	private GameService gameServices;
	
	private PlayerService playerServices;
	
	private CardService CardService;
	
	private UserService userService;
	
	@Autowired
	public StatusServicesTest(StatusService statusServices, GameService gameServices, PlayerService playerServices, CardService CardService, UserService userServices) {
		this.statusServices = statusServices;
		this.gameServices = gameServices;
		this.playerServices = playerServices;
		this.CardService = CardService;
		this.userService = userServices;
	} 
	
	Status newstatus;
	Status newtwostatus;
	Player newplayer;
	Player newtwoplayer;
	Game newgame;
	
	

	@BeforeEach
	public void init() {
		
		newstatus = new Status();
		newtwostatus = new Status();
		newplayer = new Player();
		newtwoplayer = new Player();
		newgame = new Game();
		
		User user1 = new User();
		user1.setUsername("prueba");
		newplayer.setUser(user1);
		playerServices.savePlayer(newplayer);
		user1.setPlayer(newplayer);
		user1.setFirstName("Prueba");
		user1.setLastName("Uno");
		user1.setPassword("123");
		userService.saveUser(user1);
		playerServices.savePlayer(newtwoplayer);
		gameServices.saveGame(newgame);
		
		Card firstcard = CardService.findCardById(1).get();
		Card secondcard = CardService.findCardById(2).get();
		
		List<Card> cards = new ArrayList<>();
		cards.add(firstcard);
		cards.add(secondcard);
		
		newstatus.setCards(cards);
		newstatus.setGame(newgame);
		newstatus.setPlayer(newplayer);
		newstatus.setScore(26);
		newstatus.setNumberOfCardsToPay(1);
		statusServices.saveStatus(newstatus);
	}
	

	
	@AfterEach
	public void finish() {
		statusServices.deleteStatus(newstatus);
	}
	
	
	
	@Test
	public void testIsInAnotherGame() {
	boolean expected = true;
	newtwostatus.setGame(newgame);
	newtwostatus.setPlayer(newtwoplayer);
	statusServices.saveStatus(newtwostatus);
	boolean cond = statusServices.isInAnotherGame(newtwoplayer);
	assertEquals(cond,expected);
	
	}
	
	
	@Test
	public void testIsInAnotherGameNotPresent() {
	boolean expected = false;
	
	boolean cond = statusServices.isInAnotherGame(newtwoplayer);
	assertEquals(cond,expected);
	
	}
	
	@Test
	public void testIsInAnotherGameFilters() {
	boolean expected = false;
	newgame.setStartHour(LocalTime.now());
	newgame.setEndHour(LocalTime.now());
	newtwostatus.setGame(newgame);
	newtwostatus.setPlayer(newtwoplayer);
	statusServices.saveStatus(newtwostatus);
	boolean cond = statusServices.isInAnotherGame(newtwoplayer);
	assertEquals(cond,expected);
	
	}
	
	@Test
	public void findStatusByGame() {
		int idnewstatus = newstatus.getId();
		List<Status> statusbygame = statusServices.findStatusByGame(newgame.getId()).get();
		assertEquals(statusbygame.get(0).getId(),idnewstatus);
		
	}
	
	@Test
	public void findStatusByGameAndPlayer() {
		int idnewstatus = newstatus.getId();
		Status statusbygameandplayer = statusServices.findStatusByGameAndPlayer(newgame.getId(),newplayer.getId()).get();
		assertEquals(statusbygameandplayer.getId(),idnewstatus);
		
	}
	
	
	@Test
	public void testCountPlayers() {
		
		Integer nplayers = statusServices.countPlayers(newgame);
		assertEquals(nplayers,1);
	}
	
	@Test
	public void testIsNotReadyToStart() {
		boolean expected = false;
		boolean cond = statusServices.isReadyToStart(newgame.getId());
		assertEquals(cond,expected);
	}
	
	@Test
	public void testIsNotReadyToStartOverPlayers() {
		boolean expected = false;
		
		playerServices.savePlayer(newtwoplayer);
		statusServices.saveStatus(newtwostatus);
		
		newtwostatus.setGame(newgame);
		newtwostatus.setPlayer(newtwoplayer);
		statusServices.saveStatus(newtwostatus);
		
		
		
		Player newthirdplayer = new Player();
		Player newfourthplayer = new Player();
		Player newfithplayer = new Player();
		
		playerServices.savePlayer(newthirdplayer);
		playerServices.savePlayer(newfourthplayer);
		playerServices.savePlayer(newfithplayer);
		
		
		Status newthirdstatus = new Status();
		Status newsfourthtatus = new Status();
		Status newsfithtatus = new Status();
		
		newthirdstatus.setPlayer(newthirdplayer);
		newsfourthtatus.setPlayer(newfourthplayer);
		newsfithtatus.setPlayer(newfithplayer);
		
		newthirdstatus.setGame(newgame);
		newsfourthtatus.setGame(newgame);
		newsfithtatus.setGame(newgame);
		
		statusServices.saveStatus(newthirdstatus);
		statusServices.saveStatus(newsfourthtatus);
		statusServices.saveStatus(newsfithtatus);
		gameServices.saveGame(newgame);
		
		
		
		
		
		boolean cond = statusServices.isReadyToStart(newgame.getId());
		assertEquals(cond,expected);
	}
	
	@Test
	public void testIsReadyToStart() {
		boolean expected = true;

		playerServices.savePlayer(newtwoplayer);
		statusServices.saveStatus(newtwostatus);
		
		newtwostatus.setGame(newgame);
		newtwostatus.setPlayer(newtwoplayer);
		statusServices.saveStatus(newtwostatus);
		
		boolean cond = statusServices.isReadyToStart(newgame.getId());
		assertEquals(cond,expected);
	}
	
	@Test
	public void findWinnerStatusByGame() {
		
		playerServices.savePlayer(newtwoplayer);
		statusServices.saveStatus(newtwostatus);
		
		newtwostatus.setGame(newgame);
		newtwostatus.setPlayer(newtwoplayer);
		newtwostatus.setWinner(1);
		statusServices.saveStatus(newtwostatus);

		List<Status> statuswinner = statusServices.findWinnerStatusByGame(newgame.getId()).get();
		assertEquals(statuswinner.get(0).getId(),newtwostatus.getId());
	}
	
	@Test
	public void testIsNotFull() {
		boolean expected = true;
		boolean cond = statusServices.isNotFull(newgame.getId());
		assertEquals(cond,expected);
		
	}
	
	
	@Test
	public void testIsFull() {
		//Llenamos el juego, que en un principio tenía un solo jugador y estado a 4 jugadores y estados
		boolean expected = false;
		Player newthirdplayer = new Player();
		Player newfourthplayer = new Player();
		playerServices.savePlayer(newtwoplayer);
		playerServices.savePlayer(newthirdplayer);
		playerServices.savePlayer(newfourthplayer);
		
		Status newsecondstatus = new Status();
		Status newthirdstatus = new Status();
		Status newsfourthtatus = new Status();
		
		newsecondstatus.setPlayer(newtwoplayer);
		newthirdstatus.setPlayer(newthirdplayer);
		newsfourthtatus.setPlayer(newfourthplayer);
		newsecondstatus.setGame(newgame);
		newthirdstatus.setGame(newgame);
		newsfourthtatus.setGame(newgame);
		
		statusServices.saveStatus(newsecondstatus);
		statusServices.saveStatus(newthirdstatus);
		statusServices.saveStatus(newsfourthtatus);
		
		gameServices.saveGame(newgame);
		
		boolean cond = statusServices.isNotFull(newgame.getId());
		assertEquals(cond,expected);
		
	}
	
	
	@Test
	public void testFindStatusByGameAndScore() {
		int checkscore = 26;
		List<Status> statuscheck = statusServices.findStatusByGameAndScore(newgame.getId(), checkscore).get();
		assertEquals(newstatus.getId(),statuscheck.get(0).getId());
		
		
	}

	@Test
	public void testAddStatusToPlayer() {
	
		
		Integer idstatus = newstatus.getId();
		Status newsecondstatus = new Status();
		
		newstatus.setPlayer(newplayer);
		statusServices.saveStatus(newstatus);
		playerServices.savePlayer(newplayer);
		statusServices.saveStatus(newsecondstatus);
		statusServices.addStatusToPlayer(newstatus, newplayer);
		statusServices.addStatusToPlayer(newsecondstatus, newplayer);
		List<Status> lstatus = statusServices.findStatusOfPlayer(newplayer.getId()).get();
		
		
		assertEquals(idstatus,lstatus.get(0).getId());
	}
	
	@Test
	public void testAddStatusToGame() {
		Status statustest = new Status();
		statusServices.saveStatus(statustest);
		statusServices.addStatusToGame(statustest, newgame);
		statusServices.addStatusToGame(newstatus, newgame);
		
		assertEquals(statustest.getId(),newgame.getStatus().get(0).getId());
	}
	
	@Test
	public void testdeleteStatus() {
		List<Status> statuslist = (List<Status>) statusServices.statusrFindAll();
		int beforecount = statuslist.size();
		
		statusServices.deleteStatus(newstatus);
		
		
		Iterator<Status> statusitbefore = statusServices.statusrFindAll().iterator();
        List<Status> statuslistbef = StreamSupport.stream(Spliterators.spliteratorUnknownSize(statusitbefore, Spliterator.ORDERED), false).collect(Collectors.toList());
		int aftercount = statuslistbef.size();
		
		assertEquals(aftercount, beforecount-1);
	}
	
	@Test
	public void testAddGamePlayerToStatus() {
		Player player = new Player();
		playerServices.savePlayer(player);
		Status statustest = new Status();
		statusServices.addGamePlayerToStatus(statustest, newgame, player);
		Status checkstatus = statusServices.findStatusByGameAndPlayer(newgame.getId(),player.getId()).get();
		assertEquals(checkstatus.getId(),statustest.getId());
	}
	
	@Test
	public void testdeleteStatusById() {
		List<Status> statuslist = (List<Status>) statusServices.statusrFindAll();
      
		int beforecount = statuslist.size();
		statusServices.deleteStatus(newstatus.getId());
		
		
		Iterator<Status> statusitbefore = statusServices.statusrFindAll().iterator();
        List<Status> statuslistbef = StreamSupport.stream(Spliterators.spliteratorUnknownSize(statusitbefore, Spliterator.ORDERED), false).collect(Collectors.toList());
		int aftercount = statuslistbef.size();
		
		assertEquals(aftercount, beforecount-1);
	}
	
	@WithMockUser(username="prueba")	
	@Test
	public void testDeleteCardFromHand() {
		
		boolean expected = false;
		List<Card> lsBefore = newstatus.getCards();
		Card randomcard = lsBefore.get(0);
		statusServices.deleteCardFromHand(newgame,randomcard);
		boolean existscard = statusServices.cardInHand(newstatus,randomcard);
		List<Card> lsAfter= newstatus.getCards();
		assertEquals(expected,existscard);
		assertEquals(lsBefore.size()-1,lsAfter.size());
	}
	
	
	@Test
	public void testcardInHand() {
		Boolean expected = true;
		Card firstcard = newstatus.getCards().get(0);
		Boolean cond = statusServices.cardInHand(newstatus,firstcard);
		assertEquals(cond,expected);
	}
	
	
	@Test
	public void testNocardInHand() {
		Boolean expected = false;
		Card firstcard = new Card();
		Boolean cond = statusServices.cardInHand(newstatus,firstcard);
		assertEquals(cond,expected);
	}
	
}
