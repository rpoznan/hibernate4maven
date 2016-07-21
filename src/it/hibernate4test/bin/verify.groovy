import com.myjavadoc.dto.testdb.Customer
import com.myjavadoc.dto.testdb.Invoice
import com.myjavadoc.dto.testdb.Item
import com.myjavadoc.dto.testdb.ItemId
import com.myjavadoc.dto.testdb.Product
import org.hibernate.cfg.AnnotationConfiguration
import org.hibernate.SessionFactory
import org.hibernate.Session

AnnotationConfiguration configuration = new AnnotationConfiguration();
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
 
 def sessionFactory = configuration.buildSessionFactory();
 def session = sessionFactory.openSession();
 
 session.close();
