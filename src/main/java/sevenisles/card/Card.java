package sevenisles.card;


import java.util.List; 

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import sevenisles.game.Game;
import sevenisles.islandStatus.IslandStatus;
import sevenisles.model.BaseEntity;
import sevenisles.status.Status;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card extends BaseEntity{
	@NotEmpty
	private CardType cardType;
	
	@NotEmpty
	private String urlCardImg;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="card", targetEntity= IslandStatus.class)
	private List<IslandStatus> islandStatuses;
	
	@ManyToMany(targetEntity = Status.class,mappedBy="cards")
	private List<Status> status;
	
	@ManyToMany(targetEntity = Game.class,mappedBy="cards")
	private List<Game> game;

}
