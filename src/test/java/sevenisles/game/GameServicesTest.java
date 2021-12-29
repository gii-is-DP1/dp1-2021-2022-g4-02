package sevenisles.game;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.player.Player;
import sevenisles.player.PlayerService;
import sevenisles.status.Status;
import sevenisles.status.StatusService;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServicesTest {
	
	@Autowired
	private GameService gameService;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private CardService cardService;
	@Autowired
	private StatusService statusService;
	
	Game game = new Game();
	
	@BeforeEach
	public void init() {
		game.setId(23);
		Iterator<Card> cards = cardService.cardFindAll().iterator();
		List<Card> deck = StreamSupport.stream(Spliterators.spliteratorUnknownSize(cards, Spliterator.ORDERED), false).collect(Collectors.toList());
		game.setCards(deck);
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
	
//	@Test
//	public void testFindGameByCode() {
//		Game game = new Game();
//		Player player = playerService.findPlayerById(1).get();
//		game.addPlayer(player);
//		game.setStartHour(LocalTime.now());
//		game.setCode("GTHYUIL");
//		gameService.saveGame(game);
//		assertEquals(game.getId(),gameService.findGameByCode("GTHYUIL").get().getId());
//
//	}
	
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
		gameService.saveGame(game);
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
			assertTrue(availableGames.get(i).getStatus().size()<4);
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
		System.out.println("mazo antes "+deckBefore);
		System.out.println("----------------------------"+game.getId());
		System.out.println("----------------------------"+cardToDelete.getId());
		gameService.deleteCardFromDeck(game.getId(), cardToDelete.getId());
		System.out.println(game.getCards().get(0).getId());
		gameService.saveGame(game);
		System.out.println("cartas actuales "+game.getCards().size());
		assertEquals(game.getCards().size(),deckBefore-1);
		assertFalse(game.getCards().contains(cardToDelete));	
	}
	
	@Test
	public void testDeleteCardFromDeck() {
		int deckBefore = game.getCards().size();
		Card cardToDelete = game.getCards().get(0);
		System.out.println("mazo antes "+deckBefore);
		gameService.deleteCardFromDeck(game, cardToDelete);
		gameService.saveGame(game);
		System.out.println("cartas actuales "+game.getCards().size());
		assertEquals(game.getCards().size(),deckBefore-1);
		assertFalse(game.getCards().contains(cardToDelete));	
	}

	@Test
	public void createGame() {
		Player player = new Player();
		player.setId(10);
		Game game = new Game();
		game.setId(20);
		gameService.createGame(game, player);
		Optional<Status> status = statusService.findStatusByGameAndPlayer(game.getId(), player.getId());
		assertTrue(gameService.findNotStartedGames().contains(game));
		assertFalse(status.get().equals(null));
	}
	
}
