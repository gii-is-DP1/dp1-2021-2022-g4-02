package sevenisles.card;

import java.util.List;
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
	@Autowired
	private CardRepository cardRepository;
	
	@Transactional(readOnly = true)
	public Integer cardCount() {
		return (int) cardRepository.count();
	}
	
	@Transactional(readOnly = true)
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
	
	@Transactional
    public List<Card> llenarMazo(){
        Iterable<Card> cards = cardRepository.findAll();
        List<Card> mazo = StreamSupport.stream(Spliterators.spliteratorUnknownSize(cards.iterator(),Spliterator.ORDERED), false).collect(Collectors.toList());
        return mazo;
    }
}
