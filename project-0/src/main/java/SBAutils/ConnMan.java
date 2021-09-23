package SBAutils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Properties;

public class ConnMan {
    private static Connection conn;

    private ConnMan() {

    }
    public static Connection getConn() throws FileNotFoundException {
        if (conn == null) {
            Properties props = new Properties();
            FileReader connProp = new FileReader("src/main/resources/conn.properties");
        }
        return conn;
    }
}
