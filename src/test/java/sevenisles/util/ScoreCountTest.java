package sevenisles.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.game.GameService;
import sevenisles.status.Status;
import sevenisles.status.StatusService;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ScoreCountTest {
	
	@Autowired
	private GameService gameService;
	@Autowired
	private CardService cardService;
	@Autowired
	private StatusService statusService;
	
	List<Status> status = new ArrayList<Status>();
	List<Status> statuses = new ArrayList<Status>();
	
	@BeforeEach
	public void init() {
        List<Card> deck1 = new ArrayList<Card>();
        deck1.add(cardService.findCardById(1).get());
        deck1.add(cardService.findCardById(28).get());
        deck1.add(cardService.findCardById(28).get());
        deck1.add(cardService.findCardById(31).get());
        
        Status status1 = new Status();
        status1.setCards(deck1);
        statusService.saveStatus(status1);
        status.add(status1);
        statuses.add(status1);
        
        List<Card> deck2 = new ArrayList<Card>();
        deck2.add(cardService.findCardById(1).get());
        deck2.add(cardService.findCardById(1).get());
        deck2.add(cardService.findCardById(28).get());
        deck2.add(cardService.findCardById(28).get());
        
        Status status2 = new Status();
        status2.setCards(deck2);
        statusService.saveStatus(status2);
        status.add(status2);
        statuses.add(status2);
        
        List<Card> deck3 = new ArrayList<Card>();
        deck3.add(cardService.findCardById(1).get());
        deck3.add(cardService.findCardById(1).get());
        deck3.add(cardService.findCardById(28).get());
        deck3.add(cardService.findCardById(28).get());
        
        Status status3 = new Status();
        status3.setCards(deck3);
        statusService.saveStatus(status3);
        statuses.add(status3);
	}
	
	@Test
	public void testNormalGameMode() {
		Map<String, List<Card>> map = gameService.normalGameMode(status.get(0));
		
		Map<String, List<Card>> mapDeseado = new HashMap<String, List<Card>>();
		
		List<Card> listaDoblones = new ArrayList<Card>();
		listaDoblones.add(cardService.findCardById(1).get());
		mapDeseado.put("doblones", listaDoblones);
		
		List<Card> lista1 = new ArrayList<Card>();
		lista1.add(cardService.findCardById(28).get());
		lista1.add(cardService.findCardById(31).get());
		mapDeseado.put("1", lista1);
		
		List<Card> lista2 = new ArrayList<Card>();
		lista2.add(cardService.findCardById(28).get());
		mapDeseado.put("2", lista2);
		
		assertEquals(map, mapDeseado);
	}
	
	@Test
	public void testSecondaryGameMode() {
		Map<String, List<Card>> map = gameService.secondaryGameMode(status.get(0));
		
		Map<String, List<Card>> mapDeseado = new HashMap<String, List<Card>>();
		
		List<Card> listaDoblones = new ArrayList<Card>();
		listaDoblones.add(cardService.findCardById(1).get());
		mapDeseado.put("doblones", listaDoblones);
		
		List<Card> lista1 = new ArrayList<Card>();
		lista1.add(cardService.findCardById(28).get());
		lista1.add(cardService.findCardById(31).get());
		mapDeseado.put("1", lista1);
		
		assertEquals(map, mapDeseado);
	}
	
	@Test
	public void testCountPointsNormalMode() {
		Map<String, List<Card>> map = gameService.normalGameMode(status.get(0));
		
		Integer puntosDeseados = 5;
		Integer puntos = gameService.countPoints(map);
		
		assertEquals(puntosDeseados, puntos);
	}
	
	@Test
	public void testCountPointsSecondaryMode() {
		Map<String, List<Card>> map = gameService.secondaryGameMode(status.get(0));
		
		Integer puntosDeseados = 4;
		Integer puntos = gameService.countPoints(map);
		
		assertEquals(puntosDeseados, puntos);
	}
	
	@Test
	public void testTiebreaker() {
		List<Status> list = gameService.tiebreaker(status);
		
		assertEquals(list.size(), 1);
		assertEquals(list.get(0), status.get(1));
	}
	
	@Test
	public void testTiebreakerMultipleWinners() {
		List<Status> list = gameService.tiebreaker(statuses);
		
		assertEquals(list.size(), 2);
		assertEquals(list.get(0), statuses.get(1));
		assertEquals(list.get(1), statuses.get(2));
	}

}
