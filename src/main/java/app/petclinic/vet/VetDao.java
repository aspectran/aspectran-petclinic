package app.petclinic.vet;

import app.petclinic.common.db.DefaultEntityManager;
import app.petclinic.common.pagination.PageInfo;
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.utils.annotation.jsr305.NonNull;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Repository class for <code>Vet</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
@Component
public class VetDao {

    private final EntityManager entityManager;

    private final JPAQueryFactory queryFactory;

    @Autowired
    public VetDao(DefaultEntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
    }

    public Vet findById(int id) {
        return entityManager.find(Vet.class, id);
    }

    public List<Vet> getVetList(@NonNull PageInfo pageInfo) {
        QVet vet = QVet.vet;
        List<Vet> listVets = queryFactory.selectFrom(vet)
                .offset(pageInfo.getOffset())
                .limit(pageInfo.getSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(vet.count())
                .from(vet);

        pageInfo.setTotalRecords(listVets.size(), countQuery::fetchOne);
        return listVets;
    }

}
