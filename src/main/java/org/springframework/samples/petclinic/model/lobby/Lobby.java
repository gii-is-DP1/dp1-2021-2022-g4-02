package org.springframework.samples.petclinic.model.lobby;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.game.Game;
import org.springframework.samples.petclinic.model.player.Player;

import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
//@Entity
//@Table(name = "lobbies")
//public class Lobby extends BaseEntity{
//	@NotEmpty
//	private List<Player> players;
//	@NotEmpty
//	private String code;
//	@OneToOne(mappedBy = "lobbies")
//	private Game game;
//}
