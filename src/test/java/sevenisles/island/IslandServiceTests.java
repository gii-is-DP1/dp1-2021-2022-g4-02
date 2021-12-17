package sevenisles.island;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
import org.springframework.stereotype.Service;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.game.Game;
import sevenisles.game.GameService;
import sevenisles.island.exceptions.IslandNotFoundException;
import sevenisles.islandStatus.IslandStatus;
import sevenisles.islandStatus.IslandStatusService;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class IslandServiceTests {
	@Autowired
	private IslandService IslandService;

	@Autowired
	private IslandStatusService IslandStatusService;
	
	@Autowired
	private CardService CardService;

	@Autowired
	private GameService GameService;
	
	Island newisland = new Island();
	IslandStatus newstatus = new IslandStatus();
	Game newgame = new Game();
	

	@BeforeEach
	public void init() throws IslandNotFoundException {
		IslandService.saveIsland(newisland);
		GameService.saveGame(newgame);
		
		Card card = CardService.findCardById(1).get();

		newstatus.setCard(card);
		newstatus.setIsland(newisland);
		newstatus.setGame(newgame);

		IslandStatusService.saveIslandStatus(newstatus);
	}

	@Test
	public void testCountWithInitialData() {
		int count = IslandService.islandCount();
		assertEquals(7,count);
	}
	
	@Test
	public void testFindAll() {        
        Integer count = IslandService.islandCount();
		Iterator<Island> islands = IslandService.islandFindAll().iterator();
		List<Island> islandslist = StreamSupport.stream(Spliterators.spliteratorUnknownSize(islands, Spliterator.ORDERED), false).collect(Collectors.toList());
		assertEquals(count,islandslist.size());
	}
	
	@Test
	public void testFindById() throws IslandNotFoundException {
		int id = 4;
		Iterator<Island> islands = IslandService.islandFindAll().iterator();
		
		Island comp = new Island();
		while(islands.hasNext()) {
			comp = islands.next();
			if(comp.getId()==id) break;
		}
		Optional<Island> islandOpt = IslandService.findIslandById(id);
		if(islandOpt.isPresent()) {
			Island island = islandOpt.get();
			assertEquals(comp, island);
		}
		else {
			throw new IslandNotFoundException();
		}
	}
	
	@AfterEach
	public void finish() {
		IslandService.deleteIsland(newisland);
		IslandStatusService.deleteIslandStatus(newstatus);
		//GameService.deleteGame(newgame);
	}
	
	@Test
	public void testdeleteIsland() {
		int beforecount = IslandService.islandCount();

		IslandStatusService.deleteIslandStatus(newstatus);
		IslandService.deleteIsland(newisland);
		
		int aftercount = IslandService.islandCount();
		
		assertEquals(aftercount, beforecount-1);
	}

	// TODO testAsignacionIicialIslas
	/* @Test
	public void testAsignacionInicialIslas() {
		int beforecount = IslandService.islandCount();

		IslandStatusService.deleteIslandStatus(newstatus);
		IslandService.deleteIsland(newisland);
		
		int aftercount = IslandService.islandCount();
		
		assertEquals(aftercount, beforecount-1);
	} */
}
	
	

