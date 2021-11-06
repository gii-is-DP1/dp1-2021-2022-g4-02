package org.springframework.samples.petclinic.model.game;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends BaseEntity{
	
	@NotEmpty
	private Integer currentUserId;
	
	@NotEmpty

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "username")

	@ManyToOne(cascade = CascadeType.ALL,targetEntity = Player.class)

	private List<Player> players;
	private Boolean finished;
	private Date date;
	
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "lobby_id", referencedColumnName = "id")
//    private Lobby lobbies;
	
	public void addPlayer(Player player) {
		if(this.players == null) {
			this.players = new ArrayList<>();
		}
		players.add(player);
	}
	
	public int countPlayers() {
		return players.size();
	}
	
	public boolean isNotFull() {
		return this.countPlayers()<4;
	}
	
	public boolean isReadyToStart() {
		return this.countPlayers()>=2 && this.countPlayers()<=4;
	}
	
	public void nextPlayer() {
		this.currentUserId = (this.currentUserId+1)%this.countPlayers();
	}



}
