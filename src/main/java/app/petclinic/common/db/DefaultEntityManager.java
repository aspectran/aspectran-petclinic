package app.petclinic.common.db;

import app.petclinic.common.jpa.EntityManagerAgent;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;

/**
 * <p>Created: 2025-05-02</p>
 */
@Component
@Bean(lazyDestroy = true)
public class DefaultEntityManager extends EntityManagerAgent {

    public DefaultEntityManager() {
        super("defaultEntityManagerAspect");
    }

}
