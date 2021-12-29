package sevenisles.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;

import sevenisles.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities extends BaseEntity{

	
	@OneToOne(optional=true)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	User user;
	
	@Size(min = 3, max = 50)
	String authority;
	
	public String toString() {
		return this.authority;
	}
}
