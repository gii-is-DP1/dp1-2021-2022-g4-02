package sevenisles.island;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "island", targetEntity= IslandStatus.class)
	private List<IslandStatus> islandStatuses;
	
}
