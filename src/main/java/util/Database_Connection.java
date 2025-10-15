package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Database_Connection {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cinema_Watchlist_System");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close(){
        if(emf != null && emf.isOpen()){
            emf.close();
        }
    }
}

