
package hu.bme.loanmanager.domain.io;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserVO extends AbstractLoanManagerVO {

	private String username;

	private String fullName;
	
	private String email;
	
	private String password;
	
	private UUID id;
	
	private boolean isAdmin;
	
	public UserVO(UUID id, String name, String userName, String emailAddress) {
		this.id = id;
		this.fullName = name;
		this.username = userName;
		this.email = emailAddress;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getFullName());
		builder.append(" - ");
		builder.append(getUsername());
		return builder.toString();
	}

}
