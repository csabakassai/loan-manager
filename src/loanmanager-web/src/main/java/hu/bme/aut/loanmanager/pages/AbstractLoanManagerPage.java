package hu.bme.aut.loanmanager.pages;

import hu.bme.aut.loanmanager.application.LoanManagerWebSession;
import hu.bme.loanmanager.domain.io.UserVO;
import hu.bme.loanmanager.ejb.LoanManager;

import javax.ejb.EJB;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public abstract class AbstractLoanManagerPage extends WebPage {

	protected FeedbackPanel mainFeedbackPanel;

	@EJB(name = "LoanManagerEJB!hu.bme.loanmanager.ejb.LoanManager")
	protected LoanManager manager;

	public AbstractLoanManagerPage() {
		super();
//		Context jndi;
//		try {
//			jndi = new InitialContext();
//			manager = (LoanManager) jndi.lookup("java:global/loanmanager-app-0.0.1-SNAPSHOT/loanmanager-ejb-0.0.1-SNAPSHOT/LoanManagerEJB!hu.bme.loanmanager.ejb.LoanManager");
//			Preconditions.checkNotNull(manager);
//		} catch (NamingException e) {
//			throw new RuntimeException(e);
//		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		addFeedbackPanel();
	}

	private void addFeedbackPanel() {
		mainFeedbackPanel = new FeedbackPanel("mainFeedbackPanel");
		mainFeedbackPanel.setOutputMarkupId(true);
		add(mainFeedbackPanel);
	}

	protected void updateMainFeedbackPanel(final AjaxRequestTarget target) {
		target.add(mainFeedbackPanel);
	}

	public LoanManagerWebSession getLoanManagerWebSession() {
		return (LoanManagerWebSession) super.getSession();
	}

	public UserVO getLoggedInUser() {
		return ((LoanManagerWebSession) getLoanManagerWebSession().get()).getUser();
	}

	public LoanManager getManager() {
		return manager;
	}

}
