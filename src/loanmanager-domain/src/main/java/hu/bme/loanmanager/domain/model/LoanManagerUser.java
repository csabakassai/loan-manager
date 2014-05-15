
package hu.bme.loanmanager.domain.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( name = "loanmanager_user" )
@Access( AccessType.FIELD )
@Getter
@Setter
@NoArgsConstructor
public class LoanManagerUser extends AbstractUuidIdentifiable {
	
	private String userName;
	
	private String password;
	
	private String emailAdress;
	
	private String name;
	
	private boolean isAdmin;
}
