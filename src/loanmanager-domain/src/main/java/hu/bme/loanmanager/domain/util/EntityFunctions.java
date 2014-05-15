package hu.bme.loanmanager.domain.util;

import hu.bme.loanmanager.domain.io.LoanVO;
import hu.bme.loanmanager.domain.io.UserVO;
import hu.bme.loanmanager.domain.model.Loan;
import hu.bme.loanmanager.domain.model.LoanManagerUser;

import com.google.common.base.Function;

public class EntityFunctions {
	
	public static Function<LoanManagerUser, UserVO> getUserVOFunction(){
		return UserVOFunction.INSTANCE;
	}
	
	
	private static class UserVOFunction implements Function<LoanManagerUser, UserVO> {

		private static UserVOFunction INSTANCE = new UserVOFunction();
		
		@Override
		public UserVO apply(LoanManagerUser input) {
			if(input == null) {
				return null;
			}
			UserVO userVO = new UserVO();
			userVO.setEmail(input.getEmailAdress());
			userVO.setFullName(input.getName());
			userVO.setId(input.getId());
			userVO.setUsername(input.getUserName());
			userVO.setAdmin(input.isAdmin());
			userVO.setPassword(input.getPassword());
			return userVO;
		}

		
	}
	
	public static Function<Loan, LoanVO> getLoanVoFunction(){
		return LoanVOFunction.INSTANCE;
	}
	
	
	private static class LoanVOFunction implements Function<Loan, LoanVO> {

		private static LoanVOFunction INSTANCE = new LoanVOFunction();
		
		@Override
		public LoanVO apply(Loan input) {
			LoanVO loanVO = new LoanVO();
			loanVO.setDeadLine(input.getDeadLine());
			loanVO.setDescription(input.getDescription());
			loanVO.setLoanAmount(input.getAmount().intValue());
			loanVO.setLoanDate(input.getLoanDate());
			loanVO.setLoanOwner(getUserVOFunction().apply(input.getLoanOwner()));
			loanVO.setLoanId(input.getId());
			return loanVO;
		}

		
	}

}
