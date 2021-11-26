package sevenisles.game;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import sevenisles.model.BaseEntity;
import sevenisles.card.Card;
import sevenisles.island.Island;
import sevenisles.player.Player;
import sevenisles.status.Status;
import sevenisles.util.RandomChain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends BaseEntity {
	
	private Integer currentPlayer;

//	private final IslandService islandService = new IslandService();
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
	
	@OneToMany(cascade = CascadeType.ALL,targetEntity = Island.class)
	private List<Island> islands;
	
	@OneToMany(cascade = CascadeType.ALL,targetEntity = Card.class)
	private List<Card> cards;
	
	
//	@OneToMany(cascade = CascadeType.ALL,targetEntity = Player.class)
//	private List<Player> players;
	
	@OneToMany(cascade= CascadeType.ALL, targetEntity = Status.class)
	private List<Status> status;


	@Column(name="start_hour")
	private  LocalTime startHour;
	
	@Column(name="end_hour")
	private LocalTime endHour;
	
	private String code = RandomChain.randomChain(6);
		
//	public void nextPlayer() {
//		this.currentPlayer = (this.currentPlayer+1)%this.countPlayers();
//	}

	@Override
	public String toString() {
		return "Game [currentPlayer=" + currentPlayer + " startHour=" + startHour + ", endHour=" + endHour + ", code=" + code + "]";
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
//		return this.endHour != null;
//	}
	 
}
