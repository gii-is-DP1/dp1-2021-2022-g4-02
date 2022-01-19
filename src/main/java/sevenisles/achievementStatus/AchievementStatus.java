package sevenisles.achievementStatus;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import sevenisles.achievement.Achievement;
import sevenisles.model.BaseEntity;
import sevenisles.statistics.Statistics;

@Entity
@Getter
@Setter
@Table(name="achievement_statuses")
public class AchievementStatus extends BaseEntity{
	
	@ManyToOne(cascade=CascadeType.REFRESH, targetEntity = Statistics.class)
	private Statistics statistics;
	
	@ManyToOne(cascade = CascadeType.REFRESH,targetEntity = Achievement.class)
	private Achievement achievement;
	
	private Boolean achieved;

}
