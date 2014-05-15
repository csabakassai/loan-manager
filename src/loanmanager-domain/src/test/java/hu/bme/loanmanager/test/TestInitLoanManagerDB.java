
package hu.bme.loanmanager.test;

import hu.bme.loanmanager.domain.factory.LoanManagerDomainFactory;
import hu.bme.loanmanager.domain.model.LoanManagerUser;
import hu.bme.loanmanager.domain.model.Partnership;
import hu.bme.loanmanager.domain.model.PartnershipStatus;
import hu.bme.loanmanager.domain.module.LoanManagerDomainModule;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;

//@org.junit.Ignore
public class TestInitLoanManagerDB {
	
	private static EntityManagerFactory entityManagerFactory;
	
	private EntityManager em;
	
	private Injector injector;
	
	@BeforeClass
	public static void setup() throws Exception {
		final Map<String, Object> properties = Maps.newTreeMap();
		properties.put( "hibernate.hbm2ddl.auto", "create" );
		properties.put( "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect" );
		properties.put( "hibernate.connection.url", "jdbc:postgresql://localhost:5432/loanmanager" );
		properties.put( "hibernate.connection.username", "loanmanager" );
		properties.put( "hibernate.connection.password", "loanmanager" );
		properties.put( "hibernate.connection.driver_class", "org.postgresql.Driver" );
		
		entityManagerFactory = Persistence.createEntityManagerFactory( "test::LoanManagerDomain", properties );
	}
	
	@Before
	public void start() throws Exception {
		em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		injector = Guice.createInjector( new LoanManagerDomainModule( em ) );
	}
	
	@After
	public void end() throws Exception {
		try {
			em.flush();
			final EntityTransaction transaction = em.getTransaction();
			if (transaction.isActive()) {
				transaction.commit();
			}
		}
		finally {
			em.close();
			em = null;
		}
		
	}
	
	@Test
	public void testInitEkopDb() throws Exception {
		LoanManagerDomainFactory domainFactory = injector.getInstance(LoanManagerDomainFactory.class);
		LoanManagerUser u1 = domainFactory.createUser("u1", "u1", "alma", "alma@nodomain.com");
		
		LoanManagerUser u2 = domainFactory.createUser("u2", "u2", "alma", "alma@nodomain.com");
		
		LoanManagerUser u3 = domainFactory.createUser("u3", "u3", "alma", "alma@nodomain.com");
		
		LoanManagerUser u4 = domainFactory.createUser("u4", "u4", "alma", "alma@nodomain.com");
		
		LoanManagerUser admin = domainFactory.createUser("admin", "admin", "admin", "admin@nodomain.com");
		admin.setAdmin(true);
		
		domainFactory.createMessage(u4, "HEHE");
		Partnership partnership = domainFactory.createPartnership(u1, u4, new Date());
		partnership.setStatus(PartnershipStatus.ACCEPTED);
		
		partnership = domainFactory.createPartnership(u1, u3, new Date());
		partnership.setStatus(PartnershipStatus.ACCEPTED);
		
		partnership = domainFactory.createPartnership(u1, u2, new Date());
		partnership.setStatus(PartnershipStatus.ACCEPTED);
		
		domainFactory.createMessage(u4, "message");
	}
	
}
