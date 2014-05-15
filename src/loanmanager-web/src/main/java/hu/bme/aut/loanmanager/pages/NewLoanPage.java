package hu.bme.aut.loanmanager.pages;

import hu.bme.aut.loanmanager.util.WebLoanManager;
import hu.bme.loanmanager.domain.io.LoanPartVO;
import hu.bme.loanmanager.domain.io.LoanVO;
import hu.bme.loanmanager.domain.io.LoggedInUserWithAmountVO;
import hu.bme.loanmanager.domain.io.UserVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

@AuthorizeInstantiation({"admin", "user"})
public class NewLoanPage extends AbstractLoggedInPage {

	private WebMarkupContainer container;
	private List<UserVO> peopleList;
	private LoanForm loanForm;
	private ArrayList<LoggedInUserWithAmountVO> peopleAddList;
	private ListView<LoggedInUserWithAmountVO> lists;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		peopleList =
				new WebLoanManager().setManager(manager).partnerList(getLoggedInUser());
		if(peopleList == null || peopleList.isEmpty()) {
			getLoanManagerWebSession().info("You don't have partners");
			setResponsePage(NewPartnerPage.class);
		}
		addContainer();
		addForm();
	}

	private void addForm() {
		loanForm = new LoanForm();
		container.add(loanForm);
		loanForm.add(new AjaxButton("submit") {

			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				super.onSubmit(target, form);
				updateMainFeedbackPanel(target);
				LoanVO modelObject = loanForm.getModelObject();
				modelObject.setLoanOwner(getLoggedInUser());
				modelObject.setLoanDate(new Date());
				
				List<? extends LoggedInUserWithAmountVO> list = lists.getList();
				for (LoggedInUserWithAmountVO loggedInUserWithAmountVO : list) {
					LoanPartVO loanpartVO = new LoanPartVO();
					loanpartVO.setDebter(loggedInUserWithAmountVO.getUser());
					loanpartVO.setLoanAmount(loggedInUserWithAmountVO.getAmount());
					loanpartVO.setEndDate(modelObject.getDeadLine());
					modelObject.getLoanParts().add(loanpartVO);
				}
				new WebLoanManager().setManager(manager).addLoan(modelObject);
				setResponsePage(NewLoanPage.class);
			}

			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				updateMainFeedbackPanel(target);
			}
		});
		loanForm.add(new LoanValidator());
	}

	private class LoanForm extends Form<LoanVO> {

		private WebMarkupContainer listContainer;

		public LoanForm() {
			super("loanForm", Model.of(new LoanVO()));
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			addElements();
			addList();
		}

		private void addList() {
			AjaxSubmitLink addButton = new AjaxSubmitLink("newUser") {
				@Override
				protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
					super.onSubmit(target, form);
					peopleAddList.add(new LoggedInUserWithAmountVO());
					target.add(listContainer);
				}

			};
			addButton.setDefaultFormProcessing(false);
			add(addButton);
			listContainer = new WebMarkupContainer("listContainer");
			listContainer.setOutputMarkupId(true);
			loanForm.add(listContainer);
			peopleAddList = new ArrayList<LoggedInUserWithAmountVO>();
			lists = new ListView<LoggedInUserWithAmountVO>("peopleList", new PropertyModel<List<LoggedInUserWithAmountVO>>(NewLoanPage.this, "peopleAddList")) {

				@Override
				protected void populateItem(final ListItem<LoggedInUserWithAmountVO> item) {
					item.add(new UserSelectionDropDown(item));
					item.add(new TextField<LoggedInUserWithAmountVO>("amount", new PropertyModel<LoggedInUserWithAmountVO>(item.getModelObject(), "amount")));
				}
			};
			lists.setReuseItems(true);
			listContainer.add(lists);
		}

		private void addElements() {
			add(new TextField<LoanPartVO>("loanAmount", new PropertyModel<LoanPartVO>(getModelObject(), "loanAmount")).setRequired(true));
			
			//Date field
			DateTextField dateTextField = new DateTextField("endDate", new PropertyModel<Date>(
			        getModelObject(), "deadLine"));
			DatePicker datePicker = new DatePicker();
			datePicker.setShowOnFieldClick(true);
			datePicker.setAutoHide(true);
			dateTextField.add(datePicker);
			add(dateTextField);
		}

	}

	private class UserSelectionDropDown extends DropDownChoice<UserVO> {

		public UserSelectionDropDown(final ListItem<LoggedInUserWithAmountVO> item) {
			super("selectableUsers",
					new PropertyModel<UserVO>(item.getModelObject(), "user"),
					peopleList);
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			setRequired(true);
		}

		@Override
		protected boolean wantOnSelectionChangedNotifications() {
			return true;
		}

	}

	private void addContainer() {
		container = new WebMarkupContainer("newLoanContainer");
		container.setOutputMarkupId(true);
		add(container);
	}

	private void updatePage(final AjaxRequestTarget target) {
		target.add(container);
	}

	@Override
	public String getPageName() {
		return "New Loan";
	}

	private class LoanValidator implements IFormValidator {

		@Override
		public FormComponent<?>[] getDependentFormComponents() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void validate(final Form<?> form) {
			LoanVO loan = loanForm.getModelObject();
			int loanAmount = loan.getLoanAmount();
			int sumAmount = 0;
			for (LoggedInUserWithAmountVO user : peopleAddList) {
				sumAmount += user.getAmount();
			}

			if(sumAmount != loanAmount) {
				System.out.println("hello");
				error("Full amount is less than amounts per people!");
			}
		}

	}

}
