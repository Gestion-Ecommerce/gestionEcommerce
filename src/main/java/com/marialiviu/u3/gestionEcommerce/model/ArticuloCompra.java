package com.marialiviu.u3.gestionEcommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import java.util.Objects;

@Entity
@Table(name = "articulo_compra")
public class ArticuloCompra {

    @Id
    @Column(name = "id_compra")
    private Integer compraId;

    @Id
    @Column(name = "id_articulo")
    private Integer articuloId;

    @ManyToOne
    @JoinColumn(name = "id_compra", referencedColumnName = "id", insertable = false, updatable = false)
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "id_articulo", referencedColumnName = "id", insertable = false, updatable = false)
    private Articulo articulo;

    @Column(name = "unidades")
    private int unidades;

    @Column(name = "precio_compra")
    private float precioCompra;

    public ArticuloCompra() {
        this.compraId = null;
        this.articuloId = null;
        this.compra = null;
        this.articulo = null;
        this.unidades = 0;
        this.precioCompra = 0;
    }

    public ArticuloCompra(Compra compra, Articulo articulo, int unidades, float precioCompra) {
        this();
        this.compra = compra;
        this.articulo = articulo;
        this.compraId = (compra != null) ? compra.getId() : null;
        this.articuloId = (articulo != null) ? articulo.getId() : null;
        this.unidades = (unidades >= 0) ? unidades : 0;
        this.precioCompra = (precioCompra >= 0) ? precioCompra : 0;
    }

    public Integer getCompraId() {
        return compraId;
    }

    public void setCompraId(Integer compraId) {
        this.compraId = compraId;
    }

    public Integer getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Integer articuloId) {
        this.articuloId = articuloId;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
        this.compraId = (compra != null) ? compra.getId() : null;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
        this.articuloId = (articulo != null) ? articulo.getId() : null;
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
        int idC = (compraId != null) ? compraId : 0;
        int idA = (articuloId != null) ? articuloId : 0;
        return "ArticuloCompra [idCompra=" + idC + ", idArticulo=" + idA + ", unidades=" + unidades
                + ", precioCompra=" + precioCompra + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(compraId, articuloId);
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
        return Objects.equals(compraId, other.compraId) && Objects.equals(articuloId, other.articuloId);
    }
}