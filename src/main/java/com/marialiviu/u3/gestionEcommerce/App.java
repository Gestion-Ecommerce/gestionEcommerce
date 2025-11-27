package com.marialiviu.u3.gestionEcommerce;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        
    	// Carga la configuración del persistence.xml
    	EntityManagerFactory emf = null;
    	EntityManager em = null;
    	
    	try {
			emf = Persistence.createEntityManagerFactory("ecommerce-jpa-pu");
			em = emf.createEntityManager();
			
			//TODO
			
		} catch (Exception e) {
			System.err.println("¡Error en la transacción!");
			e.printStackTrace();
			
		} finally {
			if(em != null) {
				em.close();
			}
			if(emf != null) {
				emf.close();
			}
			System.out.println("\n Aplicación finalizada");
		}
    }
}
