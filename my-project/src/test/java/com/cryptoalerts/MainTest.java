// package com.cryptoalerts;

// import com.cryptoalerts.api.AlertController;
// import com.cryptoalerts.config.BinanceAPIConfig;
// import com.cryptoalerts.config.DatabaseConfig;
// import com.cryptoalerts.model.Alert;
// import com.cryptoalerts.model.AlertStatus;
// import com.cryptoalerts.repository.AlertRepository;
// import com.cryptoalerts.service.AlertService;
// import com.cryptoalerts.service.NotificationService;
// import com.cryptoalerts.service.PriceCheckerService;
// import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
// import org.glassfish.jersey.server.ResourceConfig;
// import org.junit.AfterClass;
// import org.junit.BeforeClass;
// import org.junit.Test;

// import javax.ws.rs.client.Client;
// import javax.ws.rs.client.ClientBuilder;
// import javax.ws.rs.client.Entity;
// import javax.ws.rs.core.Response;
// import java.net.URI;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
// import java.util.List;
// import java.util.Timer;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNotNull;

// public class MainTest {

//     private static URI baseUri;
//     private static Client client;
//     private static Connection connection;

//     @BeforeClass
//     public static void setUp() throws SQLException {
//         DatabaseConfig databaseConfig = new DatabaseConfig();
//         connection = databaseConfig.getConnection();
//         AlertRepository alertRepository = new AlertRepository(connection);
//         AlertService alertService = new AlertService(alertRepository);
//         BinanceAPIConfig binanceAPIConfig = new BinanceAPIConfig();
//         NotificationService notificationService = new NotificationService();

//         ResourceConfig config = new ResourceConfig();
//         config.register(new AlertController(alertService));
//         baseUri = URI.create("http://localhost:8080/");
//         JdkHttpServerFactory.createHttpServer(baseUri, config);

//         Timer timer = new Timer(true);
//         PriceCheckerService priceCheckerService = new PriceCheckerService(alertService, binanceAPIConfig, notificationService);
//         timer.scheduleAtFixedRate(priceCheckerService, 0, 60000); // Check every minute

//         client = ClientBuilder.newClient();
//     }

//     @AfterClass
//     public static void tearDown() throws SQLException {
//         connection.close();
//     }

//     @Test
//     public void testCreateAlert() {
//         Alert alert = new Alert(0, null, null, null, 0, null, null);
//         alert.setSymbol("BTCUSDT");
//         alert.setBasis("price");
//         alert.setValue(40000);
//         alert.setDirection("UP");

//         Response response = client.target(baseUri).path("alerts")
//                 .request().post(Entity.json(alert));

//         assertEquals(201, response.getStatus());
//         Alert createdAlert = response.readEntity(Alert.class);
//         assertNotNull(createdAlert.getId());
//     }

//     @Test
//     public void testGetAllAlerts() {
//         Response response = client.target(baseUri).path("alerts")
//                 .request().get();

//         assertEquals(200, response.getStatus());
//         List<Alert> alerts = response.readEntity(List.class);
//         assertNotNull(alerts);
//     }

//     @Test
//     public void testRemoveAlert() {
//         Response response = client.target(baseUri).path("alerts/1")
//                 .request().delete();

//         assertEquals(204, response.getStatus());
//     }
// }
