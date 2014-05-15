package hu.bme.aut.loanmanager.panels;

import hu.bme.loanmanager.domain.io.LoanPartVO;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;


public class SumPanel extends AbstractLoanManagerPanel {

	private int sum;
	private final ListView<LoanPartVO> [] lists;

	public SumPanel(final String id, final ListView<LoanPartVO> ... lists) {
		super(id);
		this.lists = lists;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		setOutputMarkupId(true);
		sumSzamitas();
		add(new Label("sum", new PropertyModel<Integer>(SumPanel.this, "sum")));
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();
		sumSzamitas();
	}

	private void sumSzamitas() {
		int sumTMP = 0;
		for (ListView<LoanPartVO> listV : lists) {
			List<LoanPartVO> list = (List<LoanPartVO>) listV.getList();
			for (LoanPartVO object : list) {
				sumTMP += object.getLoanAmount();
			}
		}
		sum = sumTMP;
	}

}
