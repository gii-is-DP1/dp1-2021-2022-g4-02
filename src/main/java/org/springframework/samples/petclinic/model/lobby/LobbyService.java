package org.springframework.samples.petclinic.model.lobby;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LobbyService {
	@Autowired
	private LobbyRepository lobbyRepository;
	
	@Transactional(readOnly = true)
	public Integer lobbyCount() {
		return (int) lobbyRepository.count();
	}
	
	@Transactional(readOnly = true)
	public Iterable<Lobby> lobbyFindAll() {
		return lobbyRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Lobby findLobbyById(int id) throws IllegalArgumentException { 
		return lobbyRepository.findById(id).get();
	}
	
	@Transactional
	public void saveLobby(Lobby lobbyToUpdate) throws DataAccessException {
		lobbyRepository.save(lobbyToUpdate);
	}
}
