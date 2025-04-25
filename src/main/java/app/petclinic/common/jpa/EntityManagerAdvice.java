package app.petclinic.common.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/**
 * <p>Created: 2025-04-24</p>
 */
public class EntityManagerAdvice {

    private final EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    private boolean arbitrarilyClosed;

    public EntityManagerAdvice(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void open() {
        if (entityManager == null) {
            entityManager = entityManagerFactory.createEntityManager();
            arbitrarilyClosed = false;
        }
    }

    public void flush() {
        if (checkSession()) {
            return;
        }
        entityManager.flush();
    }

    public void clear() {
        if (checkSession()) {
            return;
        }
        entityManager.clear();
    }

    public void close() {
        if (entityManager != null) {
            entityManager.close();
            entityManager = null;
        }
    }

    public boolean isArbitrarilyClosed() {
        return arbitrarilyClosed;
    }

    private boolean checkSession() {
        if (arbitrarilyClosed) {
            return true;
        }
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager is not open");
        }
        return false;
    }

}
