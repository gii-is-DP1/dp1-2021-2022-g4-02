package org.springframework.samples.petclinic.user;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;




public interface AuthoritiesRepository extends  CrudRepository<Authorities, Integer>{
	
	@Query("SELECT auth FROM Authorities auth WHERE auth.user.id=:id")
	Authorities findAuthByUser(Integer id);
	
}
