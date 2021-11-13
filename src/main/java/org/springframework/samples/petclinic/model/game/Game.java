package org.springframework.samples.petclinic.model.game;


import java.time.LocalDate; 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.card.Card;
import org.springframework.samples.petclinic.model.island.Island;
import org.springframework.samples.petclinic.model.player.Player;
import org.springframework.samples.petclinic.util.RandomChain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends BaseEntity{
	
	private Integer currentPlayer;

//	private final IslandService islandService = new IslandService();
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
	
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL,targetEntity = Island.class)
	private List<Island> islands;
	
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL,targetEntity = Card.class)
	private List<Card> cards;
	
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL,targetEntity = Player.class, mappedBy="game")
	private List<Player> players;

	private  LocalDate fecha_comienzo ;
	private LocalDate fecha_final;
	private String code = RandomChain.randomChain(6);
	
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
		this.currentPlayer = (this.currentPlayer+1)%this.countPlayers();
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
	
//	
//	public boolean isFinished() {
//		return this.fechaFinal != null;
//	}
	 
}
