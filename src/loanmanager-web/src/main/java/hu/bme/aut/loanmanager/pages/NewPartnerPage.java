package hu.bme.aut.loanmanager.pages;

import hu.bme.aut.loanmanager.panels.PersonPanel;
import hu.bme.aut.loanmanager.util.Dropable;
import hu.bme.aut.loanmanager.util.StringUtil;
import hu.bme.aut.loanmanager.util.WebLoanManager;
import hu.bme.loanmanager.domain.io.UserVO;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

@AuthorizeInstantiation({"admin", "user"})
public class NewPartnerPage extends AbstractLoggedInPage {

	private WebMarkupContainer container;
	private PageableListView<UserVO> personList;
	private PageableListView<UserVO> minePersonList;
	private List<UserVO> selectableUsers;
	private Form<UserVO> searchForm;
	private Dropable<UserVO> dropZone;
	private final Boolean red = Boolean.FALSE;
	private PageableListView<UserVO> undecided;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		addContainer();
		addPersonList();
		addMinePersonList();
		addUndecidedPersonList();
		addSearchForm();
		addDroppable();
	}


	private void addDroppable() {
		//		dropZone = new Dropable<UserVO>("dropZone", red) {
		//
		//			@Override
		//			protected void action(final AjaxRequestTarget target,
		//					final UserVO element) {
		//				newAction(target, element);
		//
		//			}
		//		};
		//		container.add(dropZone);
	}

	private void addSearchForm() {
		searchForm = new Form<UserVO>("searchForm", Model.of(new UserVO()));
		searchForm.add(new TextField<UserVO>("username",
				new PropertyModel<UserVO>(searchForm.getModelObject(), "username")));
		searchForm.add(new TextField<UserVO>("fullName",
				new PropertyModel<UserVO>(searchForm.getModelObject(), "fullName")));
		searchForm.add(new TextField<UserVO>("email",
				new PropertyModel<UserVO>(searchForm.getModelObject(), "email")));
		searchForm.add(new AjaxButton("search") {

			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				super.onSubmit(target, form);
				searchMethod(target, searchForm.getModelObject());
			}

		});
		container.add(searchForm);
	}

	private void searchMethod(final AjaxRequestTarget target, final UserVO modelObject) {
		List<UserVO> tempList = new ArrayList<UserVO>();
		List<UserVO> tempList2 = new ArrayList<UserVO>();
		List<UserVO> persons = (List<UserVO>) personList.getList();
		UserVO searchUser = searchForm.getModelObject();

		if( StringUtil.isEmpty(searchUser.getFullName())
				&& StringUtil.isEmpty(searchUser.getEmail())
				&& StringUtil.isEmpty(searchUser.getUsername())) {
			personList.setList(selectableUsers);
			updatePage(target);
			return;
		}
		else {
			for(UserVO user : selectableUsers) {
				if(!StringUtil.isEmpty(searchUser.getFullName()) && user.getFullName().startsWith(searchUser.getFullName())) {
					tempList.add(user);
					continue;
				}
				if(!StringUtil.isEmpty(searchUser.getUsername()) && user.getUsername().startsWith(searchUser.getUsername())) {
					tempList.add(user);
					continue;
				}
				if(!StringUtil.isEmpty(searchUser.getEmail()) && user.getEmail().startsWith(searchUser.getEmail())) {
					tempList.add(user);
					continue;
				}
			}

			for(UserVO user : tempList) {
				if(!StringUtil.isEmpty(searchUser.getFullName()) && !user.getFullName().startsWith(searchUser.getFullName())) {
					continue;
				}
				if(!StringUtil.isEmpty(searchUser.getEmail()) && !user.getEmail().startsWith(searchUser.getEmail())) {
					continue;
				}
				if(!StringUtil.isEmpty(searchUser.getUsername()) && !user.getUsername().startsWith(searchUser.getUsername())) {
					continue;
				}
				tempList2.add(user);
			}

		}
		personList.setList(tempList2);
		updatePage(target);
	}

	private void addMinePersonList() {
		List<UserVO> users = new WebLoanManager().setManager(manager).partnerList(getLoggedInUser());
		minePersonList = new PageableListView<UserVO>("minePersonList", users, 8 ) {

			@Override
			protected void populateItem(final ListItem<UserVO> item) {
				item.add(new PersonPanel<UserVO>(item.getModel(), new RemoveUserLink(item.getModelObject())));
			}

		};
		container.add(new AjaxPagingNavigator("mineNavigator", minePersonList));
		container.add(minePersonList);
	}

	private void addUndecidedPersonList() {
		List<UserVO> users = new WebLoanManager().setManager(manager).yetUndecidedUsersList(getLoggedInUser());
		undecided = new PageableListView<UserVO>("undecidedPartners", users, 8 ) {

			@Override
			protected void populateItem(final ListItem<UserVO> item) {
				item.add(new PersonPanel<UserVO>(item.getModel()));
			}

		};
		container.add(new AjaxPagingNavigator("undecidedNavigator", undecided));
		container.add(undecided);
	}

	private class RemoveUserLink extends AjaxLink<UserVO> {

		public RemoveUserLink(final UserVO user) {
			super("doLink", Model.of(user));
		}

		@Override
		public void onClick(final AjaxRequestTarget target) {
			List<UserVO> list = (List<UserVO>) personList.getList();
			list.add(getModelObject());
			minePersonList.getList().remove(getModelObject());
			new WebLoanManager().setManager(manager).removeUserFromPartnerList(getLoggedInUser(), getModelObject());
			updatePage(target);
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			add(new Label("linkName", "Remove"));
		}

	}

	private void addPersonList() {
		selectableUsers =
				new WebLoanManager().setManager(manager).selectablePartners(getLoggedInUser());
		personList = new PageableListView<UserVO>("personList", selectableUsers, 9) {

			@Override
			protected void populateItem(final ListItem<UserVO> item) {
				PersonPanel<UserVO> personPanel = new PersonPanel<UserVO>(item.getModel(), new NewUserLink(item.getModelObject()));
				//				Dragable<UserVO> dragable =
				//						new Dragable<UserVO>("draggable", item.getModel(), personPanel, red, dropZone);
				//				item.add(dragable);
				item.add(personPanel);
			}

		};
		container.add(new AjaxPagingNavigator("navigator", personList));
		container.add(personList);
	}

	private void newAction(final AjaxRequestTarget target, final UserVO userVO) {
		List<UserVO> list = (List<UserVO>) undecided.getList();
//		list.add(userVO);
		personList.getList().remove(userVO);
		new WebLoanManager().setManager(manager).addUserToUndecidedList(getLoggedInUser(), userVO);
		updatePage(target);
	}

	private class NewUserLink extends AjaxLink<UserVO> {

		public NewUserLink(final UserVO user) {
			super("doLink", Model.of(user));
		}

		@Override
		public void onClick(final AjaxRequestTarget target) {
			newAction(target, getModelObject());
		}


		@Override
		protected void onInitialize() {
			super.onInitialize();
			add(new Label("linkName", "Add"));
		}

	}

	private void addContainer() {
		container = new WebMarkupContainer("newPartnerContainer");
		container.setOutputMarkupId(true);
		add(container);
	}

	private void updatePage(final AjaxRequestTarget target) {
		target.add(container);
	}

	@Override
	public String getPageName() {
		return "New partner";
	}

}
