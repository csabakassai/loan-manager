package hu.bme.aut.loanmanager.application;

import hu.bme.loanmanager.domain.io.UserVO;
import hu.bme.loanmanager.ejb.LoanManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import com.google.common.base.Preconditions;

public class LoanManagerWebSession extends AuthenticatedWebSession {

	public LoanManagerWebSession(final Request request) {
		super(request);
	}

	private UserVO user;

	public UserVO getUser() {
		return user;
	}

	public void setUser(final UserVO user) {
		this.user = user;
	}

	@Override
	public Roles getRoles() {
		Roles roles = new Roles();
		if(user != null) {
			roles.add("user");
			if(user.isAdmin()) {
				roles.add("admin");
			}
			
		}
		return roles;
	}

	@Override
	public boolean authenticate(String username, String password) {
		Context jndi;
		try {
			jndi = new InitialContext();
			LoanManager manager = (LoanManager) jndi.lookup("java:global/loanmanager-app-0.0.1-SNAPSHOT/loanmanager-ejb-0.0.1-SNAPSHOT/LoanManagerEJB!hu.bme.loanmanager.ejb.LoanManager");
			Preconditions.checkNotNull(manager);
			user = manager.loginUser(username, password);
			return user != null;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

}
