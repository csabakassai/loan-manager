package hu.bme.aut.loanmanager.pages;

import hu.bme.aut.loanmanager.panels.PersonPanel;
import hu.bme.aut.loanmanager.util.WebLoanManager;
import hu.bme.loanmanager.domain.io.UserVO;

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
public class UndecidedPartnerListPage extends AbstractLoggedInPage {

	private WebMarkupContainer container;
	private ListView<UserVO> personList;
	private List<UserVO> selectableUsers;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		addContainer();
		addPersonList();
	}

	private void addPersonList() {
		selectableUsers = new WebLoanManager().setManager(manager).undecidedUsers(getLoggedInUser());
		personList = new ListView<UserVO>("personList", selectableUsers) {

			@Override
			protected void populateItem(final ListItem<UserVO> item) {
				item.add(new PersonPanel<UserVO>(item.getModel(), new AcceptUserLink(item.getModelObject()), new RemoveUserLink(item.getModelObject())));
			}

		};
		container.add(personList);
	}

	private class AcceptUserLink extends AjaxLink<UserVO> {

		public AcceptUserLink(final UserVO user) {
			super("doLink", Model.of(user));
		}

		@Override
		public void onClick(final AjaxRequestTarget target) {
			personList.getList().remove(getModelObject());
			new WebLoanManager().setManager(manager).addUndecidedUser(getLoggedInUser(), getModelObject());
			updatePage(target);
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			add(new Label("linkName","Accept"));
		}

	}

	private class RemoveUserLink extends AjaxLink<UserVO> {

		public RemoveUserLink(final UserVO user) {
			super("doLink", Model.of(user));
		}

		@Override
		public void onClick(final AjaxRequestTarget target) {
			personList.getList().remove(getModelObject());
			new WebLoanManager().setManager(manager).removeUndecidedUser(getLoggedInUser(), getModelObject());
			updatePage(target);
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			add(new Label("linkName","Remove"));
		}

	}

	private void addContainer() {
		container = new WebMarkupContainer("undecidedPartnerListContainer");
		container.setOutputMarkupId(true);
		add(container);
	}

	private void updatePage(final AjaxRequestTarget target) {
		target.add(container);
	}

	@Override
	public String getPageName() {
		return "Undecided partners";
	}

}
