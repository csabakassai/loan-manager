package hu.bme.loanmanager.domain.io;

import hu.bme.loanmanager.domain.model.LoanPart;
import hu.bme.loanmanager.domain.util.EntityFunctions;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class LoanPartVO extends AbstractLoanManagerVO {

	private UserVO debter;
	private int loanAmount;
	private Date endDate;
	
	private UUID loanPartId;
	private LoanVO loanVO;
	
	public LoanPartVO(LoanPart loanPart){
		endDate = loanPart.getLoan().getDeadLine();
		loanAmount = loanPart.getAmount().intValue();
		debter = EntityFunctions.getUserVOFunction().apply(loanPart.getDepter());
		loanVO = EntityFunctions.getLoanVoFunction().apply(loanPart.getLoan());
		loanPartId = loanPart.getId();
	}
	
}
