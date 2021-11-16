package sevenisles.user;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface UserRepository extends  CrudRepository<User, Integer>{
	
	
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	Optional<User> findCurrentUser(String currentUsername) throws DataAccessException;
}
