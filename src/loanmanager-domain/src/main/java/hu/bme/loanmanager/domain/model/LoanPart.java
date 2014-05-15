
package hu.bme.loanmanager.domain.model;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 
 * @author cskassai
 */
@Entity
@Access( AccessType.FIELD )
@Getter
@Setter
@NoArgsConstructor
public class LoanPart extends AbstractUuidIdentifiable {
	
	@Column(nullable = false)
	private BigDecimal amount;
	
	@ManyToOne(targetEntity = Loan.class, fetch = FetchType.LAZY, optional = false)
	@Fetch(FetchMode.SELECT)
	private Loan loan;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LoanStatus status;
	
	@ManyToOne(targetEntity = LoanManagerUser.class, fetch = FetchType.LAZY, optional = false)
	@Fetch(FetchMode.SELECT)
	private LoanManagerUser depter;
	
}
