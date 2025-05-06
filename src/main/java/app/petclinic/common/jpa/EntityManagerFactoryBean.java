package app.petclinic.common.jpa;

import com.aspectran.core.component.bean.ablility.DisposableBean;
import com.aspectran.core.component.bean.ablility.InitializableFactoryBean;
import com.aspectran.core.component.bean.annotation.AvoidAdvice;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * <p>Created: 2025-04-24</p>
 */
@AvoidAdvice
public class EntityManagerFactoryBean  implements InitializableFactoryBean<EntityManagerFactory>, DisposableBean {

    private final String persistenceUnitName;

    private EntityManagerFactory entityManagerFactory;

    public EntityManagerFactoryBean(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }

    @Override
    public EntityManagerFactory getObject() throws Exception {
        return entityManagerFactory;
    }

    @Override
    public void initialize() throws Exception {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        }
    }

    @Override
    public void destroy() throws Exception {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
    }

}
