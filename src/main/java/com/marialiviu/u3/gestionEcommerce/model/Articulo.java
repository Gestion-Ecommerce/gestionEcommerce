package com.marialiviu.u3.gestionEcommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "articulos")
public class Articulo {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "precio_actual")
	private float precioActual;

	@Column(name = "stock")
	private int stock;

	// TODO añadir relación con tabla ArticuloCompra

	public Articulo() {
		this.id = 0;
		this.nombre = "";
		this.descripcion = "";
		this.precioActual = 0;
		this.stock = 0;
	}
	
	public Articulo(int id, String nombre, String descripcion, float precioActual, int stock) {
		this.id = (id > 0) ? id : 0;
		this.nombre = (nombre != null) ? nombre.trim() : "";
		this.descripcion = (descripcion != null) ? descripcion.trim() : "";
		this.precioActual = (precioActual >= 0) ? precioActual : 0;
		this.stock = (stock >= 0) ? stock : 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = (id > 0) ? id : 0;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = (nombre != null) ? nombre.trim() : "";
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = (descripcion != null) ? descripcion.trim() : "";
	}

	public float getPrecioActual() {
		return precioActual;
	}

	public void setPrecioActual(float precioActual) {
		this.precioActual = (precioActual >= 0) ? precioActual : 0;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = (stock >= 0) ? stock : 0;
	}

	@Override
	public String toString() {
		return "Articulo [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precioActual="
				+ precioActual + ", stock=" + stock + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Articulo other = (Articulo) obj;
		if (id != other.id)
			return false;
		return true;
	}
}