package sevenisles.islandStatus;
 
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import sevenisles.card.Card;
import sevenisles.game.Game;
import sevenisles.island.Island;
import sevenisles.model.BaseEntity;

@Getter
@Setter
@Table(name="island_status")
@Entity
public class IslandStatus extends BaseEntity{
	
	@ManyToOne(targetEntity = Game.class)
	private Game game;

	@ManyToOne(targetEntity = Island.class)
	private Island island;
	
	@ManyToOne(targetEntity = Card.class)
	private Card card;
}
