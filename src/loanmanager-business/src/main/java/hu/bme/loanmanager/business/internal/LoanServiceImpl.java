
package hu.bme.loanmanager.business.internal;

import hu.bme.loanmanager.business.LoanService;
import hu.bme.loanmanager.domain.factory.LoanManagerDomainFactory;
import hu.bme.loanmanager.domain.io.LoanPartVO;
import hu.bme.loanmanager.domain.io.LoanVO;
import hu.bme.loanmanager.domain.model.Loan;
import hu.bme.loanmanager.domain.model.LoanPart;
import hu.bme.loanmanager.domain.model.LoanStatus;
import hu.bme.loanmanager.domain.model.LoanManagerUser;
import hu.bme.loanmanager.domain.query.BaseQueries;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * 
 * @author cskassai
 */
public class LoanServiceImpl implements LoanService {
	
	@Inject
	private Injector injector;
	
	@Inject
	private LoanManagerDomainFactory domainFactory;
	
	@Inject
	private BaseQueries baseQueries;

	@Override
	public void addLoan(LoanVO loanVO) {
		LoanManagerUser loanOwner = baseQueries.findEntityById(LoanManagerUser.class, loanVO.getLoanOwner().getId());
		Preconditions.checkNotNull(loanOwner);
		int loanAmount = loanVO.getLoanAmount();
		
		Loan loan = domainFactory.createLoan(loanOwner , loanVO.getLoanDate(), new BigDecimal(loanAmount), loanVO.getDescription());
		
		List<LoanPartVO> loanParts = loanVO.getLoanParts();
		
		for (LoanPartVO loanPartVO : loanParts) {
			LoanManagerUser debter = baseQueries.findEntityById(LoanManagerUser.class, loanPartVO.getDebter().getId());
			Preconditions.checkNotNull(debter);
			domainFactory.createLoanPart(debter, new BigDecimal(loanPartVO.getLoanAmount()), loan);
		}
		
	}

	@Override
	public void changeLoanStatus(UUID loanPartId, LoanStatus newStatus,
			LoanStatus... expectedStatuses) {
		LoanPart loanPart = 
				baseQueries.findEntityById(LoanPart.class, loanPartId);
		
		Preconditions.checkArgument(
				Arrays.asList(expectedStatuses).contains(loanPart.getStatus()));
		
		loanPart.setStatus(newStatus);
	}
	
}
