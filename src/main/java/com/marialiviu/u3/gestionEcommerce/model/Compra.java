package com.marialiviu.u3.gestionEcommerce.model;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "fecha_compra")
    private Date fechaCompra;

    public enum EstadoCompra {
        PENDIENTE, ENVIADO, ENTREGADO
    }

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoCompra estado;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "precio_total", columnDefinition = "DECIMAL(10,2)")
    private float precioTotal;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ArticuloCompra> articuloCompras = new HashSet<>();
    
    // --- ESTA ES LA PARTE IMPORTANTE MODIFICADA ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "nif_cif", nullable = false)
    private Cliente cliente;
    // ----------------------------------------------
    
    public Compra() {
        this.id = 0;
        this.fechaCompra = Date.from(Instant.now());
        this.estado = EstadoCompra.PENDIENTE;
        this.precioTotal = 0;
        this.articuloCompras = new HashSet<>();
    }

    /**
     * Constructor MODIFICADO:
     * Ahora recibe el OBJETO 'Cliente' en lugar del String 'idCliente'.
     * Esto es obligatorio para que Hibernate pueda guardar la relación.
     */
    public Compra(int id, Cliente cliente, Date fechaCompra, EstadoCompra estado, float precioTotal) {
        this.id = (id > 0) ? id : 0;
        this.cliente = cliente; // Asignamos el objeto real
        this.fechaCompra = (fechaCompra != null) ? fechaCompra : Date.from(Instant.now());
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

    /**
     * Obtiene el ID del cliente sacándolo del objeto Cliente.
     * Mantenemos este método por si lo usabas en otra parte del código.
     */
    public String getIdCliente() {
        return (cliente != null) ? cliente.getNif_cif() : null;
    }

    // He eliminado setIdCliente(String) porque no se debe setear el ID manualmente, 
    // hay que setear el objeto Cliente completo con setCliente().

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = (fechaCompra != null) ? fechaCompra : Date.from(Instant.now());
    }

    public EstadoCompra getEstado() {
        return estado;
    }

    public void setEstado(EstadoCompra estado) {
        this.estado = (estado != null) ? estado : EstadoCompra.PENDIENTE;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
    
    // --- GETTER Y SETTER DEL OBJETO CLIENTE (NUEVOS/NECESARIOS) ---
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // -------------------------------------------------------------

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
        // Ojo al toString: No imprimas el objeto cliente entero para evitar bucles infinitos
        String nif = (cliente != null) ? cliente.getNif_cif() : "null";
        return "Compra [id=" + id + ", idCliente=" + nif + ", fechaCompra=" + fechaCompra + ", estado=" + estado
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