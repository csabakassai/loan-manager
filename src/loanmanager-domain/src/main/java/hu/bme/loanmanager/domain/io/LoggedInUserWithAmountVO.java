package hu.bme.loanmanager.domain.io;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class LoggedInUserWithAmountVO extends AbstractLoanManagerVO {

	private UserVO user;
	private int amount;

}
