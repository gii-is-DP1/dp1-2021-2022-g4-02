package org.springframework.samples.petclinic.model.game;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;




public interface GameRepository extends CrudRepository<Game, Integer>{

	@Query("SELECT game FROM Game game WHERE game.fechaFinal IS NOT NULL")
	List<Game> findFinishedGames();
	
	@Query("SELECT game FROM Game game WHERE game.fechaFinal IS NULL")
	List<Game> findUnfinishedGames();
}
