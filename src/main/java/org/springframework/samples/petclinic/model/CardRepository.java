package org.springframework.samples.petclinic.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface CardRepository extends CrudRepository<Card, Integer>{
	
//	Card findById(int id) throws DataAccessException;
	
//	void save(Card card) throws DataAccessException;
	
	@Query("SELECT card FROM Card card  WHERE card.id =:id")
	public Card findById(@Param("id") int id);

}
