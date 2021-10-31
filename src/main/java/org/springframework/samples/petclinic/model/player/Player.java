package org.springframework.samples.petclinic.model.player;


import javax.persistence.Entity;  
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends BaseEntity{
	@NotEmpty
	private Integer userId;
}
