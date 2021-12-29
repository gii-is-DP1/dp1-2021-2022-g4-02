package sevenisles.card;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	
	public List<Card> findDoubloonsInHand(List<Card> hand){
		return hand.stream().filter(x->x.getCardType().equals(CardType.DOBLON)).collect(Collectors.toList());
	}
	
	public List<Card> findDoubloons(){
		return cardRepository.findDoubloons();
	}

}
