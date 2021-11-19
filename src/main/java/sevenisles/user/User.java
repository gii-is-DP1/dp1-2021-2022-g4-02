package sevenisles.user;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Getter;
import lombok.Setter;
import sevenisles.model.BaseEntity;
import sevenisles.model.Person;
import sevenisles.player.Player;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable{
	
	@NotEmpty
	@Column(unique=true)
	String username;
	@NotEmpty
	String password;
	
	@NotEmpty
	String firstName;
	@NotEmpty
	String lastName;
	
	boolean enabled;
	
	@OneToOne(optional=true,cascade = CascadeType.ALL,mappedBy = "user")
	Authorities authorities;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	Player player;
	
	public static String getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	

}
