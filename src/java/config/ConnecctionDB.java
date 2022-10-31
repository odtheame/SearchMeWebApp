package config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnecctionDB {
    
	private final static ConnecctionDB instance = new ConnecctionDB();
	private final EntityManagerFactory factory;
        public static EntityManagerFactory emf = null;

	public ConnecctionDB(){
            factory = Persistence.createEntityManagerFactory("SearchMeWebAppPU");
	}
	
	public static ConnecctionDB getInstance(){
		return instance;
	}
	
	public EntityManagerFactory getFactory(){
		return factory;
	}
}
