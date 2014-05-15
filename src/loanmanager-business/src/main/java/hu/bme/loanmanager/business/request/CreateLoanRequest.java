
package hu.bme.loanmanager.business.request;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author cskassai
 */
@Getter
@Setter
public class CreateLoanRequest extends AbstractLoanManagerRequest {
	
	private UUID ownerId;
	
	private BigDecimal amount;
	
	private Date creationDate;
	
}
