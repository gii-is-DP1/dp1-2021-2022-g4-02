package org.springframework.samples.petclinic.model.card;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.island.Island;
import org.springframework.samples.petclinic.model.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card extends BaseEntity{
	@NotEmpty
	private CardType cardType;
	
	@OneToOne(mappedBy = "card")
	private Island island;
	
	@ManyToOne(optional=true, targetEntity = Player.class)
	private Player player;
}
