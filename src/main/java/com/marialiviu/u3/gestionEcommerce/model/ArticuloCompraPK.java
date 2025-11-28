package com.marialiviu.u3.gestionEcommerce.model;

import java.io.Serializable;
import java.util.Objects;

public class ArticuloCompraPK implements Serializable {
    private static final long serialVersionUID = 1L;

    public int compra;
    public int articulo;

    public ArticuloCompraPK() {
    }

    public ArticuloCompraPK(int compra, int articulo) {
        this.compra = compra;
        this.articulo = articulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticuloCompraPK that = (ArticuloCompraPK) o;
        return compra == that.compra && articulo == that.articulo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(compra, articulo);
    }
}
