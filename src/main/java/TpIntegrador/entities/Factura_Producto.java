package TpIntegrador.entities;

public class Factura_Producto {
    private int idFactura;
    private int idProducto;
    private int cantidad;
    private int id;
    public Factura_Producto(int id,int idFactura, int idProducto, int cantidad) {
        this.id = id;
        this.idFactura = idFactura;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Factura_Producto{" +
                "idFactura=" + idFactura +
                ", idProducto='" + idProducto + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
