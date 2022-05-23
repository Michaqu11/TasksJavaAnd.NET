package platformy_technologiczne;

import org.testng.annotations.Test;
import platformy_technologiczne.repositories.TowerRepository;
import platformy_technologiczne.repositories.mageRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String PERSISTENCE_UNIT_NAME = "magesPu";

    public static void addMagesAndTower(String towerName, int height) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Tower tower = new Tower();
        tower.setName(towerName);
        tower.setHeight(height);
        em.persist(tower);

        for (int i = 0; i < 10; i++) {
            Mage m = new Mage("as", 12, tower);
            m.setName("Gandalf" + (i+1));
            m.setLevel(i);
            m.setTower(tower);
            em.persist(m);

            tower.getMages().add(m);
            em.persist(m);
            em.persist(tower);
        }

        em.getTransaction().commit();

        em.close();
    }
    @Test
    public void deleteMag(Integer id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT m FROM Mage m WHERE m.id = :id"
        );
        q.setParameter("id", 1);
        Mage mag = (Mage) q.getSingleResult();
        System.out.println("a1");
        em.remove(mag);
        System.out.println("a2");
        em.getTransaction().commit();
        System.out.println("a3");
        Mage m = (Mage) q.getSingleResult();
        em.close();
        emf.close();

    }
    public static void deleteTower(String name) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT t FROM Tower t WHERE t.name = :name");
        q.setParameter("name", name);
        Tower t = (Tower) q.getSingleResult();
        System.out.println(t);
        em.remove(t);
        System.out.println("a2");
        em.getTransaction().commit();
        // Begin a new local transaction so that we can persist a new entity
        System.out.println("a3");
        em.close();
        emf.close();

    }

    public static void printTowersWithMags() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select t from Tower t");
        List<Tower> towers = q.getResultList();
        System.out.println(towers);
        em.close();
        emf.close();

        // We should have one family with 40 persons

    }
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = emf.createEntityManager();
        mageRepository mageRep = new mageRepository(emf);
        TowerRepository towerRep = new TowerRepository(emf);
        Boolean play = true;


        while(play) {
            System.out.println("podaj komende:");
            Scanner scan = new Scanner(System.in);
            String com = scan.nextLine();
            switch(com){
                case "addMage":
                    System.out.println("podaj imie maga");
                    String name = scan.nextLine();
                    System.out.println("podaj poziom maga");
                    int lvl = Integer.parseInt(scan.nextLine());
                    System.out.println("podaj nazwe wiezy");
                    String towerName = scan.nextLine();
                    Tower tower = towerRep.findByName(towerName);
                    if(tower != null) {
                        mageRep.add(new Mage(name, lvl, tower));
                    }
                    else{
                        System.out.println("nie ma takiej wiezy");
                    }
                    break;
                case "addTower":
                    Tower t = new Tower();
                    System.out.println("podaj nazwe wiezy");
                    t.setName(scan.nextLine());
                    System.out.println("podaj wysokosc wiezy");
                    t.setHeight(Integer.parseInt(scan.nextLine()));
                    towerRep.add(t);
                    break;

                case "fastInit":
                    Tower t1 = new Tower();
                    t1.setName("babel");
                    t1.setHeight(Integer.parseInt("300"));
                    towerRep.add(t1);

                    Tower t2 = new Tower();
                    t2.setName("wpizie");
                    t2.setHeight(Integer.parseInt("121"));
                    towerRep.add(t2);

                    Tower t3 = new Tower();
                    t3.setName("pacholek");
                    t3.setHeight(Integer.parseInt("20"));
                    towerRep.add(t3);

                    mageRep.add(new Mage("mag1", 7, t1));
                    mageRep.add(new Mage("mag2", 7, t1));
                    mageRep.add(new Mage("mag3", 17, t1));
                    mageRep.add(new Mage("mag4", 41, t1));
                    mageRep.add(new Mage("mag5", 71, t1));
                    mageRep.add(new Mage("mag6", 3, t1));
                    mageRep.add(new Mage("mag7", 17, t1));
                    mageRep.add(new Mage("mag8", 23, t1));

                    mageRep.add(new Mage("mag11", 2, t2));
                    mageRep.add(new Mage("mag12", 46, t2));
                    mageRep.add(new Mage("mag13", 53, t2));
                    mageRep.add(new Mage("mag14", 12, t2));
                    mageRep.add(new Mage("mag15", 99, t2));
                    mageRep.add(new Mage("mag16", 3, t2));

                    break;


                case "deleteMage":
                    System.out.println("podaj nazwe maga którego chcesz zabic");
                    name = scan.nextLine();
                    Mage mage = mageRep.findByName(name);
                    if(mage!=null){
                        mageRep.delete(mage);
                        System.out.println("Mag " + name + " zostal zamordowany");
                    }
                    else{
                        System.out.println("Nie istnieje taki mag");
                    }
                    break;
                case "deleteTower":
                    System.out.println("podaj nazwe wiezy do usuniecia");
                    towerName = scan.nextLine();
                    tower = towerRep.findByName(towerName);
                    if(tower!=null){
                        towerRep.delete(tower);
                        System.out.println("Wieza zostala zbuzona, wszyscy magowie zgineli");
                    }
                    else{
                        System.out.println("Nie ma takiej wiezy");
                    }
                    break;
                case "print":
                    List<Tower> towers = towerRep.findAll();
                    for(int i =0 ; i < towers.size();i++){
                        System.out.println("wieza: " + towers.get(i).getName() + " ma wysokość: " + towers.get(i).getHeight());
                    }
                    List<Mage> mages = mageRep.findAll();
                    for(int i =0 ; i < mages.size();i++){
                        System.out.println("Mag: " + mages.get(i).getName() + " ma poziom: " + mages.get(i).getLevel() + " i mieszka we wiezy: "+mages.get(i).getTower().getName());
                    }
                    break;
                case "quit":
                    play = false;
                    break;
            }
        }
        // pobranie wszystkich magów z poziomem większym niż 5
        String queryString = "SELECT m FROM Mage m WHERE m.level > 5";
        Query query = em.createQuery(queryString, Mage.class);
        List<Mage> mages = query.getResultList();
        System.out.println(mages);

        //pobranie wszystkie wież niższych niż
        queryString = "SELECT t FROM Tower t WHERE t.height < 800";
        query = em.createQuery(queryString, Tower.class);
        List<Tower> towers = query.getResultList();
        System.out.println(towers);

        //pobranie wszystkich magów z poziomem wyższym niż z poziom max maga w danej wieży
        queryString = "SELECT MAX(m.level) FROM Tower t, Mage m WHERE m.tower = t AND t.name = :name";
        query = em.createQuery(queryString);
        query.setParameter("name", "babel");
        int maxlvl = (int) query.getSingleResult();
        queryString = "SELECT m FROM Mage m WHERE m.level > :lvl";
        query = em.createQuery(queryString, Mage.class);
        query.setParameter("lvl", maxlvl);
        mages = query.getResultList();
        System.out.println(mages);

        emf.close();

    }
}
