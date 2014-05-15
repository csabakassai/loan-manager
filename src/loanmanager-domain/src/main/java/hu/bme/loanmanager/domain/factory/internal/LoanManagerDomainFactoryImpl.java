
package hu.bme.loanmanager.domain.factory.internal;

import hu.bme.loanmanager.domain.factory.LoanManagerDomainFactory;
import hu.bme.loanmanager.domain.model.Loan;
import hu.bme.loanmanager.domain.model.LoanManagerUser;
import hu.bme.loanmanager.domain.model.LoanPart;
import hu.bme.loanmanager.domain.model.LoanStatus;
import hu.bme.loanmanager.domain.model.LMMessage;
import hu.bme.loanmanager.domain.model.Partnership;
import hu.bme.loanmanager.domain.model.PartnershipStatus;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * 
 * @author cskassai
 */
public class LoanManagerDomainFactoryImpl implements LoanManagerDomainFactory {
	
	@Inject
	private EntityManager em;
	
	@Override
	public LoanManagerUser createUser( String name, String userName, String password, String emailAdress ) {
		LoanManagerUser user = new LoanManagerUser();
		
		user.setName( name );
		user.setUserName( userName );
		user.setPassword( password );
		user.setEmailAdress( emailAdress );
		
		return persist( user );
		
	}
	
	private <E> E persist( E entity ) {
		em.persist( entity );
		
		return entity;
	}

	@Override
	public Partnership createPartnership(LoanManagerUser initiator, LoanManagerUser acceptor, Date initiationDate) {
		Partnership partnership = new Partnership();
		partnership.setAcceptor(acceptor);
		partnership.setInitiator(initiator);
		partnership.setStatus(PartnershipStatus.NEW);
		partnership.setInitiationDate(initiationDate);
		
		return persist(partnership);
	}
	
	@Override
	public Loan createLoan(LoanManagerUser loanOwner, Date loanDate, BigDecimal amount, String description){
		Preconditions.checkNotNull(loanDate);
		Preconditions.checkNotNull(loanOwner);
		Preconditions.checkNotNull(amount);
		
		Loan loan = new Loan();
		loan.setLoanOwner(loanOwner);
		loan.setLoanDate(loanDate);
		loan.setAmount(amount);
		loan.setDescription(description);
		return persist(loan);
	}
	
	@Override
	public LoanPart createLoanPart(LoanManagerUser depter, BigDecimal amount, Loan loan){
		
		Preconditions.checkNotNull(depter);
		Preconditions.checkNotNull(amount);
		Preconditions.checkNotNull(loan);
		
		LoanPart loanPart = new LoanPart();
		
		loanPart.setLoan(loan);
		loanPart.setStatus(LoanStatus.NEW);
		loanPart.setAmount(amount);
		loanPart.setDepter(depter);
		return persist(loanPart);
	}

	@Override
	public LMMessage createMessage(LoanManagerUser user, String message) {
		LMMessage messageEntity = new LMMessage();
		messageEntity.setOwner(user);
		messageEntity.setMessage(message);
		messageEntity.setSeen(false);
		return persist(messageEntity);
	}
	
}
