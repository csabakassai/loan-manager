package hu.bme.aut.loanmanager.application;

import hu.bme.aut.loanmanager.pages.HomePage;
import hu.bme.aut.loanmanager.pages.LoanListPage;
import hu.bme.aut.loanmanager.pages.MessageListPage;
import hu.bme.aut.loanmanager.pages.MyLoanListPage;
import hu.bme.aut.loanmanager.pages.NewLoanPage;
import hu.bme.aut.loanmanager.pages.NewPartnerPage;
import hu.bme.aut.loanmanager.pages.RegistrationPage;
import hu.bme.aut.loanmanager.pages.UndecidedPartnerListPage;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.pages.SignInPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.wicketstuff.javaee.injection.JavaEEComponentInjector;


public class LoanManagerApplication extends AuthenticatedWebApplication {

	public LoanManagerApplication() {
		super();
		mountPages();
	}
	
	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new JavaEEComponentInjector(this));
	}

	private void mountPages() {
		mountPage("loginPage", HomePage.class);
		mountPage("registrationPage", RegistrationPage.class);
		mountPage("loanListPage", LoanListPage.class);
		mountPage("newLoanPage", NewLoanPage.class);
		mountPage("newPartnerPage", NewPartnerPage.class);
		mountPage("undecidedPartnerListPage", UndecidedPartnerListPage.class);
		mountPage("myLoansPage", MyLoanListPage.class);
		mountPage("messageListPage", MessageListPage.class);
	}

	@Override
	public Session newSession(final Request request, final Response response) {
		return new LoanManagerWebSession(request);
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return SignInPage.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return LoanManagerWebSession.class;
	}

}
