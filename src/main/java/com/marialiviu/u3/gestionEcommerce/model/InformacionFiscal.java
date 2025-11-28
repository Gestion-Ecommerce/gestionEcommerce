package com.marialiviu.u3.gestionEcommerce.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa un cliente del sistema de gestión del e-commerce.
 * <p>
 * Esta entidad se mapea a la tabla <code>informacion_fiscal</code> de la base de datos
 * mediante JPA. El identificador único de la entidad es el campo
 * {@link #nif_cif}, que corresponde al NIF/CIF del cliente.
 * </p>
 *
 * Campos principales:
 * <ul>
 *   <li><b>nif_cif</b> - Identificador único del cliente (clave primaria).</li>
 *   <li><b>telefono</b> - Número de teléfono del cliente.</li>
 *   <li><b>direccion</b> - Dirección del cliente.</li>
 * </ul>
 *
 * Nota sobre igualdad y hashCode: la clase implementa {@code equals} y
 * {@code hashCode} basándose en el {@code nif_cif} cuando éste está presente.
 * Esto permite que la identidad lógica del cliente dependa de su NIF/CIF,
 * mientras que si el identificador es nulo se recurre al comportamiento por
 * defecto de {@code Object} para evitar colisiones prematuras.
 *
 * <p><b>Ejemplo de uso:</b></p>
 * <pre>{@code
 * Cliente c = new Cliente();
 * c.setNif_cif("12345678A");
 * c.setTelefono("926874632");
 * c.setDireccion("Calle Prueba, 25, 13000, Ciudad Real");
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
	 * NIF/CIF que identifica de forma única la información fiscal.
	 * Es la clave primaria de la tabla.
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
	
	/**
	 * Constructor sin argumentos requerido por JPA.
	 */
	public InformacionFiscal() {
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
		this.telefono = telefono;
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
		this.direccion = direccion;
	}

	@Override
	public int hashCode() {
		return nif_cif != null ? Objects.hash(nif_cif) : System.identityHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		InformacionFiscal other = (InformacionFiscal) obj;
		// Usar Objects.equals para comparar cadenas (maneja nulls correctamente)
		return Objects.equals(nif_cif, other.nif_cif);
	}

	@Override
	public String toString() {
		return "InformacionFiscal [nif_cif=" + nif_cif + ", telefono=" + telefono + ", direccion=" + direccion + "]";
	}
}