package com.marialiviu.u3.gestionEcommerce.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase que representa un cliente.
 *
 * TODO
 * <p>
 * <b>Ejemplo de uso:</b>
 * </p>
 * 
 * <pre>{@code
 * Cliente cliente = new Cliente();
 * System.out.println(cliente.getEmail()); // Imprime:
 * }</pre>
 *
 * @author María
 * @version 1.0
 * @since 2025-11-27
 */

@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	@Column(name = "nif_cif")
	private String nif_cif;

	@Column(name = "nombre_completo")
	private String nombreCompleto;

	@Column(name = "email")
	private String email;

	@Column(name = "fecha_creacion")
	private LocalDate fechaCreacion;

	// TODO añadir relación con tabla Informacion Fiscal

	public Cliente() {

	}

	public String getNif_cif() {
		return nif_cif;
	}

	public void setNif_cif(String nif_cif) {
		this.nif_cif = nif_cif;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

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