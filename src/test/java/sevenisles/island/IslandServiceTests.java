package sevenisles.island;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
import org.springframework.transaction.annotation.Transactional;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.island.exceptions.IslandNotFoundException;
import sevenisles.user.User;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class IslandServiceTests {
	@Autowired
	private IslandService IslandService;
	
	@Autowired
	private CardService CardService;
	
	Island newisland = new Island();
	
//	@BeforeEach
//	public void init() throws IslandNotFoundException {
//		IslandService.saveIsland(newisland);
//		Card card = CardService.findCardById(1);
//		IslandService.fillIsland(newisland.getId(), card);
//	}
//	
//	@Test
//	public void testCountWithInitialData() {
//		int count = IslandService.islandCount();
//		assertEquals(7,count);
//	}
//	
//	@Test
//	public void testFindAll() {        
//        Integer count = IslandService.islandCount();
//		Iterator<Island> islands = IslandService.islandFindAll().iterator();
//		List<Island> islandslist = StreamSupport.stream(Spliterators.spliteratorUnknownSize(islands, Spliterator.ORDERED), false).collect(Collectors.toList());
//		assertEquals(count,islandslist.size());
//	}
//	
//	@Test
//	public void testFindById() throws IslandNotFoundException {
//		int id = 4;
//		Iterator<Island> islands = IslandService.islandFindAll().iterator();
//		
//		Island comp = new Island();
//		while(islands.hasNext()) {
//			comp = islands.next();
//			if(comp.getId()==id) break;
//		}
//		Optional<Island> islandOpt = IslandService.findIslandById(id);
//		if(islandOpt.isPresent()) {
//			Island island = islandOpt.get();
//			assertEquals(comp, island);
//		}
//		else {
//			throw new IslandNotFoundException();
//		}
//	}
//	
//	@Test
//	@Transactional
//	public void testGetCardFromIslandAndSaveIsland() throws IslandNotFoundException {
//		// getCardFromIsland
//		int newislandid = newisland.getId();
//		int correctcardid = 1;
//		Card currentcard = IslandService.getCardFromIsland(newislandid);
//		
//		if (currentcard == null) assertEquals(currentcard, null);
//		else {
//			int currentcardid = currentcard.getId();
//			assertEquals(currentcardid, correctcardid);
//		}
//		
//		// saveIsland
//		Integer newcardid = 3;
//		Card newcard = CardService.findCardById(newcardid);
//		IslandService.fillIsland(newislandid, newcard);
//		Integer actual = IslandService.getCardFromIsland(newislandid).getId();
//		assertEquals(actual, newcardid);
//	}
//	
//	@Test
//	@Transactional
//	public void testEmptyCard() throws IslandNotFoundException {
//		int newislandid = newisland.getId();
//		Card currentcard = IslandService.getCardFromIsland(newislandid);
//		
//		IslandService.emptyIsland(newislandid);
//		
//		Card upatedcard = IslandService.getCardFromIsland(newislandid);
//		
//		assertNotEquals(currentcard, upatedcard);
//		assertEquals(null, upatedcard);
//	}
//	
//	@Test
//	@Transactional
//	public void testFillIsland() throws IslandNotFoundException {
//		int newislandid = newisland.getId();
//		Card currentcard = IslandService.getCardFromIsland(newislandid);
//		
//		Card tempcard = CardService.findCardById(7);
//		IslandService.fillIsland(newislandid, tempcard);
//		
//		Card newcard = IslandService.getCardFromIsland(newislandid);
//		
//		assertNotEquals(currentcard, newcard);
//		assertEquals(tempcard, newcard);
//	}
	
	@AfterEach
	public void finish() {
		IslandService.deleteIsland(newisland);
	}
	
	@Test
	public void testdeleteIsland() {
		int beforecount = IslandService.islandCount();
		
		IslandService.deleteIsland(newisland);
		
		int aftercount = IslandService.islandCount();
		
		assertEquals(aftercount, beforecount-1);
	}
}
	
	

