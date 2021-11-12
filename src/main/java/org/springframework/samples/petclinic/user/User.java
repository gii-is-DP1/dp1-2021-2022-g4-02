package org.springframework.samples.petclinic.user;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable{

	String username;
	
	String password;
	
	boolean enabled;
	
	@OneToOne(mappedBy = "user")
	Authorities authorities;
	
	public static String getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	

}
