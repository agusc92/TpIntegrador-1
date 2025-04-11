package TpIntegrador;

import TpIntegrador.utilities.HelperMySQL;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {

        HelperMySQL helper = new HelperMySQL();
        helper.createTables();
        helper.populateDB();
    }


}