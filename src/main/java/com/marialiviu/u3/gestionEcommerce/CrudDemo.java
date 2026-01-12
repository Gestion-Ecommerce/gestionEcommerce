package com.marialiviu.u3.gestionEcommerce;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Date;

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

            // Artículos (algunos ya presentes, añadimos más para las pruebas)
            Articulo a1 = new Articulo(10, "Libro", "Libro de Java", 29.5f, 20);
            Articulo a2 = new Articulo(11, "Pendrive", "Pendrive 32GB", 12.0f, 100);
            Articulo a3 = new Articulo(12, "Auriculares", "Auriculares inalámbricos", 49.9f, 0); // sin stock
            Articulo a4 = new Articulo(13, "Ratón", "Ratón ergonómico", 19.95f, 5);

            em.persist(a1);
            em.persist(a2);
            em.persist(a3);
            em.persist(a4);

            // Clientes
            Cliente cliente1 = new Cliente("99999999Z", "Alumno Demo", "alumno@example.com");
            InformacionFiscal info1 = new InformacionFiscal("99999999Z", "600111222", "C/ Demo 1");
            cliente1.setInformacionFiscal(info1);
            em.persist(cliente1);

            Cliente cliente2 = new Cliente("88888888Y", "Cliente Enviado", "enviado@example.com");
            InformacionFiscal info2 = new InformacionFiscal("88888888Y", "600333444", "C/ Enviado 2");
            cliente2.setInformacionFiscal(info2);
            em.persist(cliente2);

            Cliente cliente3 = new Cliente("77777777X", "Cliente Entregado", "entregado@example.com");
            InformacionFiscal info3 = new InformacionFiscal("77777777X", "600555666", "C/ Entregado 3");
            cliente3.setInformacionFiscal(info3);
            em.persist(cliente3);

            // Compras - varios casos
            // 1) Compra con id=100 (PENDIENTE) para cliente1 con un item (ya en el demo original)
            Compra compra100 = new Compra(100, cliente1, null, Compra.EstadoCompra.PENDIENTE, 0f);
            ArticuloCompra ac100 = new ArticuloCompra(compra100, a2, 3, a2.getPrecioActual());
            compra100.addArticuloCompra(ac100);
            // calcular precio total
            compra100.setPrecioTotal(ac100.getUnidades() * ac100.getPrecioCompra());
            em.persist(compra100);

            // 2) Compra con varios items y estado ENVIADO
            Compra compra101 = new Compra(101, cliente2, new Date(), Compra.EstadoCompra.ENVIADO, 0f);
            ArticuloCompra ac101a = new ArticuloCompra(compra101, a1, 2, a1.getPrecioActual());
            ArticuloCompra ac101b = new ArticuloCompra(compra101, a4, 1, a4.getPrecioActual());
            compra101.addArticuloCompra(ac101a);
            compra101.addArticuloCompra(ac101b);
            float total101 = ac101a.getUnidades() * ac101a.getPrecioCompra() + ac101b.getUnidades() * ac101b.getPrecioCompra();
            compra101.setPrecioTotal(total101);
            em.persist(compra101);

            // 3) Compra entregada sin artículos (caso borde)
            Compra compra102 = new Compra(102, cliente3, new Date(), Compra.EstadoCompra.ENTREGADO, 0f);
            // Sin ArticuloCompra añadidos
            em.persist(compra102);

            // 4) Compra con artículo sin stock (comprobación de comportamiento)
            Compra compra103 = new Compra(103, cliente1, new Date(), Compra.EstadoCompra.PENDIENTE, 0f);
            ArticuloCompra ac103 = new ArticuloCompra(compra103, a3, 1, a3.getPrecioActual());
            compra103.addArticuloCompra(ac103);
            // Intentamos descontar stock: si está a 0 no lo dejamos negativo
            if (a3.getStock() >= ac103.getUnidades()) {
                a3.setStock(a3.getStock() - ac103.getUnidades());
            } else {
                System.out.println("Aviso: artículo id=" + a3.getId() + " sin stock suficiente para la compra103; stock actual=" + a3.getStock());
                // dejamos stock a 0
                a3.setStock(0);
            }
            compra103.setPrecioTotal(ac103.getUnidades() * ac103.getPrecioCompra());
            em.persist(compra103);

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

            // Actualizamos el estado de una compra (ej: 101 de ENVIADO -> ENTREGADO)
            Compra compraToUpdate = em.find(Compra.class, 101);
            if (compraToUpdate != null) {
                compraToUpdate.setEstado(Compra.EstadoCompra.ENTREGADO);
                em.merge(compraToUpdate);
                System.out.println("UPDATE Compra estado: " + compraToUpdate);
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
            em.createQuery("SELECT c FROM Compra c", Compra.class).getResultList().forEach(System.out::println);

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