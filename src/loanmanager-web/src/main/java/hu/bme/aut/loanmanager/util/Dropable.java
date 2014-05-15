package hu.bme.aut.loanmanager.util;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;

import com.googlecode.wicket.jquery.ui.interaction.Draggable;
import com.googlecode.wicket.jquery.ui.interaction.Droppable;

public abstract class Dropable<T> extends Droppable<Void> {

	private Boolean red;

	public Dropable(final String id, final Boolean red) {
		super(id);
		this.red = red;
	}

	@Override
	protected void onDrop(final AjaxRequestTarget target, final Draggable<?> draggable) {
		if(draggable != null) {
			Object modelObject = draggable.getModelObject();

			try {
				T element = (T) modelObject;
				action(target, element);
			} catch (ClassCastException e) {
				info("Sorry, you can't use it here");
			}

		}

		red = Boolean.FALSE;
		target.add(this);
	}

	protected abstract void action(final AjaxRequestTarget target, final T element);

	@Override
	protected void onInitialize() {
		super.onInitialize();
		setOutputMarkupId(true);
	}

	@Override
	protected void onComponentTag(final ComponentTag tag) {
		super.onComponentTag(tag);
		if(red == Boolean.TRUE) {
			tag.put("style", "width: 350px; height: 200px; border: 1px solid red; border-style: dashed;");
		} else {
			tag.put("style", "width: 350px; height: 200px; border: 1px solid grey;");
		}
	}

}
