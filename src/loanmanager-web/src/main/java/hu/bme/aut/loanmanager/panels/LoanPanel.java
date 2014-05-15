package hu.bme.aut.loanmanager.panels;

import hu.bme.loanmanager.domain.io.LoanPartVO;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class LoanPanel<T extends LoanPartVO> extends GenericPanel<T> {

	private final Object [] links;

	public LoanPanel(final IModel<T> model, final Object ... links) {
		super("loanPanel", model);
		this.links = links;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		addItems();
		addLinks();
	}

	private void addItems() {
		T loan = getModelObject();
		add(new Label("fullName", new PropertyModel<T>(loan, "debter.fullName")));
		add(new Label("loanAmount", new PropertyModel<T>(loan, "loanAmount")));
		add(new Label("endDate", new PropertyModel<T>(loan, "endDate")));
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
