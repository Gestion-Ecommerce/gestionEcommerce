package com.marialiviu.u3.gestionEcommerce.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase que representa la información fiscal de un cliente.
 *
 * TODO
 * <p><b>Ejemplo de uso:</b></p>
 * <pre>{@code
 * InformacionFiscal info = new InformacionFiscal();
 * System.out.println(info.getDireccion()); // Imprime:
 * }</pre>
 *
 * @author María
 * @version 1.0
 * @since 2025-11-27
 */

@Entity
@Table(name = "informacion_fiscal")
public class InformacionFiscal {
	
	@Id
	@Column(name = "nif_cif")
	private String nif_cif;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "direccion", columnDefinition = "LONGTEXT")
	private String direccion;
	
	// TODO añadir la relacion con tabla Cliente
	
	public InformacionFiscal() {
	}

	public String getNif_cif() {
		return nif_cif;
	}

	public void setNif_cif(String nif_cif) {
		this.nif_cif = nif_cif;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

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
		if (obj == null || getClass() != obj.getClass() && !(obj instanceof InformacionFiscal))
			return false;
		InformacionFiscal info = (InformacionFiscal) obj;
		if (nif_cif == null && info.nif_cif == null)
			return super.equals(obj);
		return nif_cif == info.nif_cif;
	}

	@Override
	public String toString() {
		return "InformacionFiscal [nif_cif=" + nif_cif + ", telefono=" + telefono + ", direccion=" + direccion + "]";
	}
}