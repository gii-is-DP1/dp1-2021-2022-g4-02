package sevenisles.island;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IslandService {

	private IslandRepository islandRepo;
		
	@Autowired
	public IslandService(IslandRepository islandRepo) {
		this.islandRepo = islandRepo;
	}
	
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
	
	/*@Transactional
	public void asignacionInicialIslas(Game game) {
		Iterator<Island> it = islandFindAll().iterator();
		List<Island> li = StreamSupport.stream(Spliterators.spliteratorUnknownSize(it,Spliterator.ORDERED), false).collect(Collectors.toList());
		List<IslandStatus> ls = new ArrayList<IslandStatus>();
		for(int i = 0;i<li.size();i++) {
			Island island = li.get(i);
			IslandStatus status = new IslandStatus();
			status.setIsland(island);
			status.setGame(game);
			islandStatusService.saveIslandStatus(status);
			ls.add(status);
		}
		game.setIslandStatus(ls);
	}*/

}
