package app.petclinic.common.jpa.dao;

import jakarta.persistence.EntityManager;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Created: 2025-04-25</p>
 */
public abstract class AbstractJpaDao<T extends Serializable> {

    private final EntityManager entityManager;

    private final Class<T> entityClass;

    public AbstractJpaDao(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public T findOne(long id) {
        return entityManager.find(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("from " + entityClass.getName())
                .getResultList();
    }

    public void create(T entity) {
        entityManager.persist(entity);
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(long entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }

}
