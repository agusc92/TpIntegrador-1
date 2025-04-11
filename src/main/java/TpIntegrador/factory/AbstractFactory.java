package TpIntegrador.factory;

import TpIntegrador.dao.ClienteDAO;
import TpIntegrador.dao.FacturaDAO;
import TpIntegrador.dao.Factura_ProductoDAO;
import TpIntegrador.dao.ProductoDAO;

public abstract class AbstractFactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;
    public abstract ClienteDAO getClienteDAO();
    public abstract ProductoDAO getProductoDAO();
    public abstract Factura_ProductoDAO getFactura_ProductoDAO();
    public abstract FacturaDAO getFacturaDAO();
    public static AbstractFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC : {
                return MySQLDAOFactory.getInstance();
            }
            case DERBY_JDBC: return null;
            default: return null;
        }
    }
}
