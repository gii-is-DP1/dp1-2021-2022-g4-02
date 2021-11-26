package sevenisles.status;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	
	@OneToOne
	@JoinColumn(name = "player_id", referencedColumnName = "id")
	private Player player;
	
	private Integer score;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Card.class)
	private List<Card> cards;
	
	private Integer diceNumber;
	
	public Integer throwDice() {
		this.diceNumber = ThreadLocalRandom.current().nextInt(1, 7);
		return diceNumber;
	}
	
	
	
}
