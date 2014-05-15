
package hu.bme.loanmanager.business.internal;

import hu.bme.loanmanager.business.MessageTemplates;
import hu.bme.loanmanager.business.UserService;
import hu.bme.loanmanager.domain.exception.LoanManagerBusinessException;
import hu.bme.loanmanager.domain.factory.LoanManagerDomainFactory;
import hu.bme.loanmanager.domain.io.UserVO;
import hu.bme.loanmanager.domain.io.UserVO;
import hu.bme.loanmanager.domain.model.LoanManagerUser;
import hu.bme.loanmanager.domain.query.BaseQueries;

import java.util.UUID;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * 
 * @author cskassai
 */
public class UserServiceImpl implements UserService {
	
	@Inject
	private Injector injector;
	
	@Inject
	private BaseQueries baseQueries;
	
	@Override
	public void registerUser( UserVO request ) {
		
		LoanManagerDomainFactory domainFactory = injector.getInstance( LoanManagerDomainFactory.class );
		
		domainFactory.createUser( request.getFullName(), request.getUsername(),
			request.getPassword(), request.getEmail() );
		
	}

	@Override
	public UserVO loginUser(String userName, String password) {
		UserVO user = baseQueries.findUser(userName, password);
		return user;
	}

	@Override
	public void addMessage(UUID userId, MessageTemplates template, String userName) {
		LoanManagerUser user = baseQueries.findEntityById(LoanManagerUser.class, userId);
		LoanManagerDomainFactory domainFactory = injector.getInstance( LoanManagerDomainFactory.class );
		String message = template.getMessage(userName);
		domainFactory.createMessage(user, message);
		
	}
	
}
