package TpIntegrador.dto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDTO {

    private String nombre;
    private float total_facturado;


    public ClienteDTO() {}

    public ClienteDTO(String nombre, float total_facturado) {
        this.total_facturado = total_facturado;
        this.nombre = nombre;
    }


    public String getNombre() {
        return nombre;
    }

    public float getTotal_facturado() {
        return total_facturado;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "nombre='" + nombre + '\'' +
                ", total_facturado=" + total_facturado +
                '}';
    }
}
