package manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtils {
	private static EntityManagerFactory entityManagerFactory;
	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("BolnicaJPA");
	}

	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

}
