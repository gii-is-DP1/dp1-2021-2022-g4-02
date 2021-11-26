package sevenisles.island;

import java.util.Iterator;
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

import sevenisles.card.Card;
import sevenisles.game.Game;
import sevenisles.island.exceptions.IslandNotFoundException;

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
	public Optional<Island> findIslandById(int id) throws IllegalArgumentException { 
		return islandRepo.findById(id);
	}
	
	@Transactional
	public void saveIsland(Island islandToUpdate) throws DataAccessException {
		islandRepo.save(islandToUpdate);
	}
	
	@Transactional
	public void deleteIsland(Island islandToDelete) throws DataAccessException {
		islandRepo.delete(islandToDelete);
	}
	
//	@Transactional(readOnly=true)
//	public Card getCardFromIsland (int id)  throws IslandNotFoundException{
//		Optional<Island> island = findIslandById(id);
//		if(island.isPresent()) {
//			Card card = island.get().getCard();
//			return card;
//		}
//		else {
//			throw new IslandNotFoundException();
//		}
//	}
//	
//	@Transactional
//	public void emptyIsland(int id) throws IslandNotFoundException{
//		Optional<Island> island = findIslandById(id);
//		if(island.isPresent()) {
//			island.get().setCard(null);
//		} else {
//			throw new IslandNotFoundException();
//		}
//	}
//	
//	@Transactional
//	public void fillIsland(int id, Card card) throws IslandNotFoundException {
//		Optional<Island> island = findIslandById(id);
//		if(island.isPresent()) {
//			island.get().setCard(card);
//		} else {
//			throw new IslandNotFoundException();
//		}
//	}
	
	@Transactional(readOnly = true)
	public void asignarIslas(Game game) {
		Iterator<Island> it = islandRepo.findAll().iterator();
		List<Island> ls = StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED), false).collect(Collectors.toList());
		game.setIslands(ls);
	}

}
