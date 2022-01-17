package sevenisles.statistics;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sevenisles.achievements.Achievement;
import sevenisles.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "statistics")
public class Statistics extends BaseEntity{
	
	private Integer userId;
	private Integer gamesPlayed;
	private Integer gamesWon;
	private Integer gamesLost;
	private Double averageScore;
	
	private List<Achievement> achievement;

}
