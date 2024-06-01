package com.cryptoalerts.repository;

import com.cryptoalerts.model.Alert;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertRepository {
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
                    resultSet.getInt("ma_length"), // Assuming ma_length is an integer
                    resultSet.getDouble("value"),
                    resultSet.getString("direction"),
                    resultSet.getString("status")
                );
                alerts.add(alert);
            }
            System.out.println("Retrieved " + alerts.size() + " alerts from the database.");
        } catch (SQLException e) {
            System.err.println("Error fetching alerts from the database: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for detailed error information
        }
        return alerts;
    }
    

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM alerts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public void updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE alerts SET status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }
}
