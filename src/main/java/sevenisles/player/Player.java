package sevenisles.player;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

import sevenisles.card.Card;
import sevenisles.game.Game;
import sevenisles.model.BaseEntity;
import sevenisles.user.User;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	private Integer diceNumber;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Game.class)
	private Game game;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Card.class, mappedBy="player")
	private List<Card> cards;

	public Integer throwDice() {
		this.diceNumber = ThreadLocalRandom.current().nextInt(1, 7);
		return diceNumber;
	}
	
}
