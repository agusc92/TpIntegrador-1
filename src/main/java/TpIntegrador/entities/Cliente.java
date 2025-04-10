package TpIntegrador.entities;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String eMail;

    public Cliente(int idCliente, String nombre, String eMail) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.eMail = eMail;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", eMail=" + eMail +
                '}';
    }
}
