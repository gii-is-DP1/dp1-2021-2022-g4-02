package org.springframework.samples.petclinic.model;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {
	@Autowired
	private CardRepository cardRepository;
	
	@Transactional
	public Integer cardCount() {
		return (int) cardRepository.count();
	}
	
	@Transactional
	public Iterable<Card> cardFindAll() {
		return cardRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Card findCardById(int id) throws IllegalArgumentException { 
		return cardRepository.findById(id).get();
	}
	
	@Transactional
	public void saveCard(Card cardToUpdate) throws DataAccessException {
		cardRepository.save(cardToUpdate);
	}
}
