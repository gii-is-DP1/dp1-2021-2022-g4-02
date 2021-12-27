package sevenisles.card;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface CardRepository extends CrudRepository<Card, Integer>{
	
	@Query("SELECT c FROM Card c WHERE c.cardType=0")
	public List<Card> findDoubloons();
	
	@Query("SELECT DISTINCT c.cardType FROM Card c")
	public List<CardType> findAllCardType();
	
}
