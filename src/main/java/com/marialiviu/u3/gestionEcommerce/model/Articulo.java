package com.marialiviu.u3.gestionEcommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa una compra del sistema de gestión del e-commerce.
 * <p>
 * Esta entidad se mapea a la tabla <code>articulo</code> de la base de datos
 * mediante JPA. El identificador único de la entidad es el campo
 * {@link #id}, que corresponde al ID del artículo.
 * </p>
 *
 * Campos principales:
 * <ul>
 * <li><b>id</b> - Identificador único del artículo (clave primaria).</li>
 * <li><b>nombre</b> - Nombre del artículo.</li>
 * <li><b>descripción</b> - La descripción del artículo.</li>
 * <li><b>precio_actual</b> - El precio del artículo.</li>
 * <li><b>stock</b> - La cantidad disponible del artículo.</li>
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
 * Articulo a = new Articulo();
 * c.setId("1");
 * c.setNombre("Pan");
 * c.setDescripcion("Pan integral");
 * c.setPrecioActual(1.99f);
 * c.setStock(100);
 * }</pre>
 *
 * @author Liviu
 * @version 1.0
 * @since 2025-11-27
 * @see ArticuloCompra
 */

@Entity
@Table(name = "articulos")
public class Articulo {

	/**
	 * Identificador único del articulo.
	 */
	@Id
	@Column(name = "id")
	private int id;

	/**
	 * Nombre del artículo.
	 */
	@Column(name = "nombre")
	private String nombre;

	/**
	 * Descripción del artículo.
	 */
	@Column(name = "descripcion")
	private String descripcion;

	/**
	 * Precio actual del artículo.
	 */
	@Column(name = "precio_actual", columnDefinition = "DECIMAL(10,2)")
	private float precioActual;

	/**
	 * Cantidad en stock del artículo.
	 */
	@Column(name = "stock")
	private int stock;

	/**
	 * Conjunto de ventas asociadas al artículo.
	 */
	@OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<ArticuloCompra> articuloCompras = new HashSet<>();

	/**
	 * Constructor por defecto que inicializa los campos con valores predeterminados.
	 */
	public Articulo() {
		this.id = 0;
		this.nombre = "";
		this.descripcion = "";
		this.precioActual = 0;
		this.stock = 0;
	}
	
	/**
	 * Constructor que inicializa un artículo con los valores proporcionados.
	 * @param id
	 * @param nombre
	 * @param descripcion
	 * @param precioActual
	 * @param stock
	 */
	public Articulo(int id, String nombre, String descripcion, float precioActual, int stock) {
		this.id = (id > 0) ? id : 0;
		this.nombre = (nombre != null) ? nombre.trim() : "";
		this.descripcion = (descripcion != null) ? descripcion.trim() : "";
		this.precioActual = (precioActual >= 0) ? precioActual : 0;
		this.stock = (stock >= 0) ? stock : 0;
	}

	/**
	 * Obtiene el ID del artículo.
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el ID del artículo.
	 * @param id
	 */
	public void setId(int id) {
		this.id = (id > 0) ? id : 0;
	}

	/**
	 * Obtiene el nombre del artículo.
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del artículo.
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = (nombre != null) ? nombre.trim() : "";
	}

	/**
	 * Obtiene la descripción del artículo.
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción del artículo.
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = (descripcion != null) ? descripcion.trim() : "";
	}

	/**
	 * Obtiene el precio actual del artículo.
	 * @return
	 */
	public float getPrecioActual() {
		return precioActual;
	}

	/**
	 * Establece el precio actual del artículo.
	 * @param precioActual
	 */
	public void setPrecioActual(float precioActual) {
		this.precioActual = (precioActual >= 0) ? precioActual : 0;
	}

	/**
	 * Obtiene la cantidad en stock del artículo.
	 * @return
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * Establece la cantidad en stock del artículo.
	 * @param stock
	 */
	public void setStock(int stock) {
		this.stock = (stock >= 0) ? stock : 0;
	}

	/**
	 * Obtiene el conjunto de ventas asociadas al artículo.
	 * @return
	 */
	public Set<ArticuloCompra> getArticuloCompras() {
		return articuloCompras;
	}

	/**
	 * Establece el conjunto de ventas asociadas al artículo.
	 * @param articuloCompras
	 */
	public void setArticuloCompras(Set<ArticuloCompra> articuloCompras) {
		this.articuloCompras = articuloCompras;
	}

	/**
	 * Añade una venta al conjunto de ventas del artículo.
	 * @param ac
	 */
	public void addArticuloCompra(ArticuloCompra ac) {
		if (ac == null) return;
		ac.setArticulo(this);
		this.articuloCompras.add(ac);
	}

	/**
	 * Elimina una venta del conjunto de ventas del artículo.
	 * @param ac
	 */
	public void removeArticuloCompra(ArticuloCompra ac) {
		if (ac == null) return;
		this.articuloCompras.remove(ac);
		ac.setArticulo(null);
	}

	/**
	 * Devuelve una representación en cadena del artículo.
	 */
	@Override
	public String toString() {
		return "Articulo [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precioActual="
				+ precioActual + ", stock=" + stock + ", ventas=" + articuloCompras.size() + "]";
	}

	/**
	 * Calcula el código hash del artículo basado en su ID.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/**
	 * Compara este artículo con otro objecto para determinar si son iguales basándose en su ID.
	 */
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