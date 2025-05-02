package app.petclinic.common.db;

import app.petclinic.common.jpa.EntityManagerFactoryBean;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;

/**
 * <p>Created: 2025-05-02</p>
 */
@Component
@Bean(lazyDestroy = true)
public class DefaultEntityManagerFactory extends EntityManagerFactoryBean {

    public DefaultEntityManagerFactory() {
        super("petclinic");
    }

}
