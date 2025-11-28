package com.marialiviu.u3.gestionEcommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "articulo_compra")
@IdClass(ArticuloCompraPK.class)
public class ArticuloCompra {

	@Id
	@ManyToOne
	@JoinColumn(name = "id_compra", referencedColumnName = "id")
	private Compra compra;

	@Id
	@ManyToOne
	@JoinColumn(name = "id_articulo", referencedColumnName = "id")
	private Articulo articulo;

	@Column(name = "unidades")
	private int unidades;

	@Column(name = "precio_compra")
	private float precioCompra;
	
	public ArticuloCompra() {
		this.compra = null;
		this.articulo = null;
		this.unidades = 0;
		this.precioCompra = 0;
	}
	
	public ArticuloCompra(Compra compra, Articulo articulo, int unidades, float precioCompra) {
		this.compra = compra;
		this.articulo = articulo;
		this.unidades = (unidades >= 0) ? unidades : 0;
		this.precioCompra = (precioCompra >= 0) ? precioCompra : 0;

	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
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
		int idC = (compra != null) ? compra.getId() : 0;
		int idA = (articulo != null) ? articulo.getId() : 0;
		return "ArticuloCompra [idCompra=" + idC + ", idArticulo=" + idA + ", unidades=" + unidades
				+ ", precioCompra=" + precioCompra + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		int idA = (articulo != null) ? articulo.getId() : 0;
		int idC = (compra != null) ? compra.getId() : 0;
		result = prime * result + idA;
		result = prime * result + idC;
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
		int idA = (articulo != null) ? articulo.getId() : 0;
		int otherIdA = (other.articulo != null) ? other.articulo.getId() : 0;
		if (idA != otherIdA)
			return false;
		int idC = (compra != null) ? compra.getId() : 0;
		int otherIdC = (other.compra != null) ? other.compra.getId() : 0;
		if (idC != otherIdC)
			return false;
		return true;
	}
}