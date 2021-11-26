package sevenisles.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sevenisles.game.Game;
import sevenisles.game.GameService;
import sevenisles.status.Status;
import sevenisles.status.StatusService;

@Service
public class CardService {
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private StatusService statusService;
	
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
	
	public void repartoInicial(Game game) {
		List<Status> status = game.getStatus();
		List<Card> doblones = this.cardRepository.findDoubloons();	
		for(int i=0;i<status.size();i++) {
			List<Card> hand = new ArrayList<Card>();
			for(int j=0;j<3;j++) {
				Card card = doblones.get(j);
				hand.add(card);
				gameService.deleteCard(game.getId(), card.getId());
			}
			doblones = doblones.subList(3, doblones.size());
			Status s = status.get(i);
			s.setCards(hand);
			statusService.saveStatus(s);
		}
	}
}
