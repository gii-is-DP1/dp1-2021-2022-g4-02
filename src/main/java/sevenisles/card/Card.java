package sevenisles.card;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import sevenisles.model.BaseEntity;
import sevenisles.island.Island;
import sevenisles.player.Player;
import sevenisles.status.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card extends BaseEntity{
	@NotEmpty
	private CardType cardType;
	
	@OneToOne(mappedBy = "card")
	private Island island;
	
	@ManyToOne(optional=true, targetEntity = Status.class)
	private Status status;
}
