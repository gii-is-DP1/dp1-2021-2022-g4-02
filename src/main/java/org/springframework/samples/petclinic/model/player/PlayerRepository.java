package org.springframework.samples.petclinic.model.player;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.user.User;



public interface PlayerRepository extends CrudRepository<Player, Integer>{

	@Query(value = "SELECT u FROM Users u WHERE u.username LIKE CONCAT('%',:currentUsername,'%');", nativeQuery = true)
	User findCurrentUser(@Param("currentUsername") String currentUsername) throws DataAccessException;
	
}
