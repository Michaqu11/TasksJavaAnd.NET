package platformy_technologiczne.repositories;

import lombok.Data;
import platformy_technologiczne.Mage;
import platformy_technologiczne.Tower;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

@Data
public class TowerRepository {

    private final EntityManagerFactory emf;

    public TowerRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Tower> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Tower> list = em.createQuery("select e from " + Tower.class.getSimpleName() + " e", Tower.class).getResultList();
        em.close();
        return list;
    }

    public Tower find(Integer id) {
        EntityManager em = emf.createEntityManager();
        Tower entity = em.find(Tower.class, id);
        em.close();
        return entity;
    }
    public Tower findByName(String name){
        EntityManager em = emf.createEntityManager();
        Tower entity = em.find(Tower.class, name);
        em.close();
        return entity;
    }
    public void delete(Tower entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.merge(entity));
        transaction.commit();
        em.close();
    }

    public void add(Tower entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(entity);
        transaction.commit();
        em.close();
    }

    public void update(Tower entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(entity);
        transaction.commit();
        em.close();
    }

    protected EntityManagerFactory getEmf() {
        return emf;
    }

}


