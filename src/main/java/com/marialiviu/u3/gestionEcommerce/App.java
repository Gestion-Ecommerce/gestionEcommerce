package com.marialiviu.u3.gestionEcommerce;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Date;
import java.util.Scanner;

import com.marialiviu.u3.gestionEcommerce.model.Articulo;
import com.marialiviu.u3.gestionEcommerce.model.ArticuloCompra;
import com.marialiviu.u3.gestionEcommerce.model.Cliente;
import com.marialiviu.u3.gestionEcommerce.model.Compra;
import com.marialiviu.u3.gestionEcommerce.model.InformacionFiscal;

/**
 * Aplicación interactiva por consola que replica las operaciones de `CrudDemo`.
 */
public class App {
	
	/**
	 * Punto de entrada de la aplicación.
	 * @param args
	 */
	public static void main(String[] args) {

		// Carga la configuración del persistence.xml
		EntityManagerFactory emf = null;
		EntityManager em = null;
		Scanner sc = new Scanner(System.in);

		try {
			emf = Persistence.createEntityManagerFactory("ecommerce-jpa-pu");
			em = emf.createEntityManager();

			boolean salir = false;
			while (!salir) {
				printMenu();
				System.out.print("Elige una opción: ");
				String opt = sc.nextLine().trim();
				switch (opt) {
				case "1": createArticuloInteractive(em, sc); break;
				case "2": createClienteInteractive(em, sc); break;
				case "3": createCompraInteractive(em, sc); break;
				case "4": listAll(em); break;
				case "5": updateArticuloInteractive(em, sc); break;
				case "6": updateClienteInteractive(em, sc); break;
				case "7": updateCompraEstadoInteractive(em, sc); break;
				case "8": deleteCompraInteractive(em, sc); break;
				case "9": salir = true; break;
				default: System.out.println("Opción no válida");
				}
				System.out.println();
			}

		} catch (Exception e) {
			System.err.println("¡Error en la aplicación!");
			e.printStackTrace();
			if (em != null && em.getTransaction().isActive()) em.getTransaction().rollback();
		} finally {
			sc.close();
			if (em != null) em.close();
			if (emf != null) emf.close();
			System.out.println("\nAplicación finalizada");
		}
	}
	/**
	 * Imprime el menú de opciones.
	 */
	private static void printMenu() {
		System.out.println("--- GESTIÓN ECOMMERCE (INTERACTIVO) ---");
		System.out.println("1) Crear Artículo");
		System.out.println("2) Crear Cliente");
		System.out.println("3) Crear Compra");
		System.out.println("4) Listar Artículos/Clientes/Compras");
		System.out.println("5) Actualizar Artículo (precio/stock)");
		System.out.println("6) Actualizar Cliente (email)");
		System.out.println("7) Actualizar Estado de Compra");
		System.out.println("8) Borrar Compra por id");
		System.out.println("9) Salir");
	}

	/**
	 * Crea un artículo de forma interactiva.
	 * @param em
	 * @param sc
	 */
	private static void createArticuloInteractive(EntityManager em, Scanner sc) {
		try {
			System.out.print("Id (int): ");
			int id = Integer.parseInt(sc.nextLine().trim());
			if (em.find(Articulo.class, id) != null) {
				System.out.println("Articulo con id=" + id + " ya existe.");
				return;
			}
			System.out.print("Nombre: ");
			String nombre = sc.nextLine();
			System.out.print("Descripción: ");
			String desc = sc.nextLine();
			System.out.print("Precio (float): ");
			float precio = Float.parseFloat(sc.nextLine().trim());
			System.out.print("Stock (int): ");
			int stock = Integer.parseInt(sc.nextLine().trim());

			em.getTransaction().begin();
			Articulo a = new Articulo(id, nombre, desc, precio, stock);
			em.persist(a);
			em.getTransaction().commit();
			System.out.println("Articulo creado: " + a);
		} catch (Exception e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			System.err.println("Error creando artículo: " + e.getMessage());
		}
	}
	/**
	 * Crea un cliente de forma interactiva.
	 * @param em
	 * @param sc
	 */
	private static void createClienteInteractive(EntityManager em, Scanner sc) {
		try {
			System.out.print("NIF/CIF: ");
			String nif = sc.nextLine().trim();
			if (nif.isEmpty()) { System.out.println("NIF no puede estar vacío"); return; }
			if (em.find(Cliente.class, nif) != null) { System.out.println("Cliente ya existe"); return; }
			System.out.print("Nombre completo: ");
			String nombre = sc.nextLine();
			System.out.print("Email: ");
			String email = sc.nextLine();
			System.out.print("Teléfono (info fiscal): ");
			String tel = sc.nextLine();
			System.out.print("Dirección fiscal: ");
			String dir = sc.nextLine();

			em.getTransaction().begin();
			Cliente c = new Cliente(nif, nombre, email);
			InformacionFiscal info = new InformacionFiscal(nif, tel, dir);
			c.setInformacionFiscal(info);
			em.persist(c);
			em.getTransaction().commit();
			System.out.println("Cliente creado: " + c);
		} catch (Exception e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			System.err.println("Error creando cliente: " + e.getMessage());
		}
	}
	
	/**
	 * Crea una compra interactiva.
	 * @param em
	 * @param sc
	 */
	private static void createCompraInteractive(EntityManager em, Scanner sc) {
		try {
			System.out.print("NIF/CIF cliente para la compra: ");
			String nif = sc.nextLine().trim();
			Cliente cliente = em.find(Cliente.class, nif);
			if (cliente == null) { System.out.println("Cliente no encontrado"); return; }

			int nextId = getNextCompraId(em);
			Compra compra = new Compra(nextId, cliente, new Date(), Compra.EstadoCompra.PENDIENTE, 0f);

			boolean añadir = true;
			while (añadir) {
				System.out.println("Añadir artículo a la compra? (s/n): ");
				String r = sc.nextLine().trim().toLowerCase();
				if (!r.equals("s")) break;
				System.out.print("Id artículo: ");
				int idArt = Integer.parseInt(sc.nextLine().trim());
				Articulo art = em.find(Articulo.class, idArt);
				if (art == null) { System.out.println("Artículo no encontrado"); continue; }
				System.out.print("Unidades: ");
				int uds = Integer.parseInt(sc.nextLine().trim());
				ArticuloCompra ac = new ArticuloCompra(compra, art, uds, art.getPrecioActual());
				compra.addArticuloCompra(ac);
			}

			// calcular total
			float total = 0f;
			for (ArticuloCompra ac : compra.getArticuloCompras()) {
				total += ac.getUnidades() * ac.getPrecioCompra();
			}
			compra.setPrecioTotal(total);

			em.getTransaction().begin();
			em.persist(compra);
			em.getTransaction().commit();
			System.out.println("Compra creada: " + compra);
		} catch (Exception e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			System.err.println("Error creando compra: " + e.getMessage());
		}
	}
	/**
	 * Obtiene el siguiente id disponible para una compra.
	 * @param em
	 * @return
	 */
	private static int getNextCompraId(EntityManager em) {
		TypedQuery<Integer> q = em.createQuery("SELECT MAX(c.id) FROM Compra c", Integer.class);
		Integer max = q.getSingleResult();
		return (max == null) ? 1 : (max + 1);
	}

	/**
	 * Lista todos los artículos, clientes y compras.
	 * @param em
	 */
	private static void listAll(EntityManager em) {
		System.out.println("--- ARTÍCULOS ---");
		List<Articulo> articulos = em.createQuery("SELECT a FROM Articulo a", Articulo.class).getResultList();
		articulos.forEach(System.out::println);
		System.out.println("--- CLIENTES ---");
		List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
		clientes.forEach(System.out::println);
		System.out.println("--- COMPRAS ---");
		List<Compra> compras = em.createQuery("SELECT c FROM Compra c", Compra.class).getResultList();
		compras.forEach(System.out::println);
	}

	/**
	 * Actualiza el precio y/o stock de un artículo.
	 * @param em
	 * @param sc
	 */
	private static void updateArticuloInteractive(EntityManager em, Scanner sc) {
		try {
			System.out.print("Id artículo a actualizar: ");
			int id = Integer.parseInt(sc.nextLine().trim());
			Articulo a = em.find(Articulo.class, id);
			if (a == null) { System.out.println("No existe artículo"); return; }
			System.out.print("Nuevo precio (o ENTER para mantener " + a.getPrecioActual() + "): ");
			String p = sc.nextLine().trim();
			System.out.print("Nuevo stock (o ENTER para mantener " + a.getStock() + "): ");
			String s = sc.nextLine().trim();
			em.getTransaction().begin();
			if (!p.isEmpty()) a.setPrecioActual(Float.parseFloat(p));
			if (!s.isEmpty()) a.setStock(Integer.parseInt(s));
			em.merge(a);
			em.getTransaction().commit();
			System.out.println("Artículo actualizado: " + a);
		} catch (Exception e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			System.err.println("Error actualizando artículo: " + e.getMessage());
		}
	}
	
	/**
	 * Actualiza el email de un cliente.
	 * @param em
	 * @param sc
	 */
	private static void updateClienteInteractive(EntityManager em, Scanner sc) {
		try {
			System.out.print("NIF/CIF cliente a actualizar: ");
			String nif = sc.nextLine().trim();
			Cliente c = em.find(Cliente.class, nif);
			if (c == null) { System.out.println("Cliente no encontrado"); return; }
			System.out.print("Nuevo email (o ENTER para mantener " + c.getEmail() + "): ");
			String email = sc.nextLine().trim();
			em.getTransaction().begin();
			if (!email.isEmpty()) c.setEmail(email);
			em.merge(c);
			em.getTransaction().commit();
			System.out.println("Cliente actualizado: " + c);
		} catch (Exception e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			System.err.println("Error actualizando cliente: " + e.getMessage());
		}
	}
	
	/**
	 * Actualiza el estado de una compra (PENDIENTE, ENVIADO, ENTREGADO).
	 * @param em
	 * @param sc
	 */
	private static void updateCompraEstadoInteractive(EntityManager em, Scanner sc) {
		try {
			System.out.print("Id compra a actualizar: ");
			int id = Integer.parseInt(sc.nextLine().trim());
			Compra c = em.find(Compra.class, id);
			if (c == null) { System.out.println("Compra no encontrada"); return; }
			System.out.println("Estado actual: " + c.getEstado());
			System.out.print("Nuevo estado (PENDIENTE/ENVIADO/ENTREGADO): ");
			String e = sc.nextLine().trim().toUpperCase();
			em.getTransaction().begin();
			try {
				c.setEstado(Compra.EstadoCompra.valueOf(e));
				em.merge(c);
				em.getTransaction().commit();
				System.out.println("Compra actualizada: " + c);
			} catch (IllegalArgumentException ex) {
				em.getTransaction().rollback();
				System.out.println("Estado no válido");
			}
		} catch (Exception ex) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			System.err.println("Error actualizando compra: " + ex.getMessage());
		}
	}

	/**
	 * Borra una compra por su id.
	 * @param em
	 * @param sc
	 */
	private static void deleteCompraInteractive(EntityManager em, Scanner sc) {
		try {
			System.out.print("Id compra a borrar: ");
			int id = Integer.parseInt(sc.nextLine().trim());
			Compra c = em.find(Compra.class, id);
			if (c == null) { System.out.println("Compra no encontrada"); return; }
			em.getTransaction().begin();
			em.remove(c);
			em.getTransaction().commit();
			System.out.println("Compra borrada");
		} catch (Exception ex) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			System.err.println("Error borrando compra: " + ex.getMessage());
		}
	}

}