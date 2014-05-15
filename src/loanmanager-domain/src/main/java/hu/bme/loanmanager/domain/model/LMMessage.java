package hu.bme.loanmanager.domain.model;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "message")
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class LMMessage extends AbstractUuidIdentifiable {
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	private boolean seen;
	
	private String message;
	
	@ManyToOne(optional = false)
	private LoanManagerUser owner;
	
	@PrePersist
	public void setTimestamp(){
		if(createdAt == null) {
			createdAt = new Date();
		}
	}

}
