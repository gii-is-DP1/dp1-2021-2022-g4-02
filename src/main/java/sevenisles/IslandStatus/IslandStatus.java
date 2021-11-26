package sevenisles.IslandStatus;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import sevenisles.card.Card;
import sevenisles.game.Game;
import sevenisles.island.Island;
import sevenisles.model.BaseEntity;
import sevenisles.player.Player;

@Entity
public class IslandStatus extends BaseEntity{
	
	@ManyToOne(cascade = CascadeType.ALL,targetEntity = Game.class)
	private Game game;

	@OneToOne(targetEntity = Island.class)
	private Island island;
	
	@OneToOne(targetEntity = Card.class)
	private Card card;
}
