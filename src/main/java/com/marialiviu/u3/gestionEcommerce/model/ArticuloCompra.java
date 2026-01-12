package com.marialiviu.u3.gestionEcommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import java.util.Objects;

/**
 * Representa la relación entre un artículo y una compra en el sistema de
 * gestión del e-commerce.
 * <p>
 * Esta entidad se mapea a la tabla <code>articulo_compra</code> de la base de datos
 * mediante JPA. El identificador único de la entidad es la combinación
 * {@link #compraId} y {@link #articuloId}, que corresponden a los IDs
 * de la compra y del artículo respectivamente.
 * </p>
 *
 * Campos principales:
 * <ul>
 * <li><b>compraId</b> - Identificador de la compra (clave primaria).</li>
 * <li><b>articuloId</b> - Identificador del artículo (clave primaria).</li>
 * <li><b>unidades</b> - Cantidad de unidades del artículo en la compra.</li>
 * <li><b>precio_compra</b> - Precio del artículo en el momento de la compra.</li>
 * </ul>
 *
 * Nota sobre igualdad y hashCode: la clase implementa {@code equals} y
 * {@code hashCode} basándose en la combinación de {@code compraId} y
 * {@code articuloId}. Esto permite que la identidad lógica de la relación
 * dependa de ambos identificadores.
 *
 * TODO
 * <p>
 * <b>Ejemplo de uso:</b>
 * </p>
 * 
 * <pre>{@code
 * Articulo articulo = new Articulo(...);
 * Compra compra = new Compra(...);
 * ArticuloCompra articuloCompra = new ArticuloCompra(compra, articulo, 5, 19.99f);
 * System.out.println(articuloCompra);
 * }</pre>
 *
 * @author Liviu
 * @version 1.0
 * @since 2025-11-27
 * @see Articulo
 * @see Compra
 */

@Entity
@Table(name = "articulo_compra")
public class ArticuloCompra {

	/**
	 * Identificador de la compra.
	 */
    @Id
    @Column(name = "id_compra")
    private Integer compraId;

    /**
     * Identificador del artículo.
     */
    @Id
    @Column(name = "id_articulo")
    private Integer articuloId;

    /**
     * Referencia a la entidad Compra asociada.
     */
    @ManyToOne
    @JoinColumn(name = "id_compra", referencedColumnName = "id", insertable = false, updatable = false)
    private Compra compra;

    /**
     * Referencia a la entidad Artículo asociada.
     */
    @ManyToOne
    @JoinColumn(name = "id_articulo", referencedColumnName = "id", insertable = false, updatable = false)
    private Articulo articulo;

    /**
     * Cantidad de unidades del artículo en la compra.
     */
    @Column(name = "unidades")
    private int unidades;

    /**
     * Precio del artículo en el momento de la compra.
     */
    @Column(name = "precio_compra", columnDefinition = "DECIMAL(10,2)")
    private float precioCompra;

    /**
     * Constructor por defecto que inicializa los campos a valores predeterminados.
     */
    public ArticuloCompra() {
        this.compraId = null;
        this.articuloId = null;
        this.compra = null;
        this.articulo = null;
        this.unidades = 0;
        this.precioCompra = 0;
    }

    /**
     * Constructor parametrizado para crear una instancia de ArtículoCompra con
     * valores específicos.
     * @param compra
     * @param articulo
     * @param unidades
     * @param precioCompra
     */
    public ArticuloCompra(Compra compra, Articulo articulo, int unidades, float precioCompra) {
        this();
        this.compra = compra;
        this.articulo = articulo;
        this.compraId = (compra != null) ? compra.getId() : null;
        this.articuloId = (articulo != null) ? articulo.getId() : null;
        this.unidades = (unidades >= 0) ? unidades : 0;
        this.precioCompra = (precioCompra >= 0) ? precioCompra : 0;
    }

    /**
     * Obtiene el identificador de la compra.
     * @return
     */
    public Integer getCompraId() {
        return compraId;
    }

    /**
     * Establece el identificador de la compra.
     * @param compraId
     */
    public void setCompraId(Integer compraId) {
        this.compraId = compraId;
    }

    /**
     * Obtiene el identificador del artículo.
     * @return
     */
    public Integer getArticuloId() {
        return articuloId;
    }

    /**
     * Establece el identificador del artículo.
     * @param articuloId
     */
    public void setArticuloId(Integer articuloId) {
        this.articuloId = articuloId;
    }

    /**
     * Obtiene la compra asociada.
     * @return
     */
    public Compra getCompra() {
        return compra;
    }

    /**
     * Establece la compra asociada.
     * @param compra
     */
    public void setCompra(Compra compra) {
        this.compra = compra;
        this.compraId = (compra != null) ? compra.getId() : null;
    }

    /**
     * Obtiene el artículo asociado.
     * @return
     */
    public Articulo getArticulo() {
        return articulo;
    }
    /**
     * Establece el artículo asociado.
     * @param articulo
     */
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
        this.articuloId = (articulo != null) ? articulo.getId() : null;
    }

    /**
     * Obtiene la cantidad de unidades del artículo en la compra.
     * @return
     */
    public int getUnidades() {
        return unidades;
    }

    /**
     * Establece la cantidad de unidades del artículo en la compra.
     * @param unidades
     */
    public void setUnidades(int unidades) {
        this.unidades = (unidades >= 0) ? unidades : 0;
    }

    /**
     * Obtiene el precio del artículo en el momento de la compra.
     * @return
     */
    public float getPrecioCompra() {
        return precioCompra;
    }

    /**
     * Establece el precio del artículo en el momento de la compra.
     * @param precioCompra
     */
    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = (precioCompra >= 0) ? precioCompra : 0;
    }

    /**
     * Devuelve una representación en cadena del objecto ArtículoCompra.
     */
    @Override
    public String toString() {
        int idC = (compraId != null) ? compraId : 0;
        int idA = (articuloId != null) ? articuloId : 0;
        return "ArticuloCompra [idCompra=" + idC + ", idArticulo=" + idA + ", unidades=" + unidades
                + ", precioCompra=" + precioCompra + "]";
    }

    /**
     * Genera un código hash basado en los identificadores de compra y artículo.
     */
    @Override
    public int hashCode() {
        return Objects.hash(compraId, articuloId);
    }

    /**
     * Compara este objeto con otro para determinar igualdad basada en los
     * identificadores de compra y artículo.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ArticuloCompra other = (ArticuloCompra) obj;
        return Objects.equals(compraId, other.compraId) && Objects.equals(articuloId, other.articuloId);
    }
}