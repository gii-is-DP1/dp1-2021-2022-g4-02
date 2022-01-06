package sevenisles.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import sevenisles.card.Card;
import sevenisles.card.CardService;
import sevenisles.card.CardType;
import sevenisles.status.Status;

public class ScoreCountImpl implements ScoreCount{
	
	private CardService cardService;
	
	@Autowired
	public ScoreCountImpl(CardService cardService) {
		this.cardService = cardService;
	}
	
	private final List<Integer> points = Arrays.asList(1,3,7,13,21,30,40,50,60);

	@Override
	public Map<String, List<Card>> normalGameMode(Status status) {
		Integer i = 1;
		Map<String, List<Card>> map = new HashMap<String, List<Card>>();
		List<Card> hand = status.getCards();
		List<Card> aux = new ArrayList<Card>(hand);
		List<Card> doubloons = cardService.findDoubloonsInHand(hand);
		map.put("doblones", doubloons);
		aux.removeAll(doubloons);
		while(!aux.isEmpty()) {
			map.put(i.toString(), new ArrayList<Card>());
			List<CardType> types = cardService.findAllCardType();
			List<Card> copy = new ArrayList<Card>(aux);
			for(Card c:aux) {
				if(types.contains(c.getCardType())) {
					List<Card> set = map.get(i.toString());
					set.add(c);
					types.remove(c.getCardType());
					copy.remove(c);
				}
			}
			aux = copy;
			i++;
		}
		return map;
	}
	
	
	//Modo secundario, gana el que tenga más tesoros distintos, desempatando con doblones
	@Override
	public Map<String, List<Card>> secondaryGameMode(Status status) {
		Integer i=1;
		Map<String, List<Card>> map = new HashMap<String, List<Card>>();
		List<Card> hand = status.getCards();
		List<Card> aux = new ArrayList<Card>(hand);
		List<Card> doubloons = cardService.findDoubloonsInHand(hand);
		map.put("doblones", doubloons);
		aux.removeAll(doubloons);
		map.put(i.toString(), new ArrayList<Card>());
		List<CardType> types = cardService.findAllCardType();
		for(Card c:aux) {
			if(types.contains(c.getCardType())) {
				List<Card> set = map.get(i.toString());
				set.add(c);
				types.remove(c.getCardType());
			}
		}
		i++;
		return map;
	}
	
	public List<Status> tiebreaker(List<Status> tiedPlayers) {
		Map<Integer,List<Status>> map = new HashMap<Integer, List<Status>>();
		for(int j=0;j<tiedPlayers.size();j++) {
			Status s = tiedPlayers.get(j);
			Integer count = cardService.findDoubloonsInHand(s.getCards()).size();
			
			if(map.containsKey(count)) {
				map.get(count).add(s);
			}else {
				List<Status> statuses = new ArrayList<Status>();
				statuses.add(s);	
				map.put(count, statuses);
			}

		}
		Integer max =map.keySet().stream().max(Comparator.naturalOrder()).get();
		return map.get(max);
	}

	public Integer countPoints(Map<String,List<Card>> map) {
		Integer res=0;
		res+=map.get("doblones").size();
		for(Integer i=1;i<map.keySet().size();i++) {
			res+=points.get(map.get(i.toString()).size()-1);
		}
		return res;
	}
	
}
