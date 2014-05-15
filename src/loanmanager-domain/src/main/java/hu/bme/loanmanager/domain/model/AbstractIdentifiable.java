
package hu.bme.loanmanager.domain.model;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@EqualsAndHashCode
public abstract class AbstractIdentifiable<ID extends Serializable> {
	
	private ID id;
	
}
