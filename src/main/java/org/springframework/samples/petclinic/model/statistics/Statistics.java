package org.springframework.samples.petclinic.model.statistics;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.samples.petclinic.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

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

}
