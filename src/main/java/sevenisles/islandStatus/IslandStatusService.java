package sevenisles.islandStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sevenisles.game.GameService;
import sevenisles.island.IslandService;

@Service
public class IslandStatusService {
	
	@Autowired
	IslandStatusRepository islandStatusRepo;
	
	@Autowired
	GameService gameService;
	
	@Autowired
	IslandService islandService;

	@Transactional(readOnly = true)
	public Integer islandStatusCount() {
		return (int) islandStatusRepo.count();
	}
	
	@Transactional(readOnly = true)
	public Iterable<IslandStatus> islandStatusFindAll() {
		return islandStatusRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<List<IslandStatus>> findIslandStatusByGame(int id) throws IllegalArgumentException { 
		return islandStatusRepo.findIslandStatusByGame(id);
	}
	
	@Transactional(readOnly = true)
	public Optional<IslandStatus> findIslandStatusByGameAndIsland(int gameId, int islandId) throws IllegalArgumentException { 
		return islandStatusRepo.findIslandStatusByGameAndIsland(gameId, islandId);
	}
	
	@Transactional
	public void saveIslandStatus(IslandStatus statusToUpdate) throws DataAccessException {
		islandStatusRepo.save(statusToUpdate);
	}
	
	@Transactional
	public void deleteIslandStatus(Integer id) throws DataAccessException {
		islandStatusRepo.deleteById(id);
	}
	
	@Transactional
	public void deleteIslandStatus(IslandStatus status) throws DataAccessException {
		islandStatusRepo.delete(status);
	}
	

	
	

}
