package sevenisles.user;



import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;




public interface AuthoritiesRepository extends  CrudRepository<Authorities, Integer>{
	
	@Query("SELECT auth FROM Authorities auth WHERE auth.user.id=:id")
	Optional<Authorities> findAuthByUser(Integer id);

	
}
