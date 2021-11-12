package org.springframework.samples.petclinic.model.game;


import java.time.LocalDate; 
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.card.Card;
import org.springframework.samples.petclinic.model.island.Island;
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
	
	private Integer diceNumber;

//	private final IslandService islandService = new IslandService();
	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
	
	@NotEmpty
	@ManyToOne(cascade = CascadeType.ALL,targetEntity = Island.class)
	private List<Island> islands;
	
	@NotEmpty
	@ManyToOne(cascade = CascadeType.ALL,targetEntity = Card.class)
	private List<Island> cards;
	
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
	
	public Integer throwDice() {
		this.diceNumber = ThreadLocalRandom.current().nextInt(1, 7);
		return diceNumber;
	}

//	public Card lootIsland(Integer islandNumber) {
//		Card card;
//		if(islandNumber>=1 && islandNumber<=6) {
//			Island island = islandService.findIslandById(islandNumber);
//			try {
//				card = island.getCard();
//			}catch(Exception e) {
//				throw new IllegalArgumentException("This island is empty, choose another one.");
//			}
//		}else {
//			throw new IllegalArgumentException("You have to choose an island between 1 and 6.");
//		}
//		return card;
//	}
	
	/*
	public boolean isFinished() {
		return this.fechaFinal != null;
	}
	 */
}
