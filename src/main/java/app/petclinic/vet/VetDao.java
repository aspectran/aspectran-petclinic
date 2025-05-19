package app.petclinic.vet;

import app.petclinic.common.db.DefaultEntityQuery;
import app.petclinic.common.pagination.PageInfo;
import com.aspectran.core.component.bean.annotation.Autowired;
import com.aspectran.core.component.bean.annotation.Component;
import com.aspectran.jpa.EntityQuery;
import com.aspectran.utils.annotation.jsr305.NonNull;
import com.querydsl.core.Fetchable;

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

    private final EntityQuery entityQuery;

    @Autowired
    public VetDao(DefaultEntityQuery entityQuery) {
        this.entityQuery = entityQuery;
    }

    public Vet findById(int id) {
        return entityQuery.find(Vet.class, id);
    }

    public List<Vet> findAll(@NonNull PageInfo pageInfo) {
        QVet vet = QVet.vet;
        List<Vet> listVets = entityQuery
                .selectFrom(vet)
                .offset(pageInfo.getOffset())
                .limit(pageInfo.getSize())
                .fetch();

        Fetchable<Long> countQuery = entityQuery
                .select(vet.count())
                .from(vet);

        pageInfo.setTotalElements(listVets.size(), countQuery::fetchOne);
        return listVets;
    }

}
