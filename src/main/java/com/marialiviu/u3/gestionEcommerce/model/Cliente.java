package com.marialiviu.u3.gestionEcommerce.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa un cliente del sistema de gestión del e-commerce.
 * <p>
 * Esta entidad se mapea a la tabla <code>clientes</code> de la base de datos
 * mediante JPA. El identificador único de la entidad es el campo
 * {@link #nif_cif}, que corresponde al NIF/CIF del cliente.
 * </p>
 *
 * Campos principales:
 * <ul>
 *   <li><b>nif_cif</b> - Identificador único del cliente (clave primaria).</li>
 *   <li><b>nombreCompleto</b> - Nombre y apellidos del cliente.</li>
 *   <li><b>email</b> - Dirección de correo electrónico.</li>
 *   <li><b>fechaCreacion</b> - Fecha en la que se creó el registro del cliente.</li>
 * </ul>
 *
 * Nota sobre igualdad y hashCode: la clase implementa {@code equals} y
 * {@code hashCode} basándose en el {@code nif_cif} cuando éste está presente.
 * Esto permite que la identidad lógica del cliente dependa de su NIF/CIF,
 * mientras que si el identificador es nulo se recurre al comportamiento por
 * defecto de {@code Object} para evitar colisiones prematuras.
 *
 * TODO
 * <p>
 * <b>Ejemplo de uso:</b>
 * </p>
 * 
 * <pre>{@code
 * Cliente c = new Cliente();
 * c.setNif_cif("12345678A");
 * c.setNombreCompleto("María Pérez");
 * c.setEmail("maria@example.com");
 * c.setFechaCreacion(LocalDate.now());
 * System.out.println(c.getEmail()); // imprime: maria@example.com
 * }</pre>
 *
 * @author María
 * @version 1.0
 * @since 2025-11-27
 * @see InformacionFiscal
 */

@Entity
@Table(name = "clientes")
public class Cliente {
	
	/**
	 * NIF/CIF que identifica de forma única la información fiscal.
	 * Es la clave primaria de la tabla.
	 */
	@Id
	@Column(name = "nif_cif")
	private String nif_cif;
	
	/**
	 * Nombre completo del cliente.
	 */
	@Column(name="nombre_completo")
	private String nombreCompleto;
	
	/**
	 * Correo electrónico del cliente con el que hace la compra. 
	 */
	@Column(name="email")
	private String email;
	
	/**
	 * Fecha y hora en la que se crea el cliente.
	 */
	@Column(name="fecha_creacion")
	private LocalDate fechaCreacion;
	
	/**
	 * Array de las compras que hace el cliente.
	 */
	private List<Compra> compras;
	
	// TODO añadir relación con tabla Informacion Fiscal

	public Cliente(String nif_cif, String nombreCompleto, String email, LocalDate fechaCreacion) {
		this.nif_cif = nif_cif;
		this.nombreCompleto = nombreCompleto;
		this.email = email;
		this.fechaCreacion = fechaCreacion;
	}
	
	/**
	 * Obtiene el NIF/CIF.
	 *
	 * @return el NIF/CIF (clave primaria) o {@code null} si no está establecido.
	 */
	public String getNif_cif() {
		return nif_cif;
	}

	/**
	 * Establece el NIF/CIF.
	 *
	 * @param nif_cif el NIF/CIF a asignar.
	 */
	public void setNif_cif(String nif_cif) {
		this.nif_cif = nif_cif;
	}

	/**
	 * Obtiene el nombre completo del cliente.
	 *
	 * @return el nombre del cliente. Si no tiene nombre, retornará una cadena vacía.
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * Obtiene correo electrónico del cliente.
	 *
	 * @return el correo electrónico. Si no existe, retornará una cadena vacía.
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public String toString() {
		return "Cliente [nif_cif=" + nif_cif + ", nombreCompleto=" + nombreCompleto + ", email=" + email
				+ ", fechaCreacion=" + fechaCreacion + "]";
	}

	@Override
	public int hashCode() {
		return nif_cif != null ? Objects.hash(nif_cif) : System.identityHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass() && !(obj instanceof Cliente))
			return false;
		Cliente cliente = (Cliente) obj;
		if (nif_cif == null && cliente.nif_cif == null)
			return super.equals(obj);
		return nif_cif == cliente.nif_cif;
	}
}