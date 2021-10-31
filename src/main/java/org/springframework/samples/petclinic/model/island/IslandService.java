package org.springframework.samples.petclinic.model.island;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
