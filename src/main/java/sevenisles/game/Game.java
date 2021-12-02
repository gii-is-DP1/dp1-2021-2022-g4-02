package sevenisles.game;


import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;
import sevenisles.card.Card;
import sevenisles.island.Island;
import sevenisles.islandStatus.IslandStatus;
import sevenisles.model.BaseEntity;
import sevenisles.status.Status;
import sevenisles.util.RandomChain;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends BaseEntity {
	
	private Integer currentPlayer;
	private Integer initialPlayer;
	private Integer maxTurns;
	private Integer currentTurn;
	private Integer finishedTurn=0;
	
	@ManyToMany(cascade = CascadeType.ALL,targetEntity = Card.class)
	@JoinTable(name="deck", uniqueConstraints = { @UniqueConstraint(columnNames = { "game_id", "cards_id" }) })
	private List<Card> cards;
		
	@OneToMany(cascade= CascadeType.ALL, targetEntity = Status.class)
	@JoinTable(uniqueConstraints = { @UniqueConstraint(columnNames = { "game_id", "status_id" }) })
	private List<Status> status;
	
	@OneToMany(cascade= CascadeType.ALL, targetEntity = IslandStatus.class)
	@JoinTable(uniqueConstraints = { @UniqueConstraint(columnNames = { "game_id", "island_status_id" }) })
	private List<IslandStatus> islandStatus;


	@Column(name="start_hour")
	private  LocalTime startHour;
	
	@Column(name="end_hour")
	private LocalTime endHour;
	
	private String code = RandomChain.randomChain(6);

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
	
	 
}
