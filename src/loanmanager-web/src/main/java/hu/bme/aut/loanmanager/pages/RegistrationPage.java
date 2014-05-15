package hu.bme.aut.loanmanager.pages;

import hu.bme.aut.loanmanager.util.WebLoanManager;
import hu.bme.loanmanager.domain.exception.LoanManagerBusinessException;
import hu.bme.loanmanager.domain.io.UserVO;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class RegistrationPage extends AbstractLoanManagerPage {

	public RegistrationPage() {
		super();
		if(getLoggedInUser() != null) {
			setResponsePage(MyLoanListPage.class);
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new RegistrationForm(Model.of(new UserVO())));
	}

	private class RegistrationForm extends Form<UserVO> {

		public RegistrationForm( final IModel<UserVO> model) {
			super("registrationForm", model);
		}

		@Override
		protected void onInitialize() {
			super.onInitialize();
			add(new TextField<UserVO>("username", new PropertyModel<UserVO>(getModel(), "username")).setRequired(true));
			add(new TextField<UserVO>("password", new PropertyModel<UserVO>(getModel(), "password")).setRequired(true));
			add(new TextField<UserVO>("email", new PropertyModel<UserVO>(getModel(), "email")).setRequired(true));
			add(new TextField<UserVO>("fullName", new PropertyModel<UserVO>(getModel(), "fullName")).setRequired(true));
			add(new RegistrationFormSubmitButton());
		}

		private class RegistrationFormSubmitButton extends AjaxButton {

			public RegistrationFormSubmitButton() {
				super("submitButton");
			}

			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				UserVO userVO = RegistrationForm.this.getModelObject();
				try {
					new WebLoanManager().setManager(manager).registrationUser(userVO);
				} catch (LoanManagerBusinessException e) {
					error(e.getMessage());
				}  finally {
					updateMainFeedbackPanel(target);
				}
				getLoanManagerWebSession().info("Registration is succesfull!");
				setResponsePage(HomePage.class);
			}

			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				super.onError(target, form);
				updateMainFeedbackPanel(target);
			}

		}

	}

}
