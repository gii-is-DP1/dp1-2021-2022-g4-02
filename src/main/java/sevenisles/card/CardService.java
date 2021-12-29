package sevenisles.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {
	
	private CardRepository cardRepository;
	
	
	@Autowired
	public CardService(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}
	
	@Transactional(readOnly = true)
	public Integer cardCount() {
		return (int) cardRepository.count();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Card> cardFindAll() {
		return cardRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<CardType> findAllCardType(){
		return cardRepository.findAllCardType();
	}
	
	@Transactional(readOnly = true)
	public Optional<Card> findCardById(int id) throws IllegalArgumentException { 
		return cardRepository.findById(id);
	}
	
	@Transactional
	public void saveCard(Card cardToUpdate) throws DataAccessException {
		cardRepository.save(cardToUpdate);
	}
	
	@Transactional
    public List<Card> llenarMazo(){
        Iterable<Card> cards = cardRepository.findAll();
        List<Card> mazo = StreamSupport.stream(Spliterators.spliteratorUnknownSize(cards.iterator(),Spliterator.ORDERED), false).collect(Collectors.toList());
        return mazo;
    }
	
	/*
	@Transactional
	public void repartoInicial(Game game) {
		//Reparto inicial a jugadores
		List<Status> status = game.getStatus();
		List<Card> doblones = this.cardRepository.findDoubloons();	
		for(int i=0;i<status.size();i++) {
			List<Card> hand = new ArrayList<Card>();
			for(int j=0;j<3;j++) {
				Card card = doblones.get(j);
				hand.add(card);
				gameService.deleteCardFromDeck(game.getId(), card.getId());
			}
			doblones = doblones.subList(3, doblones.size());
			Status s = status.get(i);
			s.setCards(hand);
			statusService.saveStatus(s);
		}
		//Reparto inicial a islas
		List<IslandStatus> lis = game.getIslandStatus();
		List<IslandStatus> l2 = new ArrayList<IslandStatus>();
		for(int i =0;i<lis.size();i++) {
			List<Card> deck = game.getCards();
			IslandStatus istatus = lis.get(i);
			Card card = deck.get((int)(deck.size()* Math.random()));
			istatus.setCard(card);
			islandStatusService.saveIslandStatus(istatus);
			l2.add(istatus);
			gameService.deleteCardFromDeck(game, card);
		}
		game.setIslandStatus(l2);
	}
	
	@Transactional
	public void llenarIsla(Game game, IslandStatus is) {
		List<Card> deck = game.getCards();
		if(deck.size()!=0) {
			Card card = deck.get((int)(deck.size()* Math.random()));
			is.setCard(card);
			islandStatusService.saveIslandStatus(is);
			gameService.deleteCardFromDeck(game, card);
		}
		else is.setCard(null);
		islandStatusService.saveIslandStatus(is);
	}
	*/
	
	@Transactional
	public List<Card> findDoubloonsInHand(List<Card> hand){
		return hand.stream().filter(x->x.getCardType().equals(CardType.DOBLON)).collect(Collectors.toList());
	}
	
	
	public List<CardType> findCardTypeInSet(List<Card> set){
		List<CardType> res = new ArrayList<CardType>();
		for(Card c: set) {
			if(!res.contains(c.getCardType())) res.add(c.getCardType());
		}
		return res;
	}
	
	public List<Card> findDoubloons(){
		return cardRepository.findDoubloons();
	}

}
