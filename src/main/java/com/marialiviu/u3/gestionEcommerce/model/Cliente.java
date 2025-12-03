package com.marialiviu.u3.gestionEcommerce.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
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
 * <li><b>nif_cif</b> - Identificador único del cliente (clave primaria).</li>
 * <li><b>nombreCompleto</b> - Nombre y apellidos del cliente.</li>
 * <li><b>email</b> - Dirección de correo electrónico.</li>
 * <li><b>fechaCreacion</b> - Fecha en la que se creó el registro del
 * cliente.</li>
 * </ul>
 *
 * Nota sobre igualdad y hashCode: la clase implementa {@code equals} y
 * {@code hashCode} basándose en el {@code nif_cif} cuando éste está presente.
 * Esto permite que la identidad lógica del cliente dependa de su NIF/CIF,
 * mientras que si el identificador es nulo se recurre al comportamiento por
 * defecto de {@code Object} para evitar colisiones prematuras.
 *
 * <p>
 * <b>Ejemplo de uso:</b>
 * </p>
 * 
 * <pre>{@code
 * Cliente c = new Cliente();
 * c.setNif_cif("12345678A");
 * c.setNombreCompleto("María Pérez");
 * c.setEmail("maria@example.com");
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
	 * NIF/CIF que identifica de forma única la información fiscal. Es la clave
	 * primaria de la tabla.
	 */
	@Id
	@Column(name = "nif_cif")
	private String nif_cif;

	/**
	 * Nombre completo del cliente.
	 */
	@Column(name = "nombre_completo")
	private String nombreCompleto;

	/**
	 * Correo electrónico del cliente con el que hace la compra.
	 */
	@Column(name = "email")
	private String email;

	/**
	 * Fecha y hora en la que se crea el cliente.
	 */
	@Column(name = "fecha_creacion")
	private LocalDate fechaCreacion;

	
	// TODO añadir relación con tabla Informacion Fiscal
	@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private InformacionFiscal informacionFiscal;


	// TODO añadir relacion con tabla Compra
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Compra> compras;
	
	/**
	 * Constructor que crea un objeto Cliente con valores por defecto.
	 */
	public Cliente() {
		this.nif_cif = "";
		this.nombreCompleto = "";
		this.email = "";
		//this.fechaCreacion = LocalDate.now();
		this.compras = new ArrayList<>();
	}

	/**
	 * Constructor que crea un objeto Cliente por parámetros. Si no se le pasan
	 * parámetros, se pondrá un valor por defecto.
	 * 
	 * @param nif_cif, nombreCompleto y email.
	 */
	public Cliente(String nif_cif, String nombreCompleto, String email) {
		this.nif_cif = (nif_cif != null) ? nif_cif.trim() : "";
		this.nombreCompleto = (nombreCompleto != null) ? nombreCompleto.trim() : "";
		this.email = (email != null) ? email.trim() : "";
		//this.fechaCreacion = LocalDate.now();
		this.compras = new ArrayList<>();
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
		this.nif_cif = (nif_cif != null) ? nif_cif.trim() : "";
	}

	/**
	 * Obtiene el nombre completo del cliente.
	 *
	 * @return el nombre del cliente. Si no tiene nombre, retornará una cadena
	 *         vacía.
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * Establece el nombre completo del cliente.
	 *
	 * @param nombreCompleto el nombre completo del cliente a asignar.
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = (nombreCompleto != null) ? nombreCompleto.trim() : "";
	}

	/**
	 * Obtiene correo electrónico del cliente.
	 *
	 * @return el correo electrónico. Si no existe, retornará una cadena vacía.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Establece el correo electrónico del cliente.
	 *
	 * @param email el correo electrónico del cliente a asignar.
	 */
	public void setEmail(String email) {
		this.email = (email != null) ? email.trim() : "";
	}

	/**
	 * Obtiene la fecha en la que se crea el cliente.
	 *
	 * @return la fecha de la creación del cliente.
	 */
	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Establece la fecha de creación del cliente.
	 * 
	 * No tiene parámetro ya que se le pondrá la fecha actual.
	 */
	public void setFechaCreacion(LocalDate fecha) {
		this.fechaCreacion = fecha;
	}
	
	@PrePersist
	public void prePersist() {
	    if (this.fechaCreacion == null) {
	        this.fechaCreacion = LocalDate.now();
	    }
	}

	/**
	 * Establece la información fiscal del cliente. 
	 *
	 * @param el objeto información fiscal. 
	 */
	public void setInformacionFiscal(InformacionFiscal info) {
		this.informacionFiscal = info;
		if (info != null && info.getCliente() != this) {
			info.setCliente(this);
		}
	}

	/**
	 * Obtiene la información fiscal del cliente.
	 *
	 * @return la información fiscal del cliente.
	 */
	public InformacionFiscal getInformacionFiscal() {
		return informacionFiscal;
	}
	
	/**
	 * Obtiene las compras realizadas por el cliente. 
	 *
	 * @return una lista con todas las compras realizadas por el cliente.
	 */
	public List<Compra> getCompras() {
	    return compras;
	}

	/**
	 * Establece las compras realizadas por el cliente.
	 *
	 * @param una lista de compras.
	 */
	public void setCompras(List<Compra> compras) {
	    this.compras = compras;
	}


	/**
	 * Representación en cadena de la instancia, útil para depuración.
	 * <p>
	 * Incluye el {@code nif_cif}, el {@code nombreCompleto}, el {@code email} y la
	 * {@code fechaCreacion}.
	 * </p>
	 *
	 * @return representación textual no nula de la entidad
	 */
	@Override
	public String toString() {
		return "Cliente [nif_cif=" + nif_cif + ", nombreCompleto=" + nombreCompleto + ", email=" + email
				+ ", fechaCreacion=" + fechaCreacion + "]";
	}

	/**
	 * Calcula el código hash consistente con {@link #equals(Object)}.
	 * <p>
	 * Si {@code nif_cif} no es {@code null}, el hash se basa en dicho valor. En
	 * caso contrario se delega en {@link System#identityHashCode(Object)} para
	 * evitar que dos instancias sin identificador aparente colisionen como si
	 * tuvieran la misma identidad lógica.
	 * </p>
	 *
	 * @return el código hash de la instancia
	 */

	@Override
	public int hashCode() {
		return nif_cif != null ? Objects.hash(nif_cif) : System.identityHashCode(this);
	}

	/**
	 * Calcula el código hash consistente con {@link #equals(Object)}.
	 * <p>
	 * Si {@code nif_cif} no es {@code null}, el hash se basa en dicho valor. En
	 * caso contrario se delega en {@link System#identityHashCode(Object)} para
	 * evitar que dos instancias sin identificador aparente colisionen como si
	 * tuvieran la misma identidad lógica.
	 * </p>
	 *
	 * @return el código hash de la instancia
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass() && !(obj instanceof Cliente))
			return false;
		Cliente other = (Cliente) obj;
		if (nif_cif == null && other.nif_cif == null)
			return super.equals(obj);
		return nif_cif != null && Objects.equals(nif_cif, other.getNif_cif());
	}
	
	/**
	 * Añade una compra a la lista de compras del cliente.
	 *
	 * @param un objeto compra.
	 */
	public void addCompra(Compra compra) {
	    if (compra == null) return;
	    if (!this.compras.contains(compra)) {
	        this.compras.add(compra);
	    }
	    if (compra.getCliente() != this) {
	        compra.setCliente(this);
	    }
	}

	/**
	 * Elimina una compra de la lista de compras del cliente. 
	 *
	 * @param un objeto compra.
	 */
	public void removeCompra(Compra compra) {
	    if (compra == null) return;
	    this.compras.remove(compra);
	    if (compra.getCliente() == this) {
	        compra.setCliente(null);
	    }
	}
	
}