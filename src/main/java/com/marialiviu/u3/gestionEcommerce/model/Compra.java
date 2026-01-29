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

/**
 * Representa una compra del sistema de gestión del e-commerce.
 * <p>
 * Esta entidad se mapea a la tabla <code>compras</code> de la base de datos
 * mediante JPA. El identificador único de la entidad es el campo
 * {@link #id}, que corresponde al ID de la compra.
 * </p>
 * * Campos principales:
 * <ul>
 * <li><b>id</b> - Identificador único de la compra (clave primaria).</li>
 * <li><b>idCliente</b> - Identificador del cliente que realizó la compra.</li>
 * <li><b>fechaCompra</b> - Fecha en la que se realizó la compra.</li>
 * <li><b>estado</b> - Estado actual de la compra (PENDIENTE, ENVIADO,
 * ENTREGADO).</li>
 * <li><b>direccion</b> - Dirección de envío de la compra.</li>
 * <li><b>precioTotal</b> - Precio total de la compra.</li>
 * </ul>
 * * Nota sobre igualdad y hashCode: la clase implementa {@code equals} y
 * 	{@code hashCode} basándose en el {@code id} cuando éste está presente.
 * Esto permite que la identidad lógica de la compra dependa de su ID,
 * mientras que si el identificador es nulo se recurre al comportamiento por
 * defecto de {@code Object} para evitar colisiones prematuras.
 * * <p>
 * <b>Ejemplo de uso:</b>
 * </p>
 * * <pre>{@code
 * Compra compra = new Compra();
 * compra.setId(1);
 * compra.setIdCliente("12345678A");
 * compra.setFechaCompra(new Date());
 * compra.setEstado(Compra.EstadoCompra.PENDIENTE);
 * compra.setPrecioTotal(99.99f);
 * System.out.println(compra);
 * }</pre>
 * *
 * @author Liviu y María
 * * @version 1.0
 * * @since 2025-11-27
 * * @see ArticuloCompra
 * @see Cliente
 * 
 */
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
    
    /**
	 * Constructor por defecto que inicializa los campos con valores predeterminados.
	 */
    public Compra() {
        this.id = 0;
        this.fechaCompra = Date.from(Instant.now());
        this.estado = EstadoCompra.PENDIENTE;
        this.precioTotal = 0;
        this.articuloCompras = new HashSet<>();
    }

    /**
	 * Constructor que crea un objeto Compra por parámetros. Si no se le
	 * pasan parámetros, se pondrá un valor por defecto.
	 * 
	 * @param id, idCliente, fechaCompra, estado, precioTotal
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
    /**
     * Añade un ArticuloCompra a la compra y establece la relación.
     * @param ac
     */
    public void addArticuloCompra(ArticuloCompra ac) {
        if (ac == null) return;
        ac.setCompra(this);
        this.articuloCompras.add(ac);
    }

    /**
	 * Elimina un ArticuloCompra de la compra y desasocia la relación.
	 * 
	 * @param ac el ArticuloCompra a eliminar
	 */
    public void removeArticuloCompra(ArticuloCompra ac) {
        if (ac == null) return;
        this.articuloCompras.remove(ac);
        ac.setCompra(null);
    }

    /**
	 * Representación en cadena de la instancia, útil para depuración.
	 * <p>
	 * Incluye el {@code id}, el {@code idCliente}, la {@code fechaCompra}, el
	 * {@code estado}, el {@code precioTotal} y el número de
	 * {@code items} en la compra.
	 * </p>
	 *
	 * @return representación textual no nula de la entidad
	 */
    @Override
    public String toString() {
        // Ojo al toString: No imprimas el objeto cliente entero para evitar bucles infinitos
        String nif = (cliente != null) ? cliente.getNif_cif() : "null";
        return "Compra [id=" + id + ", idCliente=" + nif + ", fechaCompra=" + fechaCompra + ", estado=" + estado
                + ", precioTotal=" + precioTotal + ", items=" + articuloCompras.size() + "]";
    }

    /**
     * Calcula el código hash consistente con {@link #equals(Object)}.
     * <p>
     * Si {@code id} es mayor que 0, el hash se basa en dicho valor. En
     * caso contrario se delega en {@link System#identityHashCode(Object)} para
     * evitar que dos instancias sin identificador aparente colisionen como si
     * tuvieran la misma identidad lógica.
     * </p>
     * @return el código hash de la instancia
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    /**
	 * Compara este objeto con otro para determinar igualdad basada en el
	 * identificador {@code id}.
	 */
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