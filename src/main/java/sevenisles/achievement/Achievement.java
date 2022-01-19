package sevenisles.achievement;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import sevenisles.model.BaseEntity;
import sevenisles.statistics.Statistics;

@Getter
@Setter
@Entity
@Table(name = "achievements")
public class Achievement extends BaseEntity{
	
	@NotNull
	private AchievementType achievementType;
	
	@NotNull
	private Boolean achieved = false;
	
	@ManyToMany(targetEntity = Statistics.class,mappedBy="achievements")
	private List<Statistics> statistics;
   
}
