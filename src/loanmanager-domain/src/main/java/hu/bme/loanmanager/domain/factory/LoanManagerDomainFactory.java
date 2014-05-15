
package hu.bme.loanmanager.domain.factory;

import hu.bme.loanmanager.domain.factory.internal.LoanManagerDomainFactoryImpl;
import hu.bme.loanmanager.domain.model.Loan;
import hu.bme.loanmanager.domain.model.LoanPart;
import hu.bme.loanmanager.domain.model.LMMessage;
import hu.bme.loanmanager.domain.model.Partnership;
import hu.bme.loanmanager.domain.model.LoanManagerUser;

import java.math.BigDecimal;
import java.util.Date;

import com.google.inject.ImplementedBy;

/**
 * 
 * @author cskassai
 */
@ImplementedBy( LoanManagerDomainFactoryImpl.class )
public interface LoanManagerDomainFactory {
	
	LoanManagerUser createUser( String name, String userName, String password, String emailAdress );
	
	Partnership createPartnership(LoanManagerUser initiator, LoanManagerUser acceptor, Date initiationDate);

	Loan createLoan(LoanManagerUser loanOwner, Date loanDate, BigDecimal amount,
			String description);

	LoanPart createLoanPart(LoanManagerUser depter, BigDecimal amount, Loan loan);

	LMMessage createMessage(LoanManagerUser user, String message);
	
}
