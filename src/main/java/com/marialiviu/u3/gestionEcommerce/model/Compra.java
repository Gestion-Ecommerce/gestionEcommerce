package com.marialiviu.u3.gestionEcommerce.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
@Table(name = "compras")
public class Compra {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "id_cliente")
	private String idCliente;

	@Column(name = "fecha_compra")
	private LocalDate fechaCompra;

	public enum EstadoCompra {
		PENDIENTE, ENVIADO, ENTREGADO
	}

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private EstadoCompra estado;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "precio_total")
	private float precioTotal;

	// TODO añadir relación con tabla ArticuloCompra

	public Compra(int id, String idCliente, LocalDate fechaCompra, EstadoCompra estado, float precioTotal) {
		this.id = id;
		this.idCliente = idCliente;
		this.fechaCompra = fechaCompra;
		this.estado = estado;
		this.precioTotal = precioTotal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public LocalDate getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(LocalDate fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public EstadoCompra getEstado() {
		return estado;
	}

	public void setEstado(EstadoCompra estado) {
		this.estado = estado;
	}

	public float getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(float precioTotal) {
		this.precioTotal = precioTotal;
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + ", idCliente=" + idCliente + ", fechaCompra=" + fechaCompra + ", estado=" + estado
				+ ", precioTotal=" + precioTotal + "]";
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
		Compra other = (Compra) obj;
		if (id != other.id)
			return false;
		return true;
	}
}