package gt.carritogt.controller;

public class AgregarRequest {

    private Integer idProducto;
    private Integer cantidad;

    public AgregarRequest() {
    }

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    @Override
    public String toString() {
        return "AgregarRequest[" + idProducto + ", " + cantidad + "]";
    }
}
