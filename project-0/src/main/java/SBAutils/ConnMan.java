package SBAutils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class ConnMan {
    private static Connection conn;

    private ConnMan() {

    }
    public static Connection getConn() throws IOException {
        if (conn == null) {
            Properties props = new Properties();
            FileReader connProp = new FileReader("src/main/resources/conn.properties");
            props.load(connProp);

            // "jdbc:mariadb://hostname:port/databaseName?user=username&password=password"

            String connStr= "jdbc.mariadb://" +
            props.getProperty("hostname") + ":" +
            props.getProperty("port") + "/" +
            props.getProperty("databaseName") + "?user=" +
            props.getProperty("user") + "&password=" +
            props.getProperty("password");
        }
        return conn;
    }
}
