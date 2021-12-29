package sevenisles.card;


import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import sevenisles.game.Game;
import sevenisles.islandStatus.IslandStatus;
import sevenisles.model.BaseEntity;
import sevenisles.status.Status;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card extends BaseEntity{
	@NotEmpty
	private CardType cardType;//Crear tabla cardType que asocie codigo con String
	
	@NotEmpty
	private String urlCardImg;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="card", targetEntity= IslandStatus.class)
	private List<IslandStatus> islandStatuses;
	
	@ManyToMany(targetEntity = Status.class,mappedBy="cards")
	private List<Status> status;
	
	@ManyToMany(targetEntity = Game.class,mappedBy="cards")
	private List<Game> game;

	@Override
	public int hashCode() {
		return Objects.hash(cardType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		return cardType == other.cardType;
	}
	
	
}
