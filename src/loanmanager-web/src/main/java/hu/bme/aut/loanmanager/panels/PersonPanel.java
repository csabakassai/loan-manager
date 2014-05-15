package hu.bme.aut.loanmanager.panels;

import hu.bme.loanmanager.domain.io.UserVO;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class PersonPanel<T extends UserVO> extends GenericPanel<T> {

	private final Object [] links;

	public PersonPanel(final IModel<T> model, final Object ... links) {
		super("personPanel", model);
		this.links = links;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		addItems();
		addLinks();
	}

	private void addItems() {
		T person = getModelObject();
		add(new Label("username", new PropertyModel<T>(person, "username")));
		add(new Label("email", new PropertyModel<T>(person, "email")));
		add(new Label("fullName", new PropertyModel<T>(person, "fullName")));
		add(new Label("fullNameHeader", new PropertyModel<T>(person, "fullName")));
	}

	public void addLinks() {
		ListView<Object> linkList = new ListView<Object>("linkList", Arrays.asList(links)) {

			@Override
			protected void populateItem(final ListItem<Object> item) {
				Object modelObject = item.getModelObject();
				item.add((Component) modelObject);
			}

		};
		add(linkList);
	}

}
