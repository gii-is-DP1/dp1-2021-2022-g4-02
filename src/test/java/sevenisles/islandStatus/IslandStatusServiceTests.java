package sevenisles.islandStatus;

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
		assertEquals(7,count);
	}
	
	@Test
	public void testFindAll() {        
        Integer count = IslandStatusService.islandStatusCount();
		Iterator<IslandStatus> islandStatus = IslandStatusService.islandStatusFindAll().iterator();
		List<IslandStatus> islandStatuslist = StreamSupport.stream(Spliterators.spliteratorUnknownSize(islandStatus, Spliterator.ORDERED), false).collect(Collectors.toList());
		assertEquals(count,islandStatuslist.size());
	}

	@Test
	public void testFindIslandStatusByGame() throws IslandStatusNotFoundException {        
        Optional<List<IslandStatus>> islandStatusListOpt = IslandStatusService.findIslandStatusByGame(newgame.getId());
		
		if(islandStatusListOpt.isPresent()) {
			List<IslandStatus> islandStatusList = islandStatusListOpt.get();

			Boolean bool = islandStatusList.stream().allMatch(islandStatus -> islandStatus.getGame() == newgame);
			assertEquals(bool, true);
		}
		else {
			throw new IslandStatusNotFoundException();
		}
	}

	@Test
	public void testFindIslandStatusByGameAndIsland() throws IslandStatusNotFoundException {        
		Optional<IslandStatus> islandStatusOpt = IslandStatusService.findIslandStatusByGameAndIsland(newgame.getId(), newisland.getId());
		
		if(islandStatusOpt.isPresent()) {
			IslandStatus islandStatus = islandStatusOpt.get();
			assertEquals(newstatus, islandStatus);
		}
		else {
			throw new IslandStatusNotFoundException();
		}
	}

	@AfterEach
	public void finish() {
		IslandStatusService.deleteIslandStatus(newstatus);
        IslandService.deleteIsland(newisland);
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
}
