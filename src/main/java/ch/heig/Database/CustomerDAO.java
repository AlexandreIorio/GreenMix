package ch.heig.Database;

import ch.heig.Customer.Customer;
import ch.heig.Garden.Plant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CustomerDAO {
    private static Connection connection;
    static {
        try {
            connection = Database.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Logger log = LoggerFactory.getLogger(CustomerDAO.class);

    public static Map<String, Customer> getCustomers() {
        log.info("Getting customers from database");
        String query = "SELECT * FROM customers ORDER BY id";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            Map<String, Customer> customers = new HashMap<>();
            while (rs.next()) {
                Customer customer = new Customer(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("hashcode"),
                        rs.getDouble("wallet"),
                        rs.getInt("plant_space"),
                        rs.getString("email"));
                customer.setGarden(PlantDAO.getGrowingPlants(customer.getId()));
                customer.setHarvestedPlants(PlantDAO.getHavrestedPlants(customer.getId()));
                customers.put(rs.getString("username"), customer);
            }
            log.info("Found {} customers", customers.size());
            return customers;
        } catch (SQLException e) {
            log.error("Error while getting customers");
            e.printStackTrace();
        }
        return null;
    }

    public static Customer createCustomer(Customer customer) {
        String query = "INSERT INTO customers (username, hashcode, wallet) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
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
        String query = "UPDATE customers SET username = ?, wallet = ?, hashcode = ? , email = ? , plant_space = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, customer.getUsername());
            pstmt.setDouble(2, customer.getWallet());
            pstmt.setString(3, customer.getHashcode());
            pstmt.setString(4, customer.getEmail());
            pstmt.setInt(5, customer.getPlantSpace());
            pstmt.setInt(6, customer.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteCustomer(Customer customer) {

    }
}
