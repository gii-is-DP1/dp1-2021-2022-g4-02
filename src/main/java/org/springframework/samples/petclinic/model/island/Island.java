package org.springframework.samples.petclinic.model.island;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.card.Card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "isles")
public class Island extends BaseEntity{
	@NotEmpty
	private Integer island;
//	@OneToOne(mappedBy = "cardId")
//	private Card card;
	
}
