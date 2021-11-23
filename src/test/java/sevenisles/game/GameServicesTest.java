package sevenisles.game;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


import sevenisles.player.Player;
import sevenisles.player.PlayerService;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServicesTest {
	
	@Autowired
	private GameService gameService;
	@Autowired
	private PlayerService playerService;
	
	
	@Test
	public void testCountWithInitialData() {
        int count = gameService.gameCount();
        assertEquals(2,count);
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
		int id = 1;
		Game game = gameService.findGameById(id);
		assertEquals(id,game.getId());
	}
	
	@Test
	public void testFindGameByCode() {
		Game game = new Game();
		Player player = playerService.findPlayerById(1).get();
		game.addPlayer(player);
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
	
	@Test
	public void testFindFinishedGames() {
		List<Game> finishedGames = gameService.findFinishedGames();
		for(Integer i=0;i<finishedGames.size();i++) {
			assertNotEquals(finishedGames.get(i).getEndHour(),null);
		}
	}
	
	@Test
	public void testSaveGame() {
		Game game = new Game();
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
}
