package sevenisles.user;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface UserRepository extends  CrudRepository<User, Integer>{
	
	
	@Query(value = "SELECT u FROM Users u WHERE u.username == ?1", nativeQuery = true)
	User findCurrentUser(String currentUsername) throws DataAccessException;
}
