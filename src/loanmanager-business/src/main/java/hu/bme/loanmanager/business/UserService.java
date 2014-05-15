
package hu.bme.loanmanager.business;

import hu.bme.loanmanager.business.internal.UserServiceImpl;
import hu.bme.loanmanager.domain.io.UserVO;

import java.util.UUID;

import com.google.inject.ImplementedBy;

/**
 * 
 * @author cskassai
 */
@ImplementedBy( UserServiceImpl.class )
public interface UserService {
	
	void registerUser( UserVO request );

	UserVO loginUser(String userName, String password);
	
	void addMessage(UUID userId, MessageTemplates template, String userName);
	
}
