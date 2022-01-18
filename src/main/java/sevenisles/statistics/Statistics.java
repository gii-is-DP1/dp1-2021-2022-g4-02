package sevenisles.statistics;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
//import sevenisles.achievements.Achievement;
import sevenisles.model.BaseEntity;
import sevenisles.player.Player;

@Getter
@Setter
@Entity
@Table(name = "statistics")
public class Statistics extends BaseEntity{
	
	@OneToOne
	private Player player;
	
	private Integer gamesPlayed = 0;
	private Integer gamesWon = 0;
	private Double averageScore = 0.0;
	private long averageTime = 0L;
	private Integer totalScore = 0;
	private long totalTime = 0L;
	
	private Integer doubloonCount = 0;
	private Integer chaliceCount = 0;
	private Integer diamondCount = 0;
	private Integer rubyCount = 0;
	private Integer rumCount = 0;
	private Integer gunCount = 0;
	private Integer necklaceCount = 0;
	private Integer swordCount = 0;
	private Integer mapCount = 0;
	private Integer crownCount = 0;
	
	/*
	private List<Achievement> achievement;
*/
}
