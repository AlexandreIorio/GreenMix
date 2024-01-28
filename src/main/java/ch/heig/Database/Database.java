package ch.heig.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/greenmix?currentSchema=greenmix";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }
}
