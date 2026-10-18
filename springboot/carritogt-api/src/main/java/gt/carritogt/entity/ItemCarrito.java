package gt.carritogt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shopping_cart")
public class ItemCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idShoppingCart;
    private String codUsuario;
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    public ItemCarrito() {
    }

    public Integer getIdShoppingCart() { return idShoppingCart; }
    public void setIdShoppingCart(Integer idShoppingCart) { this.idShoppingCart = idShoppingCart; }
    public String getCodUsuario() { return codUsuario; }
    public void setCodUsuario(String codUsuario) { this.codUsuario = codUsuario; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    @Override
    public String toString() {
        return "ItemCarrito[" + idShoppingCart + ", " + codUsuario + ", " + producto + " x" + cantidad + "]";
    }
}
