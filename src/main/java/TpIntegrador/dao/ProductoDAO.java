package TpIntegrador.dao;

import TpIntegrador.entities.Cliente;
import TpIntegrador.entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAO {
    private Connection conn;
    public ProductoDAO(Connection connection) {
        this.conn = conn;
    }

    public void insertProducto(Producto producto) {
        String query = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, producto.getIdProducto()); // idPersona
            ps.setString(2, producto.getNombre()); // nombre
            ps.setFloat(3, producto.getValor()); // edad
            ps.executeUpdate();
            System.out.println("Cliente insertada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
    public boolean delete(int id) {
        String query = "DELETE FROM Producto WHERE idProducto = ?";
        PreparedStatement ps = null;
        boolean result = false;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id); // idPersona

            ps.executeUpdate();
            System.out.println("Producto borrado con exito.");
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Producto find(int id) {
        String query = "SELECT p.nombre, p.valor" +
                "FROM producto p " +
                "WHERE p.idProducto = ?";
        Producto productoById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id); // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                float valor = rs.getFloat("valor");


                // Crear una nueva instancia de Persona con los datos recuperados de la consulta
                productoById = new Producto(id, nombre, valor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return productoById;
    }
}
