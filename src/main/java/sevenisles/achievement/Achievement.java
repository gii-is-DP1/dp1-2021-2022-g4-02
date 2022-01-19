package sevenisles.achievement;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import sevenisles.achievementStatus.AchievementStatus;
import sevenisles.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "achievements")
public class Achievement extends BaseEntity{
	
	@NotNull
	private AchievementType achievementType;
	
	@OneToMany(cascade = CascadeType.ALL,targetEntity=AchievementStatus.class, mappedBy="achievement")
	private List<AchievementStatus> achievementStatus;
   
}
