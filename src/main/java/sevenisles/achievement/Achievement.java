package sevenisles.achievement;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import sevenisles.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "achievements")
public class Achievement extends BaseEntity{

    @NotNull
    private AchievementType achievementType;

    @NotNull
    private Boolean achieved = false;
    
}