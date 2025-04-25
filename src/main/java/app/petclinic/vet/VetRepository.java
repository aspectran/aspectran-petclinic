package app.petclinic.vet;

import app.petclinic.common.jpa.EntityManagerAgent;
import app.petclinic.common.jpa.dao.AbstractJpaDao;
import app.petclinic.common.pagination.PageInfo;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * <p>Created: 2025-04-25</p>
 */
public class VetRepository extends AbstractJpaDao<Vet> {

    public VetRepository(EntityManagerAgent entityManagerAgent) {
        super(entityManagerAgent, Vet.class);
    }

    public Vet findById(Long id) {
        return entityManagerAgent.find(Vet.class, id);
    }

    @Transactional
    public List<Vet> findAll(PageInfo pageInfo) {
        entityManagerAgent.getTransaction().
    }


}
