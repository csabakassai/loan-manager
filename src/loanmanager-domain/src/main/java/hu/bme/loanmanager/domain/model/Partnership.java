package hu.bme.loanmanager.domain.model;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "partnership")
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class Partnership extends AbstractUuidIdentifiable {
	
	@ManyToOne(targetEntity=LoanManagerUser.class, fetch = FetchType.LAZY, optional = false)
	@Fetch(FetchMode.SELECT)
	private LoanManagerUser initiator;
	
	@ManyToOne(targetEntity=LoanManagerUser.class, fetch = FetchType.LAZY, optional = false)
	@Fetch(FetchMode.SELECT)
	private LoanManagerUser acceptor;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date initiationDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date decisionDate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PartnershipStatus status;

}
