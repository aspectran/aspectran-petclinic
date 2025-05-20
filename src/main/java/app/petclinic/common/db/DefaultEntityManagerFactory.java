package app.petclinic.common.db;

import app.petclinic.owner.Owner;
import app.petclinic.owner.Pet;
import app.petclinic.owner.Visit;
import app.petclinic.vet.Specialty;
import app.petclinic.vet.Vet;
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.jpa.EntityManagerFactoryBean;
import jakarta.persistence.PersistenceConfiguration;
import jakarta.persistence.PersistenceUnitTransactionType;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.jpa.HibernatePersistenceConfiguration;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.sql.DataSource;
import java.util.Map;

/**
 * <p>Created: 2025-05-02</p>
 */
@Component
@Bean(lazyDestroy = true)
public class DefaultEntityManagerFactory extends EntityManagerFactoryBean {

    private final DataSource dataSource;

    @Autowired
    public DefaultEntityManagerFactory(DataSource dataSource) {
        super("petclinic");
        this.dataSource = dataSource;
    }

//    @Override
//    protected PersistenceConfiguration configuration() {
//        return new HibernatePersistenceConfiguration("petclinic")
////                .provider(HibernatePersistenceProvider.class.getName())
//                .transactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL)
//                .property(JdbcSettings.JAKARTA_NON_JTA_DATASOURCE, dataSource)
//                .property(JdbcSettings.SHOW_SQL, false)
//                .property(JdbcSettings.FORMAT_SQL, false)
//                .property(JdbcSettings.USE_SQL_COMMENTS, false);
//    }

    @Override
    protected void preConfigure(Map<String, Object> properties) {
        super.preConfigure(properties);
        properties.put(JdbcSettings.JAKARTA_NON_JTA_DATASOURCE, dataSource);
        properties.put(JdbcSettings.SHOW_SQL, false);
        properties.put(JdbcSettings.FORMAT_SQL, false);
        properties.put(JdbcSettings.USE_SQL_COMMENTS, false);

        properties.put("eclipselink.logging.level", "FINE");
        properties.put("eclipselink.logging.parameters", "true");
        properties.put("eclipselink.logging.level.sql", "FINE");
        properties.put("eclipselink.logging.level.session", "FINE");
        properties.put("eclipselink.logging.level.transaction", "FINE");
        properties.put("eclipselink.logging.level.connection", "FINE");
        properties.put("eclipselink.logging.level.query", "FINE");
        properties.put("eclipselink.logging.level.event", "FINE");
        properties.put("eclipselink.logging.level.metadata", "FINE");
    }

    @Override
    protected void preConfigure(PersistenceConfiguration persistenceConfiguration) {
        super.preConfigure(persistenceConfiguration);
//        persistenceConfiguration.provider(HibernatePersistenceProvider.class.getName());
        persistenceConfiguration.transactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL);
        persistenceConfiguration.managedClass(Vet.class);
        persistenceConfiguration.managedClass(Specialty.class);
        persistenceConfiguration.managedClass(Visit.class);
        persistenceConfiguration.managedClass(Owner.class);
        persistenceConfiguration.managedClass(Pet.class);
    }

}
