package sevenisles.player;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import sevenisles.model.BaseEntity;
import sevenisles.statistics.Statistics;
import sevenisles.status.Status;
import sevenisles.user.User;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL,targetEntity=Status.class, mappedBy="player")
	private List<Status> status;
	
	@OneToOne(cascade=CascadeType.ALL,targetEntity=Statistics.class, mappedBy="player")
	private Statistics statistics;
	
}
