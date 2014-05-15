package hu.bme.aut.loanmanager.panels;

import hu.bme.aut.loanmanager.pages.AdminPage;
import hu.bme.aut.loanmanager.pages.LoanListPage;
import hu.bme.aut.loanmanager.pages.MessageListPage;
import hu.bme.aut.loanmanager.pages.MyLoanListPage;
import hu.bme.aut.loanmanager.pages.NewLoanPage;
import hu.bme.aut.loanmanager.pages.NewPartnerPage;
import hu.bme.aut.loanmanager.pages.UndecidedPartnerListPage;

import org.apache.wicket.authroles.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.link.Link;

public class MenuPanel extends AbstractLoanManagerPanel {

	public MenuPanel() {
		super("menuPanel");
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Link<Void>("newLoan") {

			@Override
			public void onClick() {
				setResponsePage(NewLoanPage.class);
			}

		});
		add(new Link<Void>("newPartner") {

			@Override
			public void onClick() {
				setResponsePage(NewPartnerPage.class);
			}

		});
		add(new Link<Void>("undecidedPartners") {

			@Override
			public void onClick() {
				setResponsePage(UndecidedPartnerListPage.class);
			}

		});
		add(new Link<Void>("myLoans") {

			@Override
			public void onClick() {
				setResponsePage(MyLoanListPage.class);
			}

		});
		add(new Link<Void>("messages") {

			@Override
			public void onClick() {
				setResponsePage(MessageListPage.class);
			}

		});
		add(new Link<Void>("incomingLoans") {

			@Override
			public void onClick() {
				setResponsePage(LoanListPage.class);
			}

		});
		
		AdminPageLink adminLink = new AdminPageLink("adminLink");
		add(adminLink);
		MetaDataRoleAuthorizationStrategy.authorize(adminLink, RENDER, "admin");
	}
	
	private class AdminPageLink extends Link<Void> {

		public AdminPageLink(String id) {
			super(id);
		}

		@Override
		public void onClick() {
			setResponsePage(AdminPage.class);
			
		}
		
	}

}
