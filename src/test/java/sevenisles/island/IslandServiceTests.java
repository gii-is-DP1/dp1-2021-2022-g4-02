package sevenisles.island;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.user.Authorities;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class IslandServiceTests {
	@Autowired
	private IslandService IslandService;
	
	@Autowired
	private CardService CardService;
	
	@Test
	public void testCountWithInitialData() {
		int count = IslandService.islandCount();
		assertEquals(6,count);
	}
	
	@Test
	public void testFindAll() {
		Iterator<Island> islands = IslandService.islandFindAll().iterator();
        int i = 1;
        while(islands.hasNext()) {
            Island island = islands.next();
            assertEquals(i, island.getId());
            i++;
        }
	}
	
	@Test
	public void testFindById() {
		int id = 4;
		Iterator<Island> islands = IslandService.islandFindAll().iterator();
		
		Island comp = new Island();
		while(islands.hasNext()) {
			comp = islands.next();
			if(comp.getId()==id) break;
		}
		Island res = IslandService.findIslandById(id);
		assertEquals(comp, res);
	}
	
	@Test
	@Transactional
	public void testSaveIsland() {
		// Comprobar que se guarda la nueva isla
		Island newisland = new Island();
		int count = IslandService.islandCount();
		IslandService.saveIsland(newisland);
		assertEquals(count+1, IslandService.islandCount());
		
		// Comprobar que el valor actual de la carta de la isla es el correcto
		int newislandid = newisland.getId();
		Integer cardid = 1;
		Card card = IslandService.getCardFromIsland(newislandid);
		if (card == null) assertEquals(card, null);
		else {
			int cardid2 = card.getId();
			assertEquals(cardid2, cardid);
		}
		
		// Comprobar que el valor de la carta de la isla se actualiza correctamente
		Card card2 = CardService.findCardById(cardid);
		IslandService.fillIsland(newislandid, card2);
		Integer actual = IslandService.getCardFromIsland(newislandid).getId();
		assertEquals(actual, cardid);
	}
}
	
	

