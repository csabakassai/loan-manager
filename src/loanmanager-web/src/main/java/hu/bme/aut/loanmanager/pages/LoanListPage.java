package hu.bme.aut.loanmanager.pages;

import hu.bme.aut.loanmanager.panels.LoanPanel;
import hu.bme.aut.loanmanager.panels.SumPanel;
import hu.bme.aut.loanmanager.util.WebLoanManager;
import hu.bme.loanmanager.domain.io.LoanPartVO;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;


@AuthorizeInstantiation({"admin", "user"})
public class LoanListPage extends AbstractLoggedInPage {

	private ListView<LoanPartVO> accepted;
	private ListView<LoanPartVO> rejected;
	private ListView<LoanPartVO> equaled;
	private ListView<LoanPartVO> waited;
	
	@Override
	public String getPageName() {
		return "Loan List";
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		equaledLoans();
		rejectedLoans();
		acceptedLoans();
		waitedLoans();
		addSumPanel();
	}

	private void addSumPanel() {
		SumPanel panel = new SumPanel("sumPanel", accepted, rejected, equaled, waited);
		add(panel);
	}

	private void acceptedLoans() {
		List<LoanPartVO> acceptedLoanList = new WebLoanManager().setManager(manager).acceptedLoanList(getLoggedInUser());
		accepted = new ListView<LoanPartVO>("accepted", acceptedLoanList) {

			@Override
			protected void populateItem(final ListItem<LoanPartVO> item) {
				item.add(new LoanPanel<LoanPartVO>(item.getModel()));
			}

		};
		add(accepted);
	}

	private void rejectedLoans() {
		List<LoanPartVO> rejectedLoanList = new WebLoanManager().setManager(manager).rejectedLoanList(getLoggedInUser());
		rejected = new ListView<LoanPartVO>("rejected", rejectedLoanList) {

			@Override
			protected void populateItem(final ListItem<LoanPartVO> item) {
				item.add(new LoanPanel<LoanPartVO>(item.getModel()));
			}

		};
		add(rejected);
	}

	private void equaledLoans() {
		List<LoanPartVO> rejectedLoanList = new WebLoanManager().setManager(manager).equalLoanList(getLoggedInUser());
		equaled = new ListView<LoanPartVO>("equaled",rejectedLoanList) {

			@Override
			protected void populateItem(final ListItem<LoanPartVO> item) {
				item.add(new LoanPanel<LoanPartVO>(item.getModel()));
			}

		};
		add(equaled);
	}

	private void waitedLoans() {
		List<LoanPartVO> waitedLoanList = new WebLoanManager().setManager(manager).waitedLoanList(getLoggedInUser());
		waited = new ListView<LoanPartVO>("waited", waitedLoanList) {

			@Override
			protected void populateItem(final ListItem<LoanPartVO> item) {
				item.add(new LoanPanel<LoanPartVO>(item.getModel()));
			}

		};
		add(waited);
	}

}
