package hu.bme.aut.loanmanager.util;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.ui.interaction.Draggable;

public class Dragable<T> extends Draggable<T>{

	private Boolean red;
	private final Dropable<T> [] drops;
	private final Component component;

	public Dragable(final String id,
			final IModel<T> model,
			final Component component,
			final Boolean red,
			final Dropable<T> ... drops) {
		super(id, model);
		this.component = component;
		this.red = red;
		this.drops = drops;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(component);
		setRevert(true);
	}

	@Override
	protected void onDragStart(final AjaxRequestTarget target) {
		super.onDragStart(target);
		red = Boolean.TRUE;
		target.add(drops);
	}

	@Override
	protected void onDragStop(final AjaxRequestTarget target) {
		super.onDragStop(target);
		red = Boolean.FALSE;
		target.add(drops);
	}

	@Override
	protected boolean isStopEventEnabled() {
		return true;
	}

}
