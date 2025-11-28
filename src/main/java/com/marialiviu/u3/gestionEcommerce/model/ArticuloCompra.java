package com.marialiviu.u3.gestionEcommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "articulo_compra")
public class ArticuloCompra {

	@Id
	@Column(name = "id_compra")
	private int idCompra;

	@Id
	@Column(name = "id_articulo")
	private int idArticulo;

	@Column(name = "unidades")
	private int unidades;

	@Column(name = "precio_compra")
	private float precioCompra;
	
	public ArticuloCompra() {
		this.idCompra = 0;
		this.idArticulo = 0;
		this.unidades = 0;
		this.precioCompra = 0;
	}
	
	public ArticuloCompra(int idCompra, int idArticulo, int unidades, float precioCompra) {
		this.idCompra = (idCompra > 0) ? idCompra : 0;
		this.idArticulo = (idArticulo > 0) ? idArticulo : 0;
		this.unidades = (unidades >= 0) ? unidades : 0;
		this.precioCompra = (precioCompra >= 0) ? precioCompra : 0;

	}

	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = (idCompra > 0) ? idCompra : 0;
	}

	public int getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(int idArticulo) {
		this.idArticulo = (idArticulo > 0) ? idArticulo : 0;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = (unidades >= 0) ? unidades : 0;
	}

	public float getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(float precioCompra) {
		this.precioCompra = (precioCompra >= 0) ? precioCompra : 0;
	}

	@Override
	public String toString() {
		return "ArticuloCompra [idCompra=" + idCompra + ", idArticulo=" + idArticulo + ", unidades=" + unidades
				+ ", precioCompra=" + precioCompra + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idArticulo;
		result = prime * result + idCompra;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticuloCompra other = (ArticuloCompra) obj;
		if (idArticulo != other.idArticulo)
			return false;
		if (idCompra != other.idCompra)
			return false;
		return true;
	}
}