package sevenisles.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CardServiceTests {
	@Autowired
	private CardService cardService;
	
	@Test
	public void testCountWithInitialData() {
		int count = cardService.cardCount();
		assertEquals(66,count);
	}
	
	@Test
	public void testFindAll() {
		Iterator<Card> cards = cardService.cardFindAll().iterator();
        int i = 1;
        while(cards.hasNext()) {
            Card card=cards.next();
            assertEquals(i, card.getId());
            i++;
        }
	}
	
	@Test
	public void testFindAllCardType() {
		List<CardType> ls = cardService.findAllCardType();
        assertEquals(ls.size(), 10);
	}
	
	@Test
	public void testFindById() {
		int id = 37;
		Iterator<Card> cards = cardService.cardFindAll().iterator();
		Card comp = new Card();
		while(cards.hasNext()) {
			comp=cards.next();
			if(comp.getId()==id) break;
		}
		Card res = cardService.findCardById(id).get();
		assertEquals(comp, res);
	}
	
	@Test
	@Transactional
	public void testSaveCard() {
		int id = 22;
		CardType ct= CardType.RON;
		CardType actual = cardService.findCardById(id).get().getCardType();
		assertNotEquals(ct,actual);
		Card card = cardService.findCardById(id).get();
		card.setCardType(ct);
		assertEquals(ct,card.getCardType());
		cardService.saveCard(card);
		card = cardService.findCardById(id).get();
		assertEquals(ct,card.getCardType());
	}
	
	@Test
	public void testFindDoubloons() {
		List<Card> doubloons = cardService.findDoubloons();
		assertEquals(doubloons.size(), 27);
	}
	
	@Test
	public void testFindDoubloonsInHand() {
		List<Card> hand = new ArrayList<Card>();
		Card c1 = new Card();
		Card c2 = new Card();
		Card c3 = new Card();
		c1.setCardType(CardType.DOBLON);
		c2.setCardType(CardType.DOBLON);
		c3.setCardType(CardType.CALIZ);
		hand.add(c1);hand.add(c2);hand.add(c3);
		List<Card> doubloons = cardService.findDoubloonsInHand(hand);
		assertEquals(doubloons.size(), 2);
	}
	
}
	
	

