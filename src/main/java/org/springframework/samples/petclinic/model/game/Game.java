package org.springframework.samples.petclinic.model.game;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.util.RandomChain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends BaseEntity{
	
	@NotEmpty
	private Integer currentUser;
	
	

	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
	
	@NotEmpty
	@ManyToOne(cascade = CascadeType.ALL,targetEntity = User.class)
	private List<User> players;
	
	//private Boolean finished;
	//private Date date;
	private  LocalDate fechaComienzo ;
	private LocalDate fechaFinal;
	private String code = RandomChain.randomChain(6);
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "lobby_id", referencedColumnName = "id")
//    private Lobby lobbies;
	
	public void addPlayer(User player) {
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
		this.currentUser = (this.currentUser+1)%this.countPlayers();
	}

	/*
	public boolean isFinished() {
		return this.fechaFinal != null;
	}
	 */
}
