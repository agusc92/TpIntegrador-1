package TpIntegrador.dao;

import TpIntegrador.entities.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    private Connection conn;
    public ClienteDAO(Connection connection) {
        this.conn = conn;
    }

    public void insertCliente(Cliente cliente) {
        String query = "INSERT INTO Cliente (idCliente, nombre, eMail) VALUES (?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, cliente.getIdCliente()); // idPersona
            ps.setString(2, cliente.getNombre()); // nombre
            ps.setString(3, cliente.geteMail()); // edad
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
        String query = "DELETE FROM Cliente WHERE idCliente = ?";
        PreparedStatement ps = null;
        boolean result = false;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id); // idPersona

            ps.executeUpdate();
            System.out.println("Cliente borrado con exito.");
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

    public Cliente find(Integer id) {
        String query = "SELECT c.nombre, c.eMail" +
                "FROM cliente c " +
                "WHERE p.idPersona = ?";
        Cliente clienteById = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id); // Establecer el parámetro en la consulta SQL
            rs = ps.executeQuery();
            if (rs.next()) { // Verificar si hay resultados
                String nombre = rs.getString("nombre");
                String eMail = rs.getString("eMail");


                // Crear una nueva instancia de Persona con los datos recuperados de la consulta
                clienteById = new Cliente(id, nombre, eMail);
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

        return clienteById;
    }

    //preguntar si hay que hacer el delete
}
