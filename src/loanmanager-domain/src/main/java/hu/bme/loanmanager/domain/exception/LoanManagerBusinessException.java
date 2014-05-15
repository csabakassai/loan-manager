package hu.bme.loanmanager.domain.exception;

import com.google.common.base.Preconditions;

public class LoanManagerBusinessException extends Exception {

	public LoanManagerBusinessException(final String errorMessage) {
		super(errorMessage);
		Preconditions.checkNotNull(errorMessage);
	}
	
	public String getErrorMessage() {
		return super.getMessage();
	}
	
}
