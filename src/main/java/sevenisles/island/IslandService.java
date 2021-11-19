package sevenisles.island;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sevenisles.card.Card;

@Service
public class IslandService {
	@Autowired
	private IslandRepository islandRepo;
	
	@Transactional(readOnly = true)
	public Integer islandCount() {
		return (int) islandRepo.count();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Island> islandFindAll() {
		return islandRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Island findIslandById(int id) throws IllegalArgumentException { 
		return islandRepo.findById(id).get();
	}
	
	@Transactional
	public void saveIsland(Island islandToUpdate) throws DataAccessException {
		islandRepo.save(islandToUpdate);
 }
	
	@Transactional(readOnly=true)
	public Card getCardFromIsland (int id) {
		Island island = findIslandById(id);
		Card card = island.getCard();
		return card;
	}
	
	@Transactional
	public void emptyIsland(int id) {
		Island island = findIslandById(id);
		island.setCard(null);
	}
	
	@Transactional
	public void fillIsland(int id, Card card) {
		Island island = findIslandById(id);
		island.setCard(card);
	}

}
