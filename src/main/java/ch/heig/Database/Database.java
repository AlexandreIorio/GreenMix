package ch.heig.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final Logger log = LoggerFactory.getLogger(Database.class);
    public static Connection getConnection() throws SQLException {
        log.info("Connecting to database...");
        String url = "jdbc:postgresql://greenmix_database:5432/greenmix?currentSchema=greenmix";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }
}
