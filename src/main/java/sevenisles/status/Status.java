package sevenisles.status;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;
import sevenisles.card.Card;
import sevenisles.game.Game;
import sevenisles.model.BaseEntity;
import sevenisles.player.Player;

@Getter
@Setter
@Entity
@Table(name= "status")
public class Status extends BaseEntity{
	
	@ManyToOne(cascade = CascadeType.ALL,targetEntity = Game.class)
	private Game game;
	
	@ManyToOne(targetEntity = Player.class)
	private Player player;
	
	private Integer score;

	@ManyToMany(cascade = CascadeType.ALL, targetEntity = Card.class)
	@JoinTable(name="hand",uniqueConstraints = { @UniqueConstraint(columnNames = { "cards_id", "status_id" }) })
	private List<Card> cards;
	
	private Integer diceNumber;
	private Integer chosenIsland;
	private Integer numberOfCardsToPay;
	
	
}
