
package hu.bme.loanmanager.domain.model;

import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Type;

@MappedSuperclass
public class AbstractUuidIdentifiable extends AbstractIdentifiable<UUID> {
	
	@PrePersist
	public void generateIdIfNotSpecified() {
		if (getId() == null) {
			final UUID generatedId = UUID.randomUUID();
			setId( generatedId );
		}
	}
	
	@Override
	@Access( AccessType.PROPERTY )
	@Id
	@Type( type = "uuid-char" )
	@Column( length = 36 )
	public UUID getId() {
		return super.getId();
	}
	
}
