package com.cryptoalerts;

import com.cryptoalerts.api.AlertController;
import com.cryptoalerts.api.HealthCheckController;
import com.cryptoalerts.config.BinanceAPIConfig;
import com.cryptoalerts.config.DatabaseConfig;
import com.cryptoalerts.repository.AlertRepository;
import com.cryptoalerts.service.AlertService;
import com.cryptoalerts.service.NotificationService;
import com.cryptoalerts.service.PriceCheckerService;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            // Load application properties
            Properties properties = loadApplicationProperties();

            // Read database connection details from properties
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");

            DatabaseConfig databaseConfig = new DatabaseConfig(dbUrl, dbUsername, dbPassword);
            Connection connection = databaseConfig.getConnection();
            AlertRepository alertRepository = new AlertRepository(connection);
            AlertService alertService = new AlertService(alertRepository);

            // Read Binance API base URL from properties
            String binanceBaseUrl = properties.getProperty("binance.base.url");
            BinanceAPIConfig binanceAPIConfig = new BinanceAPIConfig(binanceBaseUrl);

            // Create other necessary services
            NotificationService notificationService = new NotificationService();

            // Create resource config and register AlertController
            ResourceConfig config = new ResourceConfig();
            config.register(new AlertController(alertService));
            config.register(new HealthCheckController());

            // Enable CORS by adding CORS filter to ResourceConfig
            config.register(new CorsFilter());

            // Define base URI and create server
            String portStr = properties.getProperty("server.port", "8888");
            int port = Integer.parseInt(portStr);
            String host = properties.getProperty("server.host", "localhost");
            URI baseUri = UriBuilder.fromUri("http://" + host + "/").port(port).build();
            JdkHttpServerFactory.createHttpServer(baseUri, config);

            // Start price checker service
            Set<AlertWebSocketServer> webSocketServers = new HashSet<>();
            AlertWebSocketServer socketServer = new AlertWebSocketServer(8080);
            webSocketServers.add(socketServer);
            socketServer.start();

            Timer timer = new Timer(true);
            PriceCheckerService priceCheckerService = new PriceCheckerService(alertService, binanceAPIConfig,
                    notificationService, webSocketServers);
            long checkInterval = Long.parseLong(properties.getProperty("price.check.interval", "60000"));
            timer.scheduleAtFixedRate(priceCheckerService, 0, checkInterval);

            logger.info("Server started at {}", baseUri);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred during startup", e);
        }
    }

    private static Properties loadApplicationProperties() throws IOException {
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            logger.error("Failed to load application.properties file", e);
            throw e;
        }
        return properties;
    }
}
