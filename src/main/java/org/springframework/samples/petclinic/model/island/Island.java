package org.springframework.samples.petclinic.model.island;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "isles")
public class Island extends BaseEntity{
	private Integer card;
}
