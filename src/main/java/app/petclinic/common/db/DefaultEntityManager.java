package app.petclinic.common.db;

import app.petclinic.common.jpa.EntityManagerAgent;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * <p>Created: 2025-05-02</p>
 */
@Component
@Bean(lazyDestroy = true)
public class DefaultEntityManager extends EntityManagerAgent {

    public DefaultEntityManager() {
        super("defaultEntityManagerAspect");
    }

    public JPAQueryFactory getQueryFactory() {
        return new JPAQueryFactory(JPQLTemplates.DEFAULT, this);
    }

}
