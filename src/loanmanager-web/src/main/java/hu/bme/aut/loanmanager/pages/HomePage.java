package hu.bme.aut.loanmanager.pages;

import org.apache.wicket.markup.html.link.Link;


public class HomePage extends AbstractLoanManagerPage {

	public HomePage() {
		super();
		if(getLoggedInUser() != null) {
			setResponsePage(MyLoanListPage.class);
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Link<Void>("login"){

			@Override
			public void onClick() {
				setResponsePage(LoanListPage.class);
			}

		});
		add(new Link<Void>("registration"){

			@Override
			public void onClick() {
				setResponsePage(RegistrationPage.class);
			}

		});

	}

}
