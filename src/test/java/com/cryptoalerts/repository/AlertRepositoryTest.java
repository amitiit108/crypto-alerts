// package com.cryptoalerts.repository;

// import com.cryptoalerts.config.DatabaseConfig;
// import com.cryptoalerts.model.Alert;
// import com.cryptoalerts.model.AlertStatus;
// import org.junit.After;
// import org.junit.Before;
// import org.junit.Test;

// import java.sql.Connection;
// import java.sql.SQLException;
// import java.util.List;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNotNull;

// public class AlertRepositoryTest {

//     private Connection connection;
//     private AlertRepository alertRepository;

//     @Before
//     public void setUp() throws SQLException {
//         DatabaseConfig databaseConfig = new DatabaseConfig();
//         connection = databaseConfig.getConnection();
//         alertRepository = new AlertRepository(connection);
//     }

//     @After
//     public void tearDown() throws SQLException {
//         connection.close();
//     }

//     @Test
//     public void testSaveAlert() throws SQLException {
//         Alert alert = new Alert(0, null, null, null, 0, null, null);
//         alert.setSymbol("BTCUSDT");
//         alert.setBasis("price");
//         alert.setValue(40000);
//         alert.setDirection("UP");
//         alert.setStatus(AlertStatus.PENDING.name());

//         alertRepository.save(alert);
//         assertNotNull(alert.getId());
//     }

//     @Test
//     public void testFindAllAlerts() throws SQLException {
//         Alert alert = new Alert(0, null, null, null, 0, null, null);
//         alert.setSymbol("BTCUSDT");
//         alert.setBasis("price");
//         alert.setValue(40000);
//         alert.setDirection("UP");
//         alert.setStatus(AlertStatus.PENDING.name());

//         alertRepository.save(alert);
//         List<Alert> alerts = alertRepository.findAll();
//         assertNotNull(alerts);
//         assertEquals(1, alerts.size());
//     }

//     @Test
//     public void testDeleteAlert() throws SQLException {
//         Alert alert = new Alert(0, null, null, null, 0, null, null);
//         alert.setSymbol("BTCUSDT");
//         alert.setBasis("price");
//         alert.setValue(40000);
//         alert.setDirection("UP");
//         alert.setStatus(AlertStatus.PENDING.name());

//         alertRepository.save(alert);
//         alertRepository.delete(alert.getId());

//         List<Alert> alerts = alertRepository.findAll();
//         assertEquals(0, alerts.size());
//     }

//     @Test
//     public void testUpdateStatus() throws SQLException {
//         Alert alert = new Alert(0, null, null, null, 0, null, null);
//         alert.setSymbol("BTCUSDT");
//         alert.setBasis("price");
//         alert.setValue(40000);
//         alert.setDirection("UP");
//         alert.setStatus(AlertStatus.PENDING.name());

//         alertRepository.save(alert);
//         alertRepository.updateStatus(alert.getId(), AlertStatus.COMPLETED.name());

//         List<Alert> alerts = alertRepository.findAll();
//         assertEquals(AlertStatus.COMPLETED.name(), alerts.get(0).getStatus());
//     }
// }
