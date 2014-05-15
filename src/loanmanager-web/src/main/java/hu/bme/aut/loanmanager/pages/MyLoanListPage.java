package hu.bme.aut.loanmanager.pages;

import hu.bme.aut.loanmanager.panels.LoanPanel;
import hu.bme.aut.loanmanager.panels.SumPanel;
import hu.bme.aut.loanmanager.util.WebLoanManager;
import hu.bme.loanmanager.domain.io.LoanPartVO;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

@AuthorizeInstantiation({"admin", "user"})
public class MyLoanListPage extends AbstractLoggedInPage {

	private WebMarkupContainer container;
	private ListView<LoanPartVO> acceptableLoanListView;
	private ListView<LoanPartVO> loanListView;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		addContainer();
		addLoanList();
		addAcceptedLoanList();
		addSumPanel();
	}

	private void addSumPanel() {
		SumPanel panel = new SumPanel("sumPanel", acceptableLoanListView, loanListView);
		container.add(panel);
	}

	private void addAcceptedLoanList() {
		List<LoanPartVO> acceptedLoanList = new WebLoanManager().setManager(manager).loanList(getLoggedInUser());// TODO ejb
		loanListView = new ListView<LoanPartVO>("loanList", acceptedLoanList) {
			@Override
			protected void populateItem(final ListItem<LoanPartVO> item) {
				LoanPartVO modelObject = item.getModelObject();
				item.add(new LoanPanel<LoanPartVO>(item.getModel(), new EqualLink(item.getModelObject())));
			}
		};
		container.add(loanListView);
	}

	private class EqualLink extends AjaxLink<LoanPartVO> {

		public EqualLink(final LoanPartVO vo) {
			super("doLink", Model.of(vo));
		}

		@Override
		public void onClick(final AjaxRequestTarget target) {
			loanListView.getList().remove(getModelObject());
			new WebLoanManager().setManager(manager).equalLoan(getLoggedInUser(), getModelObject());
			updatePage(target);
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			add(new Label("linkName","Equal"));
		}

	}

	private void addLoanList() {
		List<LoanPartVO> loanList = new WebLoanManager().setManager(manager).acceptableLoanList(getLoggedInUser()); // TODO ejb
		acceptableLoanListView = new ListView<LoanPartVO>("acceptableLoanList", loanList) {
			@Override
			protected void populateItem(final ListItem<LoanPartVO> item) {
				LoanPartVO modelObject = item.getModelObject();
				item.add(new LoanPanel<LoanPartVO>(item.getModel(), new AcceptLink(modelObject), new RejectLink(modelObject)));
			}
		};
		container.add(acceptableLoanListView);
	}

	private class RejectLink extends AjaxLink<LoanPartVO> {

		public RejectLink( final LoanPartVO loanvo) {
			super("doLink", Model.of(loanvo));
		}

		@Override
		public void onClick(final AjaxRequestTarget target) {
			acceptableLoanListView.getList().remove(getModelObject());
			new WebLoanManager().setManager(manager).rejectLoan(getLoggedInUser(), getModelObject());
			updatePage(target);
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			add(new Label("linkName", "Reject"));
		}

	}

	private class AcceptLink extends AjaxLink<LoanPartVO> {

		public AcceptLink( final LoanPartVO loanvo) {
			super("doLink", Model.of(loanvo));
		}

		@Override
		public void onClick(final AjaxRequestTarget target) {
			acceptableLoanListView.getList().remove(getModelObject());
			new WebLoanManager().setManager(manager).acceptLoan(getLoggedInUser(), getModelObject());
			updatePage(target);
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			add(new Label("linkName", "Accept"));
		}

	}

	private void addContainer() {
		container = new WebMarkupContainer("myLoansContainer");
		container.setOutputMarkupId(true);
		add(container);
	}

	private void updatePage(final AjaxRequestTarget target) {
		target.add(container);
	}

	@Override
	public String getPageName() {
		return "My outcoming loans";
	}

}
