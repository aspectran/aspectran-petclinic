package app.petclinic.common.db;

import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.jpa.EntityManagerFactoryBean;
import org.hibernate.cfg.AvailableSettings;

import javax.sql.DataSource;
import java.util.Map;

/**
 * <p>Created: 2025-05-02</p>
 */
@Component
@Bean(lazyDestroy = true)
public class DefaultEntityManagerFactory extends EntityManagerFactoryBean {

    private final DataSource dataSource;

    public DefaultEntityManagerFactory(DataSource dataSource) {
        super("petclinic");
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(Map<String, Object> properties) {
        super.configure(properties);
        properties.put(AvailableSettings.JAKARTA_NON_JTA_DATASOURCE, dataSource);
    }

}
