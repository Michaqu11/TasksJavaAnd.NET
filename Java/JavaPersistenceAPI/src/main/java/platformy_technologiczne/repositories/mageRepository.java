package platformy_technologiczne.repositories;

import platformy_technologiczne.Mage;
import platformy_technologiczne.Tower;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class mageRepository {

    private final EntityManagerFactory emf;

    public mageRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Mage> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Mage> list = em.createQuery("select e from " + Mage.class.getSimpleName() + " e", Mage.class).getResultList();
        em.close();
        return list;
    }


    public Mage find(Integer id) {
        EntityManager em = emf.createEntityManager();
        Mage entity = em.find(Mage.class, id);
        em.close();
        return entity;
    }

    public Mage findByName(String name){
        EntityManager em = emf.createEntityManager();
        Mage entity = em.find(Mage.class, name);
        em.close();
        return entity;
    }

    public void delete(Mage entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.merge(entity));
        transaction.commit();
        em.close();
    }

    public void add(Mage entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
      //  entity.getTower().getMages().add(entity);
       // System.out.println(entity.getTower().getName() + " " + entity.getTower().getMages());
        em.persist(entity);
        transaction.commit();
        em.close();
    }

    public void update(Mage entity) {
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


