package TpIntegrador.utilities;

import TpIntegrador.entities.Cliente;
import TpIntegrador.entities.Factura;
import TpIntegrador.entities.Factura_Producto;
import TpIntegrador.entities.Producto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelperMySQL {

    private Connection conn = null;

    public HelperMySQL() {//Constructor
        String driver = "com.mysql.cj.jdbc.Driver";
        String uri = "jdbc:mysql://localhost:3306/integrador1";

        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(uri, "root", "");
            conn.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (conn != null){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dropTables() throws SQLException {
        String dropCliente = "DROP TABLE IF EXISTS cliente";
        this.conn.prepareStatement(dropCliente).execute();
        this.conn.commit();

        String dropProducto = "DROP TABLE IF EXISTS producto";
        this.conn.prepareStatement(dropProducto).execute();
        this.conn.commit();

        String dropFactura= "DROP TABLE IF EXISTS factura";
        this.conn.prepareStatement(dropFactura).execute();
        this.conn.commit();

        String dropProducto_factura = "DROP TABLE IF EXISTS producto_factura";
        this.conn.prepareStatement(dropProducto_factura).execute();
        this.conn.commit();





    }

    public void createTables() throws SQLException {
        String tableCliente = "CREATE TABLE IF NOT EXISTS cliente(" +
                "idCliente INT NOT NULL, " +
                "nombre VARCHAR(500), " +
                "eMail VARCHAR(150), " +

                "CONSTRAINT cliente_pk PRIMARY KEY (idCliente));" ;
        this.conn.prepareStatement(tableCliente).execute();
        this.conn.commit();
        String tableProducto = "CREATE TABLE IF NOT EXISTS producto(" +
                "idProducto INT NOT NULL, " +
                "nombre VARCHAR(45), " +
                "valor float NOT NULL, " +
                "CONSTRAINT producto_pk PRIMARY KEY (idProducto)); ";
        this.conn.prepareStatement(tableProducto).execute();
        this.conn.commit();

        String tableFactura = "CREATE TABLE IF NOT EXISTS factura(" +
                "idFactura INT NOT NULL, " +
                "idCliente INT, "+
                "CONSTRAINT factura_pk PRIMARY KEY (idFactura),"+

                "CONSTRAINT cliente_fk FOREIGN KEY (idCliente) REFERENCES cliente(idCliente) );";
        this.conn.prepareStatement(tableFactura).execute();
        this.conn.commit();

        String tableFactura_Producto = "CREATE TABLE IF NOT EXISTS factura_producto (" +
                "idProducto INT NOT NULL, " +
                "idFactura INT NOT NULL, " +
                "cantidad INT, " +
                "CONSTRAINT factura_producto_factura_fk FOREIGN KEY (idFactura) REFERENCES factura(idFactura), " +
                "CONSTRAINT factura_producto_producto_fk FOREIGN KEY (idProducto) REFERENCES producto(idProducto), " +
                "PRIMARY KEY (idProducto, idFactura)" +
                ");";
        this.conn.prepareStatement(tableFactura_Producto).execute();
        this.conn.commit();
    }

    private Iterable<CSVRecord> getData(String archivo) throws IOException {
        String path = "src\\main\\resources\\" + archivo;
        Reader in = new FileReader(path);
        String[] header = {};  // Puedes configurar tu encabezado personalizado aquí si es necesario
        CSVParser csvParser = CSVFormat.EXCEL.withHeader(header).parse(in);

        Iterable<CSVRecord> records = csvParser.getRecords();
        return records;
    }
    public void populateDB() throws Exception {
        System.out.println("Populating DB...");
        for(CSVRecord row : getData("clientes.csv")) {
            if(row.size() >= 3) { // Verificar que hay al menos 4 campos en el CSVRecord
                String idString = row.get(0);

                if(!idString.isEmpty()) {
                    try {
                        int id = Integer.parseInt(idString);
                        Cliente cliente = new Cliente(id, row.get(1), row.get(2));
                        insertCliente(cliente);
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato en datos de dirección: " + e.getMessage());
                    }
                }
            }
        }
        System.out.println("Direcciones insertadas");

        for (CSVRecord row : getData("productos.csv")) {
            if (row.size() >= 3) { // Verificar que hay al menos 4 campos en el CSVRecord
                String idString = row.get(0);
                String nombre = row.get(1);
                String valorString = row.get(2);


                if (!idString.isEmpty() && !nombre.isEmpty() && !valorString.isEmpty()) {
                    try {
                        int id = Integer.parseInt(idString);
                        float valor = Float.parseFloat(valorString);

                        Producto producto = new Producto(id, nombre, valor);
                        insertProducto(producto);
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato en datos de persona: " + e.getMessage());
                    }
                }
            }
        }

        for (CSVRecord row : getData("facturas.csv")) {
            if (row.size() >= 2) { // Verificar que hay al menos 4 campos en el CSVRecord
                String idString = row.get(0);
                String idClienteString = row.get(1);



                if (!idString.isEmpty() && !idClienteString.isEmpty() ) {
                    try {
                        int id = Integer.parseInt(idString);
                        int idCliente = Integer.parseInt(idClienteString);


                        Factura factura = new Factura(id, idCliente);
                        insertFactura(factura);
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato en datos de persona: " + e.getMessage());
                    }
                }
            }
        }
        for (CSVRecord row : getData("facturas-productos.csv")) {
            if (row.size() >= 3) { // Verificar que hay al menos 4 campos en el CSVRecord

                String idStringFactura = row.get(0);
                String idStringProducto = row.get(1);
                String stringCantidad = row.get(2);


                if (!idStringFactura.isEmpty() && !idStringProducto.isEmpty()&& !stringCantidad.isEmpty()) {
                    try {

                        int idFactura = Integer.parseInt(idStringFactura);
                        int idProducto = Integer.parseInt(idStringProducto);
                        int cantidad = Integer.parseInt(stringCantidad);

                        Factura_Producto fp = new Factura_Producto(idFactura, idProducto,cantidad);
                        insertFactura_Producto(fp);
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato en datos de persona: " + e.getMessage());
                    }
                }
            }
        }

        System.out.println("Personas insertadas");

    }
    public void insertCliente(Cliente cliente) {
        String query = "INSERT INTO cliente (idCliente, nombre, eMail) VALUES (?, ?, ?)";
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
    public void insertFactura(Factura factura) {
        String query = "INSERT INTO factura (idFactura, idCliente) VALUES (?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, factura.getIdFactura());


                ps.setInt(2, factura.getIdCliente());


            ps.executeUpdate();
            System.out.println("Factura insertada exitosamente.");
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
    public void insertFactura_Producto(Factura_Producto fp){

            String query = "INSERT INTO factura_producto ( idProducto, idFactura, cantidad) VALUES ( ?, ?, ?)";
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement(query);
                ps.setInt(1, fp.getIdProducto());
                ps.setInt(2, fp.getIdFactura());
                ps.setInt(3, fp.getCantidad());
                ps.executeUpdate();
                System.out.println("Factura_Producto insertada exitosamente.");
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

}
