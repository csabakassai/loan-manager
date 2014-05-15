
package hu.bme.loanmanager.domain.module;

import javax.persistence.EntityManager;

import com.google.inject.AbstractModule;
import com.google.inject.binder.AnnotatedBindingBuilder;

/**
 * 
 * @author cskassai
 */
public class LoanManagerDomainModule extends AbstractModule {
	
	private final EntityManager em;
	
	public LoanManagerDomainModule( final EntityManager em ) {
		super();
		this.em = em;
	}
	
	@Override
	protected void configure() {
		final AnnotatedBindingBuilder<EntityManager> bindingBuilder = bind( EntityManager.class );
		bindingBuilder.toInstance( em );
		
	}
	
}
