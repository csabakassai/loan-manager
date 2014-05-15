
package hu.bme.loanmanager.business;

import java.util.UUID;

import com.google.inject.ImplementedBy;

import hu.bme.loanmanager.business.internal.LoanServiceImpl;
import hu.bme.loanmanager.domain.io.LoanVO;
import hu.bme.loanmanager.domain.model.LoanStatus;

/**
 * 
 * @author cskassai
 */

@ImplementedBy(LoanServiceImpl.class)
public interface LoanService {

	void addLoan(LoanVO loanVO);
	
	void changeLoanStatus(UUID loanPartId, LoanStatus newStatus, LoanStatus... expectedStatuses);
	
}
