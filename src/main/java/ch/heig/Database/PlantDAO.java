package ch.heig.Database;

import ch.heig.Customer.Customer;
import ch.heig.Garden.Plant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class PlantDAO {
    private static Connection connection;
    static {
        try {
            connection = Database.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static final Logger log = LoggerFactory.getLogger(PlantDAO.class);


    public static Map<Integer, Plant> getGrowingPlants() {
        log.info("Getting plants from database");
        String query = "SELECT * FROM plants ORDER BY id";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            Map<Integer, Plant> plants = new HashMap<>();
            while (rs.next()) {

            /*int id, String type, double duration_factor, double quantity_factor, LocalTime plantingTime*/
                Plant plant = new Plant(rs.getInt("id"),
                        rs.getString("type"),
                        rs.getDouble("duration_factor"),
                        rs.getDouble("quantity_factor"),
                        rs.getLong("plantingTime"));
                plants.put(rs.getInt("id"), plant);
            }
            log.info("Found {} plants", plants.size());
            return plants;
        } catch (SQLException e) {
            log.error("Error while getting customers");
            e.printStackTrace();
        }
        return null;
    }

    public static Map<Integer, Plant> getGrowingPlants(int ownerId) {
        log.info("Getting plants from database");
        String query = "SELECT * FROM plants WHERE owner = ? AND havrested_time IS NULL ORDER BY id";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, ownerId);
            ResultSet rs = pstmt.executeQuery();
            Map<Integer, Plant> plants = new HashMap<>();
            while (rs.next()) {

                /*int id, String type, double duration_factor, double quantity_factor, LocalTime plantingTime*/
                Plant plant = new Plant(rs.getInt("id"),
                        rs.getString("type"),
                        rs.getDouble("duration_factor"),
                        rs.getDouble("quantity_factor"),
                        rs.getLong("planting_time"));
                plants.put(rs.getInt("id"), plant);
            }
            log.info("Found {} plants", plants.size());
            return plants;
        } catch (SQLException e) {
            log.error("Error while getting customers");
            e.printStackTrace();
        }
        return null;
    }

    public static Map<Integer, Plant> getHavrestedPlants(int ownerId) {
        log.info("Getting plants from database");
    String query = "SELECT * FROM plants WHERE owner = ? AND havrested_time IS NOT NULL ORDER BY id";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, ownerId);
            ResultSet rs = pstmt.executeQuery();
            Map<Integer, Plant> plants = new HashMap<>();
            while (rs.next()) {
                /*int id, String type, double duration_factor, double quantity_factor, LocalTime plantingTime*/
                Plant plant = new Plant(rs.getInt("id"),
                        rs.getString("type"),
                        rs.getDouble("duration_factor"),
                        rs.getDouble("quantity_factor"),
                        rs.getLong("planting_time"));
                plants.put(rs.getInt("id"), plant);
            }
            log.info("Found {} plants", plants.size());
            return plants;
        } catch (SQLException e) {
            log.error("Error while getting customers");
            e.printStackTrace();
        }
        return null;
    }

    public static Plant createPlant(Plant plant, int owmerId) {
        String query = "INSERT INTO plants (owner, type, duration_factor, quantity_factor, planting_time) " +
                            "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, owmerId);
            pstmt.setString(2, plant.getPlantType().getName());
            pstmt.setDouble(3, plant.getDurationFactor());
            pstmt.setDouble(4, plant.getQuantityFactor());
            pstmt.setLong(5, plant.getPlantingTime());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        // Récupérer l'ID généré
                        int id = rs.getInt(1);
                        plant.setId(id);
                        return plant; // Retourner l'objet Customer mis à jour
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // ou vous pouvez choisir de lancer une exception si l'insertion échoue
    }

    /*définit que la plante est ramassée maintenant*/
    public static void havrestPlant(Customer customer, Plant plant) {
        log.info("Harvesting plant {}", plant.getId());
        String query =  "UPDATE plants SET havrested_time = ? WHERE id = ?;" +
                        "UPDATE customers SET wallet = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, (long)(System.currentTimeMillis()/1000));
            pstmt.setInt(2, plant.getId());
            pstmt.setDouble(3, customer.getWallet());
            pstmt.setInt(4, customer.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("Plant {} harvested", plant.getId());
    }
}
