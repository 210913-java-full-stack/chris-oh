package SBAutils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnMan {
    private static Connection conn;

    private ConnMan() {

    }
    public static Connection getConn() throws IOException, SQLException {
        if (conn == null) {
            Properties props = new Properties();
            FileReader connProp = new FileReader("src/main/resources/conn.properties");
            props.load(connProp);

            String connStr= "jdbc:mariadb://" +
            props.getProperty("hostname") + ":" +
            props.getProperty("port") + "/" +
            props.getProperty("databaseName") + "?user=" +
            props.getProperty("user") + "&password=" +
            props.getProperty("password");

            conn = DriverManager.getConnection(connStr);
            connProp.close();
        }
        return conn;
    }
}
