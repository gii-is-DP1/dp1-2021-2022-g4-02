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
	private Integer maxRounds;
	private Integer currentRound;
	private Integer finishedTurn=0;
	
	@ManyToMany(cascade = CascadeType.ALL,targetEntity = Card.class)
	@JoinTable(name="deck", uniqueConstraints = { @UniqueConstraint(columnNames = { "game_id", "cards_id" }) })
	private List<Card> cards;
		
	@OneToMany(cascade= CascadeType.ALL, targetEntity = Status.class,mappedBy="game")
	private List<Status> status;
	
	@OneToMany(cascade= CascadeType.ALL, targetEntity = IslandStatus.class,mappedBy="game")
	private List<IslandStatus> islandStatus;


	@Column(name="start_hour")
	private  LocalTime startHour;
	
	@Column(name="end_hour")
	private LocalTime endHour;
	
	private String code = RandomChain.randomChain(6);
	private Integer gameMode; // 0 = normal, 1= secondary

	@Override
	public String toString() {
		return "Game [currentPlayer=" + currentPlayer + " startHour=" + startHour + ", endHour=" + endHour + ", code=" + code + "]";
	}
	
	 
}
