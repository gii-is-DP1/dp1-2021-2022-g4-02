package org.springframework.samples.petclinic.model.player;


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

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.card.Card;
import org.springframework.samples.petclinic.model.game.Game;
import org.springframework.samples.petclinic.model.island.Island;
import org.springframework.samples.petclinic.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends BaseEntity{
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	
	private Integer diceNumber;
	
	@NotEmpty
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Game.class)
	private Game games;
	
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Card.class, mappedBy="player")
	private List<Card> cards;

	public Integer throwDice() {
		this.diceNumber = ThreadLocalRandom.current().nextInt(1, 7);
		return diceNumber;
	}
	
}
