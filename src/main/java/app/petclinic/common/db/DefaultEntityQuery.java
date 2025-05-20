package app.petclinic.common.db;

import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.jpa.querydsl.EntityQuery;
import com.querydsl.jpa.JPQLTemplates;

/**
 * <p>Created: 2025-05-02</p>
 */
@Component
@Bean(lazyDestroy = true)
public class DefaultEntityQuery extends EntityQuery {

    public DefaultEntityQuery() {
        super("defaultEntityManagerAspect");
        setTemplates(JPQLTemplates.DEFAULT);
    }

}
