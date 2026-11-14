package main;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static EntityManagerFactory emf = null;
    
    public static EntityManagerFactory initEMF(String persistenceunit) {
    	if(emf!=null && emf.isOpen()) {
    		emf.close();
    	}
    	// Permite usar outra base de dados (ex.: nos testes) com -Dmemoriesfx.db.url=jdbc:derby:...
    	Map<String, String> props = new HashMap<>();
    	String dbUrl = System.getProperty("memoriesfx.db.url");
    	if (dbUrl != null && !dbUrl.isEmpty()) {
    		props.put("javax.persistence.jdbc.url", dbUrl);
    	}
    	emf = Persistence.createEntityManagerFactory(persistenceunit, props);
    	return emf;
    }

}
