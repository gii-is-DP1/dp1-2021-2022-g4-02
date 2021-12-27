package sevenisles.auditory;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import sevenisles.model.BaseEntity;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass

public class UserAuditory extends BaseEntity {
	@CreatedBy private String creator;
	@CreatedDate private LocalDateTime createdDate;
	@LastModifiedBy private String modifier;
	@LastModifiedDate private LocalDateTime lastModifiedDate;
	
}
