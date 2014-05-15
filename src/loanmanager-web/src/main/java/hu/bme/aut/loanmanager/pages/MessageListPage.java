package hu.bme.aut.loanmanager.pages;

import hu.bme.aut.loanmanager.util.WebLoanManager;
import hu.bme.loanmanager.domain.io.MessageVO;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

@AuthorizeInstantiation({"admin", "user"})
public class MessageListPage extends AbstractLoggedInPage {

	@Override
	protected void onInitialize() {
		super.onInitialize();
		List<MessageVO> messagesList = new WebLoanManager().setManager(manager).messageList(getLoggedInUser());
		add(new MessageListView("messages", messagesList));
	}

	private class MessageListView extends ListView<MessageVO> {

		public MessageListView(final String id, final List<? extends MessageVO> list) {
			super(id, list);
		}

		@Override
		protected void populateItem(final ListItem<MessageVO> item) {
			MessageVO modelObject = item.getModelObject();
			item.add(new Label("message", new PropertyModel<MessageVO>(modelObject, "message")));
			item.add(new Label("messageDate", new PropertyModel<MessageVO>(modelObject, "messageDate")));
		}

	}

	@Override
	public String getPageName() {
		return "Messages";
	}

}
