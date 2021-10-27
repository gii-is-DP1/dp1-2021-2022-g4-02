package org.springframework.samples.petclinic.model;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Integer>{
	
	Card findById(int id) throws DataAccessException;
	
	void saveCard(Card card) throws DataAccessException;

}
