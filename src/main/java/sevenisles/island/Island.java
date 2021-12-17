package sevenisles.island;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import sevenisles.islandStatus.IslandStatus;
import sevenisles.model.BaseEntity;

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
