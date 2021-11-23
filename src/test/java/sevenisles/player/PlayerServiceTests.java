package sevenisles.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTests {

	@Autowired
	private PlayerService PlayerService;
	
	
	
	@Test
	public void testCountWithInitialData() {
        int count = PlayerService.playerCount();
        assertEquals(2,count);
    }
	
	@Test
	public void testFindAll() {
		Integer count = PlayerService.playerCount();
		Iterator<Player> players = PlayerService.playerFindAll().iterator();
		List<Player> playersList = StreamSupport.stream(Spliterators.spliteratorUnknownSize(players, Spliterator.ORDERED), false).collect(Collectors.toList());
		assertEquals(count,playersList.size());
	}
	
	@Test
	public void testFindPlayerById() {
		/* Sabemos que el id= 2 es el player2, con username = player2*/
		int id = 2;
		Player player = PlayerService.findPlayerById(id).get();
		assertEquals("player2",player.getUser().getUsername());
	}
	
	
	@Test
	public void TestSavePlayer() {
		Player player = new Player();
		
		int countBefore = PlayerService.playerCount();
		
		PlayerService.savePlayer(player);
        int countAfter = PlayerService.playerCount();
		
	
		assertEquals(countBefore+1, countAfter);
		
		PlayerService.deletePlayer(player.getId());
		
	}
	
	@Test
	public void testDeletePlayer() {
		Player player = new Player();
		PlayerService.savePlayer(player);
		int countBefore = PlayerService.playerCount();
		
		PlayerService.deletePlayer(player.getId());;
		int countAfter = PlayerService.playerCount();
		
		assertEquals(countAfter,countBefore-1);
	}
}
