package app.petclinic.common.db;

import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.core.component.bean.annotation.Initialize;
import com.aspectran.jpa.EntityManagerFactoryBean;
import com.aspectran.jpa.eclipselink.Slf4jSessionLogger;
import jakarta.persistence.PersistenceConfiguration;
import jakarta.persistence.PersistenceUnitTransactionType;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.logging.SessionLog;
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
//        properties.put(JdbcSettings.JAKARTA_NON_JTA_DATASOURCE, dataSource);
//        properties.put(JdbcSettings.SHOW_SQL, false);
//        properties.put(JdbcSettings.FORMAT_SQL, false);
//        properties.put(JdbcSettings.USE_SQL_COMMENTS, false);

        properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, dataSource);
        properties.put(PersistenceUnitProperties.LOGGING_LOGGER, Slf4jSessionLogger.class.getName());

        properties.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_ONLY);
        properties.put(PersistenceUnitProperties.DDL_GENERATION_MODE, PersistenceUnitProperties.DDL_BOTH_GENERATION);
    }

    @Override
    protected void preConfigure(PersistenceConfiguration configuration) {
        super.preConfigure(configuration);
//        configuration.provider(HibernatePersistenceProvider.class.getName());
        configuration.transactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL);
    }

    @Initialize(profile = "!prod")
    public void init() {
        setProperty(PersistenceUnitProperties.LOGGING_LEVEL, "FINE");
        setProperty(PersistenceUnitProperties.LOGGING_PARAMETERS, "true");
        setProperty(PersistenceUnitProperties.CATEGORY_LOGGING_LEVEL_ + SessionLog.SQL, "FINE");
    }

}
