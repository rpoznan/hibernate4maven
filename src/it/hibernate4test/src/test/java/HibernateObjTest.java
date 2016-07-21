import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.myjavadoc.dto.testdb.Customer;
import com.myjavadoc.dto.testdb.Invoice;
import com.myjavadoc.dto.testdb.Item;
import com.myjavadoc.dto.testdb.ItemId;
import com.myjavadoc.dto.testdb.Product;
import org.hibernate.cfg.Configuration;

public class HibernateObjTest {

	 private SessionFactory sessionFactory;
	 private Session session = null;

	 @Before
	public void mySetup() {
		 Configuration configuration = new Configuration();
		  configuration.addAnnotatedClass(Customer.class)
		    .addAnnotatedClass(Invoice.class)
		    .addAnnotatedClass(Product.class)
		    .addAnnotatedClass(Item.class);
		  configuration.setProperty("hibernate.dialect",
		    "org.hibernate.dialect.HSQLDialect");
		  configuration.setProperty("hibernate.connection.driver_class",
		    "org.hsqldb.jdbcDriver");
		  configuration.setProperty("hibernate.connection.url", "jdbc:hsqldb:hsql://localhost/xdb");
		  configuration.setProperty("hibernate.connection.username", "SA");
		  configuration.setProperty("hibernate.connection.password", "");
		 
		 sessionFactory = configuration.buildSessionFactory();
		 session = sessionFactory.openSession();
	 }
	 
	 @Test
	 public void testCustomer() {
		 
		 Query q = session.createQuery("from Customer");
		 q.setMaxResults(10);
		 List<Customer> customerList = q.list();
		 for(Customer c: customerList) {
			 System.out.println(c.getFirstname()+","+c.getLastname()+","+c.getId()+","+c.getCity());
		 }
	 }
	 
	 @After
	 public void endTest() {
		 session.close();
		 sessionFactory.close();
	 }

}
