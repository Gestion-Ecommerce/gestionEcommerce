package com.marialiviu.u3.gestionEcommerce;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

import com.marialiviu.u3.gestionEcommerce.model.Articulo;
import com.marialiviu.u3.gestionEcommerce.model.ArticuloCompra;
import com.marialiviu.u3.gestionEcommerce.model.Cliente;
import com.marialiviu.u3.gestionEcommerce.model.Compra;
import com.marialiviu.u3.gestionEcommerce.model.InformacionFiscal;

/**
 * Clase de demostración para probar CRUD con JPA.
 */
public class CrudDemo {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("ecommerce-jpa-pu");
            em = emf.createEntityManager();

            System.out.println("--- INICIO DEMO CRUD ---\n");

            // CREATE
            em.getTransaction().begin();

            Articulo a1 = new Articulo(10, "Libro", "Libro de Java", 29.5f, 20);
            Articulo a2 = new Articulo(11, "Pendrive", "Pendrive 32GB", 12.0f, 100);
            em.persist(a1);
            em.persist(a2);

            Cliente cliente = new Cliente("99999999Z", "Alumno Demo", "alumno@example.com");
            InformacionFiscal info = new InformacionFiscal("99999999Z", "600111222", "C/ Demo 1");
            cliente.setInformacionFiscal(info);
            em.persist(cliente);

            Compra compra = new Compra(100, cliente.getNif_cif(), null, Compra.EstadoCompra.PENDIENTE, 0f);
            ArticuloCompra ac = new ArticuloCompra(compra, a2, 3, a2.getPrecioActual());
            compra.addArticuloCompra(ac);
            compra.setPrecioTotal(ac.getUnidades() * ac.getPrecioCompra());
            em.persist(compra);

            em.getTransaction().commit();
            System.out.println("CREATE: OK\n");

            // READ
            System.out.println("READ: listar clientes, articulos y compras:\n");
            TypedQuery<Cliente> qC = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
            List<Cliente> clientes = qC.getResultList();
            clientes.forEach(System.out::println);

            TypedQuery<Articulo> qA = em.createQuery("SELECT a FROM Articulo a", Articulo.class);
            List<Articulo> articulos = qA.getResultList();
            articulos.forEach(System.out::println);

            TypedQuery<Compra> qCp = em.createQuery("SELECT c FROM Compra c", Compra.class);
            List<Compra> compras = qCp.getResultList();
            compras.forEach(System.out::println);

            // UPDATE
            em.getTransaction().begin();
            Articulo toUpdate = em.find(Articulo.class, 11);
            if (toUpdate != null) {
                toUpdate.setPrecioActual(10.0f);
                toUpdate.setStock(toUpdate.getStock() - 1);
                em.merge(toUpdate);
                System.out.println("UPDATE Articulo: " + toUpdate);
            }

            Cliente clienteUpd = em.find(Cliente.class, "99999999Z");
            if (clienteUpd != null) {
                clienteUpd.setEmail("alumno.nuevo@example.com");
                em.merge(clienteUpd);
                System.out.println("UPDATE Cliente: " + clienteUpd);
            }
            em.getTransaction().commit();

            // DELETE
            em.getTransaction().begin();
            Compra toDelete = em.find(Compra.class, 100);
            if (toDelete != null) {
                em.remove(toDelete);
                System.out.println("DELETE Compra id=100");
            }
            em.getTransaction().commit();

            System.out.println("\nESTADO FINAL:\n");
            em.createQuery("SELECT a FROM Articulo a", Articulo.class).getResultList().forEach(System.out::println);
            em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList().forEach(System.out::println);

            System.out.println("--- FIN DEMO CRUD ---");

        } catch (Exception e) {
            System.err.println("Error en demo CRUD");
            e.printStackTrace();
            if (em != null && em.getTransaction().isActive()) em.getTransaction().rollback();
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}
