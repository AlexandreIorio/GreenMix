package ch.heig.Database;

import ch.heig.Customer.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAO {
    private Connection connection;

    public static Map<String, Customer> getCustomers() {

        String query = "SELECT * FROM customers ORDER BY id";
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            Map<String, Customer> customers = new HashMap<>();
            while (rs.next()) {
                customers.put(rs.getString("username"), new Customer(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("hashcode"),
                        rs.getDouble("wallet")));
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Customer getCustomer(String username) {
        String query = "SELECT * FROM customers WHERE username = ?";
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getString("username"), rs.getString("hashcode"), rs.getDouble("wallet"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Customer createCustomer(Customer customer) {
        String query = "INSERT INTO customers (username, hashcode, wallet) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, customer.getUsername());
            pstmt.setString(2, customer.getHashcode());
            pstmt.setDouble(3, customer.getWallet());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        // Récupérer l'ID généré
                        int id = rs.getInt(1);
                        customer.setId(id);
                        return customer; // Retourner l'objet Customer mis à jour
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // ou vous pouvez choisir de lancer une exception si l'insertion échoue
    }

    public static void updateCustomer(Customer customer) {
        String query = "UPDATE customers SET username = ?, wallet = ?, hashcode = ? , email = ? WHERE id = ?";
        try (PreparedStatement pstmt = Database.getConnection().prepareStatement(query)) {
            pstmt.setString(1, customer.getUsername());
            pstmt.setDouble(2, customer.getWallet());

            pstmt.setString(2, customer.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteCustomer(Customer customer) {

    }
}
