package org.springframework.samples.petclinic.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities extends BaseEntity{
	
//  @OneToOne(cascade = CascadeType.ALL)
//  @JoinColumn(name = "lobby_id", referencedColumnName = "id")
//  private Lobby lobbies;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	User user;
	
	@Size(min = 3, max = 50)
	String authority;
	
	public String toString() {
		return this.authority;
	}
}
