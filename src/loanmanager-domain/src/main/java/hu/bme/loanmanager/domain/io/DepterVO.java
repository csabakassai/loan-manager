
package hu.bme.loanmanager.domain.io;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author cskassai
 */
@Getter
@Setter
@NoArgsConstructor
public class DepterVO extends AbstractLoanManagerVO {
	
	private UUID personId;
	
	private int share;
	
}
