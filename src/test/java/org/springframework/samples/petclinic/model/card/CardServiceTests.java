package org.springframework.samples.petclinic.model.card;

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
		Card card1 = cards.next();
		assertEquals(card1.getId(), 1);
		Card finalCard = new Card();
		while(cards.hasNext()) {
			finalCard=cards.next();
		}
		assertEquals(66, finalCard.getId());
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
		Card res = CardService.findCardById(id);
		assertEquals(comp, res);
	}
	
	@Test
	@Transactional
	public void testSaveCard() {
		int id = 22;
		CardType ct= CardType.RUM;
		CardType actual = CardService.findCardById(id).getCardType();
		assertNotEquals(ct,actual);
		Card card = CardService.findCardById(id);
		card.setCardType(ct);
		assertEquals(ct,card.getCardType());
		CardService.saveCard(card);
		card = CardService.findCardById(id);
		assertEquals(ct,card.getCardType());
	}
}
	
	

