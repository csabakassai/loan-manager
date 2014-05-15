
package hu.bme.loanmanager.ejb.internal;

import hu.bme.loanmanager.business.LoanService;
import hu.bme.loanmanager.business.PartnerService;
import hu.bme.loanmanager.business.UserService;
import hu.bme.loanmanager.domain.exception.LoanManagerBusinessException;
import hu.bme.loanmanager.domain.io.LoanPartVO;
import hu.bme.loanmanager.domain.io.LoanVO;
import hu.bme.loanmanager.domain.io.MessageVO;
import hu.bme.loanmanager.domain.io.UserVO;
import hu.bme.loanmanager.domain.model.LoanManagerUser;
import hu.bme.loanmanager.domain.model.LoanStatus;
import hu.bme.loanmanager.domain.model.PartnershipStatus;
import hu.bme.loanmanager.domain.module.LoanManagerDomainModule;
import hu.bme.loanmanager.domain.query.BaseQueries;
import hu.bme.loanmanager.domain.util.EntityFunctions;
import hu.bme.loanmanager.ejb.LoanManager;

import java.util.List;
import java.util.UUID;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * 
 * @author cskassai
 */
@Stateless
@Local( LoanManager.class )
@WebService
public class LoanManagerEJB implements LoanManager {
	
	@PersistenceContext( unitName = "hu.bme.loanmanager.jpa" )
	protected EntityManager em;
	
	private Injector buildInjector() {
		return Guice.createInjector( new LoanManagerDomainModule( em ) );
	}
	
	@Override
	public void registerUser( UserVO request ) throws LoanManagerBusinessException {
		buildInjector().getInstance( UserService.class ).registerUser( request );
		
	}
	
	@Override
	public UserVO loginUser( String userName, String password ) {
		return buildInjector().getInstance( UserService.class ).loginUser( userName, password);
		
	}
	
	@Override
	public List<MessageVO> messageList( UserVO user ) {
		return buildInjector().getInstance(BaseQueries.class).loadMessagesByUserId(user.getId());
	}
	
	@Override
	public List<UserVO> undecidedUsers( UserVO userVO ) {
		return buildInjector().getInstance( BaseQueries.class ).loadUsersByAcceptor(userVO.getId(), PartnershipStatus.NEW);
		
	}
	
	@Override
	public void rejectPartnership( UserVO userVO, UserVO removeUser ) {
		buildInjector().getInstance( PartnerService.class ).changePartnershipStatus(userVO.getId(), removeUser.getId(), 
				PartnershipStatus.REJECTED, PartnershipStatus.NEW);
		
	}
	
	@Override
	public void acceptPartnership( UserVO userVO, UserVO addUser ) {
		buildInjector().getInstance( PartnerService.class ).changePartnershipStatus(userVO.getId(), addUser.getId(), 
				PartnershipStatus.ACCEPTED, PartnershipStatus.NEW);
		
	}
	
	@Override
	public void partnershipRequest( UserVO userVO, UserVO addUser ) {
		buildInjector().getInstance( PartnerService.class ).partnershipRequest(userVO, addUser);
		
	}
	
	@Override
	public List<UserVO> partnerList( UserVO userVO ) {
		UUID loggedInUserId = userVO.getId();
		BaseQueries baseQueries = buildInjector().getInstance( BaseQueries.class );
		List<UserVO> initiators = baseQueries.loadUsersByAcceptor(loggedInUserId, PartnershipStatus.ACCEPTED);
		List<UserVO> acceptors = baseQueries.loadUsersByInitator(loggedInUserId, PartnershipStatus.ACCEPTED);
		
		List<UserVO> partners = Lists.newArrayList(initiators);
		partners.addAll(acceptors);
		return partners;
		
	}
	
	@Override
	public void removeUserFromPartnerList( UserVO userVO, UserVO removeUser ) {
		buildInjector().getInstance( PartnerService.class ).removeUserFromPartnerList(userVO.getId(), removeUser.getId());
		
	}
	
	@Override
	public List<UserVO> selectablePartners( UserVO userVO ) {
		return buildInjector().getInstance( PartnerService.class ).loadSelectablePartners(userVO.getId());
		
	}
	
	@Override
	public List<UserVO> yetUndecidedUsersList( UserVO userVO ) {
		return buildInjector().getInstance(BaseQueries.class).loadUsersByInitator(userVO.getId(), PartnershipStatus.NEW);
		
	}
	
	@Override
	public void addLoan( LoanVO loanVO ) {
		buildInjector().getInstance(LoanService.class).addLoan(loanVO);
		
	}
	
	@Override
	public List<LoanPartVO> loanList( UserVO userVO ) {
		return buildInjector().getInstance(BaseQueries.class).loadLoansByDepter(userVO.getId(), LoanStatus.ACCEPTED);
		
	}
	
	@Override
	public List<LoanPartVO> acceptableLoanList( UserVO userVO ) {
		return buildInjector().getInstance(BaseQueries.class).loadLoansByDepter(userVO.getId(), LoanStatus.NEW);
		
	}
	
	@Override
	public void acceptLoan( UserVO user, LoanPartVO loan ) {
		buildInjector().getInstance(LoanService.class).
			changeLoanStatus(loan.getLoanPartId(), LoanStatus.ACCEPTED, LoanStatus.NEW);
		
	}
	
	@Override
	public void rejectLoan( UserVO user, LoanPartVO loan ) {
		buildInjector().getInstance(LoanService.class).
		changeLoanStatus(loan.getLoanPartId(), LoanStatus.REJECTED, LoanStatus.NEW);
		
	}
	
	@Override
	public void equalLoan( UserVO user, LoanPartVO loan ) {
		buildInjector().getInstance(LoanService.class).
		changeLoanStatus(loan.getLoanPartId(), LoanStatus.BALANCE_ACCEPTED, LoanStatus.ACCEPTED);
		
	}
	
	@Override
	public List<LoanPartVO> rejectedLoanList( UserVO userVO ) {
		return buildInjector().getInstance(BaseQueries.class).loadLoansByOwner(userVO.getId(), LoanStatus.REJECTED);
		
	}
	
	@Override
	public List<LoanPartVO> acceptedLoanList( UserVO userVO ) {
		return buildInjector().getInstance(BaseQueries.class).loadLoansByOwner(userVO.getId(), LoanStatus.ACCEPTED);
		
	}
	
	@Override
	@WebMethod(exclude = true)
	public List<LoanPartVO> equalLoanList( UserVO userVO ) {
		return buildInjector().getInstance(BaseQueries.class).loadLoansByOwner(userVO.getId(), LoanStatus.BALANCE_ACCEPTED);

		
	}
	
	@Override
	@WebMethod(exclude = true)
	public List<LoanPartVO> waitedLoanList( UserVO userVO ) {
		return buildInjector().getInstance(BaseQueries.class).loadLoansByOwner(userVO.getId(), LoanStatus.NEW);

		
	}

	@Override
	@WebMethod()
	public List<UserVO> loadAllUser() {
		List<LoanManagerUser> userEntities = buildInjector().getInstance(BaseQueries.class).loadAll(LoanManagerUser.class);
		List<UserVO> users = Lists.transform(userEntities, EntityFunctions.getUserVOFunction());
		return users;
	}
	
}
