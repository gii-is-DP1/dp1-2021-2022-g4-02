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

import lombok.Getter;
import lombok.Setter;

import sevenisles.card.Card;
import sevenisles.game.Game;
import sevenisles.model.BaseEntity;
import sevenisles.status.Status;
import sevenisles.user.User;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL,targetEntity=Status.class)
	private List<Status> status;
//	@OneToMany(cascade = CascadeType.ALL, targetEntity = Card.class, mappedBy="player")
//	private List<Card> cards;
	
//	private Integer diceNumber;
	
//	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Game.class)
//	private Game game;


//	public Integer throwDice() {
//		this.diceNumber = ThreadLocalRandom.current().nextInt(1, 7);
//		return diceNumber;
//	}
	
}
