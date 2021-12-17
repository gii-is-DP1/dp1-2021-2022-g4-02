package sevenisles.islandStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import sevenisles.island.Island;
import sevenisles.island.IslandService;
import sevenisles.islandStatus.exceptions.IslandStatusNotFoundException;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class IslandStatusServiceTests {
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
	public void init() throws IslandStatusNotFoundException {
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
		int count = IslandStatusService.islandStatusCount();
		assertEquals(1,count);
	}
	
	/* @Test
	public void testFindAll() {        
        Integer count = IslandStatusService.islandStatusCount();
		Iterator<IslandStatus> islandStatus = IslandStatusService.islandStatusFindAll().iterator();
		List<IslandStatus> islandStatuslist = StreamSupport.stream(Spliterators.spliteratorUnknownSize(islandStatus, Spliterator.ORDERED), false).collect(Collectors.toList());
		assertEquals(count,islandStatuslist.size());
	} */

    /* findIslandStatusByGame
    findIslandStatusByGameAndIsland */

	
	/* @AfterEach
	public void finish() {
		//IslandStatusService.deleteIslandStatus(newstatus);
        IslandService.deleteIsland(newisland);
        //GameService.deleteGame(newgame);
	} */
	
	/* @Test
	public void testdeleteIsland() {
		int beforecount = IslandService.islandCount();

		IslandStatusService.deleteIslandStatus(newstatus);
		IslandService.deleteIsland(newisland);
		
		int aftercount = IslandService.islandCount();
		
		assertEquals(aftercount, beforecount-1);
	} */
}
