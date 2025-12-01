package com.marialiviu.u3.gestionEcommerce.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

/**
 * Representa una compra del sistema de gestión del e-commerce.
 * <p>
 * Esta entidad se mapea a la tabla <code>compra</code> de la base de datos
 * mediante JPA. El identificador único de la entidad es el campo
 * {@link #id}, que corresponde al ID de la compra.
 * </p>
 *
 * Campos principales:
 * <ul>
 * <li><b>id</b> - Identificador único de la compra (clave primaria).</li>
 * <li><b>id_cliente</b> - Identificador del cliente relacionado a la compra.</li>
 * <li><b>fecha_compra</b> - Fecha y hora en la que se realizó la compra.</li>
 * <li><b>estado</b> - El estado de la compra.</li>
 * <li><b>dirrecion</b> - La dirreción de envío de la compra.</li>
 * <li><b>precio_total</b> - El precio total de la compra.</li>
 * </ul>
 *
 * Nota sobre igualdad y hashCode: la clase implementa {@code equals} y
 * {@code hashCode} basándose en el {@code id} cuando éste está presente.
 * Esto permite que la identidad lógica de la compra dependa de su ID,
 * mientras que si el identificador es nulo se recurre al comportamiento por
 * defecto de {@code Object} para evitar colisiones prematuras.
 *
 * TODO
 * <p>
 * <b>Ejemplo de uso:</b>
 * </p>
 * 
 * <pre>{@code
 * Compra c = new Compra();
 * c.setId("1");
 * c.setIdCliente("12345678A");
 * c.setFechaCompra(LocalDate.now());
 * c.setEstado(Compra.EstadoCompra.PENDIENTE);
 * c.setDireccion("Calle Falsa 123");
 * c.setPrecioTotal(99.99f);
 * }</pre>
 *
 * @author Liviu
 * @version 1.0
 * @since 2025-11-27
 * @see InformacionFiscal
 */

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

	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<ArticuloCompra> articuloCompras = new HashSet<>();
	
	public Compra() {
		this.id = 0;
		this.idCliente = "";
		this.fechaCompra = LocalDate.now();
		this.estado = EstadoCompra.PENDIENTE;
		this.precioTotal = 0;
		this.articuloCompras = new HashSet<>();
	}

	public Compra(int id, String idCliente, LocalDate fechaCompra, EstadoCompra estado, float precioTotal) {
		this.id = (id > 0) ? id : 0;
		this.idCliente = (idCliente != null) ? idCliente.trim() : "";
		this.fechaCompra = (fechaCompra != null) ? fechaCompra : LocalDate.now();
		this.estado = (estado != null) ? estado : EstadoCompra.PENDIENTE;
		this.precioTotal = (precioTotal >= 0) ? precioTotal : 0;
		this.articuloCompras = new HashSet<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = (id > 0) ? id : 0;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = (idCliente != null) ? idCliente.trim() : "";
	}

	public LocalDate getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(LocalDate fechaCompra) {
		this.fechaCompra = (fechaCompra != null) ? fechaCompra : LocalDate.now();
	}

	public EstadoCompra getEstado() {
		return estado;
	}

	public void setEstado(EstadoCompra estado) {
		this.estado = (estado != null) ? estado : EstadoCompra.PENDIENTE;
	}

	public float getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(float precioTotal) {
		this.precioTotal = (precioTotal >= 0) ? precioTotal : 0;
	}

	public Set<ArticuloCompra> getArticuloCompras() {
		return articuloCompras;
	}

	public void setArticuloCompras(Set<ArticuloCompra> articuloCompras) {
		this.articuloCompras = articuloCompras;
	}

	public void addArticuloCompra(ArticuloCompra ac) {
		if (ac == null) return;
		ac.setCompra(this);
		this.articuloCompras.add(ac);
	}

	public void removeArticuloCompra(ArticuloCompra ac) {
		if (ac == null) return;
		this.articuloCompras.remove(ac);
		ac.setCompra(null);
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + ", idCliente=" + idCliente + ", fechaCompra=" + fechaCompra + ", estado=" + estado
				+ ", precioTotal=" + precioTotal + ", items=" + articuloCompras.size() + "]";
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