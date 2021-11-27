package sevenisles.island;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import sevenisles.model.BaseEntity;
import sevenisles.card.Card;
import sevenisles.game.Game;
import sevenisles.islandStatus.IslandStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "isles")
public class Island extends BaseEntity{
	
	@OneToOne(optional=true,cascade = CascadeType.ALL,mappedBy = "card")
	private IslandStatus islandStatus;
	
//	@ManyToMany(targetEntity = Game.class,mappedBy="islands")
//	private List<Game> game;
}
