
package hu.bme.loanmanager.domain.query;

import hu.bme.loanmanager.domain.io.LoanPartVO;
import hu.bme.loanmanager.domain.io.MessageVO;
import hu.bme.loanmanager.domain.io.UserVO;
import hu.bme.loanmanager.domain.model.AbstractIdentifiable;
import hu.bme.loanmanager.domain.model.LoanStatus;
import hu.bme.loanmanager.domain.model.Partnership;
import hu.bme.loanmanager.domain.model.PartnershipStatus;
import hu.bme.loanmanager.domain.query.hibernate.HibernateBaseQueries;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.google.inject.ImplementedBy;

/**
 * 
 * @author cskassai
 */
@ImplementedBy(HibernateBaseQueries.class)
public interface BaseQueries {
	
	<ID extends Serializable, E extends AbstractIdentifiable<ID>> E findEntityById( Class<E> entityClass, ID id );
	
	<ID extends Serializable, E extends AbstractIdentifiable<ID>> List<E> loadAll(Class<E> entityClass);
	
	UserVO findUser(String userName, String password);
	
	List<UserVO> loadUsersByInitator(UUID initiatorId, PartnershipStatus... statuses);
	
	List<UserVO> loadUsersByAcceptor(UUID acceptorId, PartnershipStatus... statuses);
	
	Partnership loadPartnershipByUsers(UUID initiatorId, UUID acceptorId);
	
	List<LoanPartVO> loadLoansByOwner(UUID ownerId, LoanStatus... statuses);
	
	List<LoanPartVO> loadLoansByDepter(UUID depterId, LoanStatus... statuses);
	
	List<MessageVO> loadMessagesByUserId(UUID userId);

}
