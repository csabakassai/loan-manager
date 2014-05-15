package hu.bme.aut.loanmanager.panels;

import hu.bme.aut.loanmanager.application.LoanManagerWebSession;
import hu.bme.aut.loanmanager.pages.AbstractLoanManagerPage;
import hu.bme.aut.loanmanager.pages.AbstractLoggedInPage;
import hu.bme.loanmanager.domain.io.UserVO;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class AbstractLoanManagerPanel extends Panel {

	public AbstractLoanManagerPanel(final String id) {
		super(id);
	}

	public AbstractLoanManagerPanel(final String id, final IModel<?> model) {
		super(id, model);
	}

	protected AbstractLoanManagerPage getLoanManagerPage() {
		return (AbstractLoanManagerPage) super.getPage();
	}

	protected AbstractLoggedInPage getAbstractLoggedInPage() {
		return (AbstractLoggedInPage) super.getPage();
	}

	protected LoanManagerWebSession getLoanManagerWebSession() {
		return getLoanManagerPage().getLoanManagerWebSession();
	}

	protected UserVO getLoggedInUser() {
		return getLoanManagerPage().getLoggedInUser();
	}

	protected void exit() {
		getLoanManagerPage().getLoanManagerWebSession().setUser(null);
	}

}
