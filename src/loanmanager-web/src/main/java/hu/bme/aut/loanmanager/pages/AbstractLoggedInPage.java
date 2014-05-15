package hu.bme.aut.loanmanager.pages;

import hu.bme.aut.loanmanager.panels.HeaderPanel;
import hu.bme.aut.loanmanager.panels.MenuPanel;

public abstract class AbstractLoggedInPage extends AbstractLoanManagerPage {

	public AbstractLoggedInPage() {
		super();
		if(getLoggedInUser() == null) {
			setResponsePage(HomePage.class);
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new HeaderPanel());
		add(new MenuPanel());
	}

	public abstract String getPageName();

}
