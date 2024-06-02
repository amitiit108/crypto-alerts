package com.cryptoalerts.repository;

import com.cryptoalerts.model.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertRepository {
    private static final Logger logger = LoggerFactory.getLogger(AlertRepository.class);

    private final Connection connection;

    public AlertRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Alert alert) throws SQLException {
        String sql = "INSERT INTO alerts (symbol, basis, ma_length, value, direction, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, alert.getSymbol());
            statement.setString(2, alert.getBasis());
            statement.setObject(3, alert.getMaLength());
            statement.setDouble(4, alert.getValue());
            statement.setString(5, alert.getDirection());
            statement.setString(6, alert.getStatus());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    alert.setId(generatedKeys.getInt(1));
                }
            }
            logger.info("Alert saved successfully with ID: {}", alert.getId());
        } catch (SQLException e) {
            logger.error("Error saving alert to the database: {}", e.getMessage());
            throw e; // Rethrow the exception to propagate it to the caller
        }
    }

    public List<Alert> findAll() {
        List<Alert> alerts = new ArrayList<>();
        String sql = "SELECT * FROM alerts";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Alert alert = new Alert(
                        resultSet.getInt("id"),
                        resultSet.getString("symbol"),
                        resultSet.getString("basis"),
                        resultSet.getInt("ma_length"),
                        resultSet.getDouble("value"),
                        resultSet.getString("direction"),
                        resultSet.getString("status")
                );
                alerts.add(alert);
            }
            logger.info("Retrieved {} alerts from the database.", alerts.size());
        } catch (SQLException e) {
            logger.error("Error fetching alerts from the database: {}", e.getMessage());
            e.printStackTrace();
        }
        return alerts;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM alerts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.info("Alert with ID {} deleted successfully.", id);
        } catch (SQLException e) {
            logger.error("Error deleting alert from the database: {}", e.getMessage());
            throw e;
        }
    }

    public void updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE alerts SET status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
            logger.info("Alert status updated successfully for ID {}: {}", id, status);
        } catch (SQLException e) {
            logger.error("Error updating alert status in the database: {}", e.getMessage());
            throw e;
        }
    }
}
