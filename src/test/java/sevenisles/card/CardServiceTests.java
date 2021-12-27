package sevenisles.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CardServiceTests {
	@Autowired
	private CardService CardService;
	
	@Test
	public void testCountWithInitialData() {
		int count = CardService.cardCount();
		assertEquals(66,count);
	}
	
	@Test
	public void testFindAll() {
		Iterator<Card> cards = CardService.cardFindAll().iterator();
        int i = 1;
        while(cards.hasNext()) {
            Card card=cards.next();
            assertEquals(i, card.getId());
            i++;
        }
	}
	
	@Test
	public void testFindById() {
		int id = 37;
		Iterator<Card> cards = CardService.cardFindAll().iterator();
		Card comp = new Card();
		while(cards.hasNext()) {
			comp=cards.next();
			if(comp.getId()==id) break;
		}
		Card res = CardService.findCardById(id).get();
		assertEquals(comp, res);
	}
	
	@Test
	@Transactional
	public void testSaveCard() {
		int id = 22;
		CardType ct= CardType.RON;
		CardType actual = CardService.findCardById(id).get().getCardType();
		assertNotEquals(ct,actual);
		Card card = CardService.findCardById(id).get();
		card.setCardType(ct);
		assertEquals(ct,card.getCardType());
		CardService.saveCard(card);
		card = CardService.findCardById(id).get();
		assertEquals(ct,card.getCardType());
	}
}
	
	

