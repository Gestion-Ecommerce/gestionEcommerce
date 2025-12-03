package com.marialiviu.u3.gestionEcommerce.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Representa un cliente del sistema de gestión del e-commerce.
 * <p>
 * Esta entidad se mapea a la tabla <code>informacion_fiscal</code> de la base
 * de datos mediante JPA. El identificador único de la entidad es el campo
 * {@link #nif_cif}, que corresponde al NIF/CIF del cliente.
 * </p>
 *
 * Campos principales:
 * <ul>
 * <li><b>nif_cif</b> - Identificador único del cliente (clave primaria).</li>
 * <li><b>telefono</b> - Número de teléfono del cliente.</li>
 * <li><b>direccion</b> - Dirección del cliente.</li>
 * </ul>
 *
 * Nota sobre igualdad y hashCode: la clase implementa {@code equals} y
 * {@code hashCode} basándose en el {@code nif_cif} cuando éste está presente.
 * Esto permite que la identidad lógica de la información fiscal dependa de su
 * NIF/CIF, mientras que si el identificador es nulo se recurre al
 * comportamiento por defecto de {@code Object} para evitar colisiones
 * prematuras.
 *
 * <p>
 * <b>Ejemplo de uso:</b>
 * </p>
 * 
 * <pre>{@code
 * InformacionFiscal info = new InformacionFiscal();
 * info.setNif_cif("12345678A");
 * info.setTelefono("926874632");
 * info.setDireccion("Calle Prueba, 25, 13000, Ciudad Real");
 * System.out.println(c.getTelefono()); // imprime: 926874632
 * }</pre>
 *
 * @author María
 * @version 1.0
 * @since 2025-11-27
 * @see Cliente
 */

@Entity
@Table(name = "informacion_fiscal")
public class InformacionFiscal {

	/**
	 * NIF/CIF que identifica de forma única la información fiscal. Es la clave
	 * primaria de la tabla.
	 */
	@Id
	@Column(name = "nif_cif")
	private String nif_cif;

	/**
	 * Teléfono de contacto asociado a la información fiscal.
	 */
	@Column(name = "telefono")
	private String telefono;

	/**
	 * Dirección fiscal completa.
	 */
	@Column(name = "direccion")
	private String direccion;

	// TODO añadir la relacion con tabla Cliente
	@OneToOne
	@MapsId // hace que esta entidad use la misma PK que cliente
	@JoinColumn(name = "nif_cif")
	private Cliente cliente;

	/**
	 * Constructor que crea un objeto Información Fiscal con valores por defecto.
	 */
	public InformacionFiscal() {
		this.nif_cif = "";
		this.telefono = "";
		this.direccion = "";
	}

	/**
	 * Constructor que crea un objeto Información Fiscal por parámetros. Si no se le
	 * pasan parámetros, se pondrá un valor por defecto.
	 * 
	 * @param nif_cif, telefono y dirección
	 */
	public InformacionFiscal(String nif_cif, String telefono, String direccion) {
		this.nif_cif = (nif_cif != null) ? nif_cif.trim() : "";
		this.telefono = (telefono != null) ? telefono.trim() : "";
		this.direccion = (direccion != null) ? direccion.trim() : "";
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
	 * Obtiene el teléfono asociado.
	 *
	 * @return el teléfono o {@code null} si no está establecido.
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Establece el teléfono asociado.
	 *
	 * @param telefono el teléfono a asignar.
	 */
	public void setTelefono(String telefono) {
		this.telefono = (telefono != null) ? telefono.trim() : "";
	}

	/**
	 * Obtiene la dirección fiscal.
	 *
	 * @return la dirección o {@code null} si no está establecida.
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Establece la dirección fiscal.
	 *
	 * @param direccion la dirección a asignar.
	 */
	public void setDireccion(String direccion) {
		this.direccion = (direccion != null) ? direccion.trim() : "";
	}

	/**
	 * Establece el cliente, asociando el nif_cif del ciente al de la información fiscal.
	 * Establenciendo así el cliente a su información fiscal.
	 *
	 * @param objeto cliente.
	 */
	public void setCliente(Cliente c) {
		this.cliente = c;
		if (c != null) {
	        this.nif_cif = c.getNif_cif();
	    }
	}

	/**
	 * Obtiene el cliente asociado a la información fiscal.
	 *
	 * @return el objeto cliente asociado a la información fiscal.
	 */
	public Cliente getCliente() {
		return cliente;
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
	 * Comprueba la igualdad lógica entre esta instancia y otro objeto.
	 * <p>
	 * Dos instancias de {@code InformacionFiscal} se consideran iguales si ambos
	 * son del mismo tipo y tienen el mismo {@code nif_cif}. La comparación maneja
	 * correctamente valores {@code null} mediante
	 * {@link java.util.Objects#equals(Object, Object)}.
	 * </p>
	 *
	 * @param obj el objeto a comparar
	 * @return {@code true} si los objetos son iguales según la definición anterior,
	 *         {@code false} en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		InformacionFiscal other = (InformacionFiscal) obj;
		// Usar Objects.equals para comparar cadenas (maneja nulls correctamente)
		return nif_cif != null && Objects.equals(nif_cif, other.getNif_cif());
	}

	/**
	 * Representación en cadena de la instancia, útil para depuración.
	 * <p>
	 * Incluye el {@code nif_cif}, el {@code telefono} y la {@code direccion}.
	 * </p>
	 *
	 * @return representación textual no nula de la entidad
	 */
	@Override
	public String toString() {
		return "InformacionFiscal [nif_cif=" + nif_cif + ", telefono=" + telefono + ", direccion=" + direccion + "]";
	}
}