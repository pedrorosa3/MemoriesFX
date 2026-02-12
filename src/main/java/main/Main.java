package main;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static EntityManagerFactory emf = null;
    
    public static EntityManagerFactory initEMF(String persistenceunit) {
    	if(emf!=null && emf.isOpen()) {
    		emf.close();
    	}
    	emf = Persistence.createEntityManagerFactory(persistenceunit);
    	return emf;
    }

}
