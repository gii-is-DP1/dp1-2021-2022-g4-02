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
import sevenisles.user.UserService;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServicesTest {
	
	@Autowired
	private GameService gameService;
	@Autowired
	private PlayerService playerService;
	
	
	
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
		List<Game> gamesunfinished = gameService.findUnfinishedGames();
		for(Integer i=0;i<gamesunfinished.size();i++) {
			assertEquals(gamesunfinished.get(i).getEndHour(),null);
		}
	}
	
	@Test
	public void testFindFinishedGames() {
		List<Game> gamesfinished = gameService.findFinishedGames();
		for(Integer i=0;i<gamesfinished.size();i++) {
		assertNotEquals(gamesfinished.get(i).getEndHour(),null);
		}
	}
	
	@Test
	public void testSaveGame() {
		Game game = new Game();
		int count = gameService.gameCount();
		gameService.saveGame(game);
		assertEquals(count+1,gameService.gameCount());
	}
	
}
