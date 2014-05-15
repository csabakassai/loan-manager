package hu.bme.loanmanager.domain.io;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.google.common.collect.Lists;

@Getter
@Setter
@NoArgsConstructor
public class LoanVO extends AbstractLoanManagerVO {
	
	private UserVO loanOwner;
	
	private Date loanDate;
	
	private Date deadLine;
	
	private int loanAmount;
	
	private final List<LoanPartVO> loanParts = Lists.newArrayList();
	
	private String description;
	
	private UUID loanId;

}
