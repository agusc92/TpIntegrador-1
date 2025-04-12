package TpIntegrador;

import TpIntegrador.dao.ClienteDAO;
import TpIntegrador.dao.ProductoDAO;
import TpIntegrador.dto.ClienteDTO;
import TpIntegrador.dto.ProductoDTO;
import TpIntegrador.entities.Cliente;
import TpIntegrador.entities.Producto;
import TpIntegrador.factory.AbstractFactory;
import TpIntegrador.utilities.HelperMySQL;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        HelperMySQL helper = new HelperMySQL();
        //helper.createTables();
        //helper.populateDB();
        AbstractFactory chosenFactory = AbstractFactory.getDAOFactory(1);
        ProductoDAO productoDao = chosenFactory.getProductoDAO();
        ClienteDAO clienteDao = chosenFactory.getClienteDAO();

        System.out.println("PUNTO 3- Busca producto con mayor recaudacion:");
        ProductoDTO productoMayorRec= productoDao.findProductoDTO();
        System.out.println(productoMayorRec);

        System.out.println("______________________________________________________________");
System.out.println("______________________________________________________________");
System.out.println("PUNTO 4-Ranking de Clientes segun su facturacion:");

//        List<Direccion> listadoDirecciones = direccion.selectList();
//        System.out.println(listadoDirecciones);
        List<ClienteDTO> ListadoClientes= clienteDao.listarClientesPorFacturacion();
        for (ClienteDTO dir : ListadoClientes) {
            System.out.println(dir);
        }


    }


}