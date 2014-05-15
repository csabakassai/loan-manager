package hu.bme.aut.loanmanager.panels;

import hu.bme.aut.loanmanager.pages.HomePage;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;


public class HeaderPanel extends AbstractLoanManagerPanel {

	public HeaderPanel() {
		super("headerPanel");
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		addLoggedInUser();
		addLogoutLink();
		addPageName();
	}

	private void addPageName() {
		add(new Label("pageName", getAbstractLoggedInPage().getPageName()));
	}

	private void addLogoutLink() {
		Link<Void> link = new Link<Void>("logoutLink"){

			@Override
			public void onClick() {
				exit();
				getSession().invalidate();
				setResponsePage(HomePage.class);
			}

		};
		add(link);
	}

	private void addLoggedInUser() {
		String fullName = getLoggedInUser().getFullName();
		add(new Label("loggedInUser", Model.of(fullName)));
	}

}
