package app.petclinic.common.db;

import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Bean;
import com.aspectran.core.component.bean.annotation.Component;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * <p>Created: 2025. 5. 5.</p>
 */
@Component
@Bean(lazyDestroy = true)
public class DefaultQueryFactory extends JPAQueryFactory {

    @Autowired
    public DefaultQueryFactory(DefaultEntityManager entityManager) {
        super(JPQLTemplates.DEFAULT, entityManager);
    }

}
