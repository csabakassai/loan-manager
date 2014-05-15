
package hu.bme.loanmanager.domain.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( name = "loan" )
@Access( AccessType.FIELD )
@Getter
@Setter
@NoArgsConstructor
public class Loan extends AbstractUuidIdentifiable {
	
	private String description;
	
	private Date loanDate;
	
	@Temporal(TemporalType.DATE)
	private Date deadLine;
	
	@ManyToOne( targetEntity = LoanManagerUser.class, fetch = FetchType.LAZY, optional = false )
	private LoanManagerUser loanOwner;
	
	private BigDecimal amount;
	
}
