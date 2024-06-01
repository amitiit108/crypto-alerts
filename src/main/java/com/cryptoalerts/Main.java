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

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Timer;

public class Main {
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

            // Define base URI and create server
            String portStr = properties.getProperty("server.port", "8888");
            int port = Integer.parseInt(portStr);
            String host = properties.getProperty("server.host", "localhost");
            URI baseUri = UriBuilder.fromUri("http://" + host + "/").port(port).build();
            JdkHttpServerFactory.createHttpServer(baseUri, config);

            // Start price checker service
            Timer timer = new Timer(true);
            PriceCheckerService priceCheckerService = new PriceCheckerService(alertService, binanceAPIConfig,
                    notificationService);
            long checkInterval = Long.parseLong(properties.getProperty("price.check.interval", "60000"));
            timer.scheduleAtFixedRate(priceCheckerService, 0, checkInterval);

            System.out.println("Server started at " + baseUri);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static Properties loadApplicationProperties() throws IOException {
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            System.err.println("Failed to load application.properties file");
            throw e;
        }
        return properties;
    }
}
