package sevenisles.user;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Getter;
import lombok.Setter;
import sevenisles.auditory.UserAuditory;
import sevenisles.model.BaseEntity;
import sevenisles.player.Player;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends UserAuditory implements Serializable, Comparable<User>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@NotBlank
	@Column(unique=true)
	@Size(min = 3, max = 50)
	String username;
	
	@NotBlank
	String password;
	
	@NotBlank
	@Size(min = 3, max = 50)
	String firstName;
	@NotBlank
	@Size(min = 3, max = 50)
	String lastName;
	
	boolean enabled;
	
	@OneToOne(optional=true,cascade = CascadeType.ALL,mappedBy = "user")
	Authorities authorities;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	Player player;
	
	public static String getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@Override
	public int compareTo(User o) {
		return this.username.compareTo(o.getUsername());	
	}
	
	public boolean isNew() {
		return this.id == null;
	}

}
