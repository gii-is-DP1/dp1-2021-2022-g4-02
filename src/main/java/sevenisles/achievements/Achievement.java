package sevenisles.achievements;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import sevenisles.model.BaseEntity;
import sevenisles.statistics.Statistics;

@Getter
@Setter
@Entity
@Table(name = "achievements")
public class Achievement extends BaseEntity{
	
	private AchievementType achievement;
	//private String name = achievement.name();
	//private String description = achievement.getDescription();
	private Boolean achieved = false;
	
	/*
	@ManyToOne
	private Statistics statistics;
	*/
}
