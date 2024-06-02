package com.cryptoalerts;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AlertWebSocketServer extends WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(AlertWebSocketServer.class);
    private final ConcurrentMap<WebSocket, String> activeConnections;

    public AlertWebSocketServer(int port) {
        super(new InetSocketAddress(port));
        activeConnections = new ConcurrentHashMap<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        logger.info("New connection: {}", conn.getRemoteSocketAddress());
        activeConnections.put(conn, conn.getRemoteSocketAddress().toString());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        if (conn != null) {
            logger.info("Closed connection: {}", conn.getRemoteSocketAddress());
            activeConnections.remove(conn);
            conn.close();
        } else {
            logger.warn("Attempting to close null connection");
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        logger.info("Received message from {}: {}", conn.getRemoteSocketAddress(), message);
        // Process the received message (if needed)
    }


    @Override
    public void onError(WebSocket conn, Exception ex) {
        if (conn != null) {
            logger.error("Error occurred for connection {}: {}", conn.getRemoteSocketAddress(), ex.getMessage(), ex);
            activeConnections.remove(conn);
            conn.close();
        } else {
            logger.warn("Error occurred for null connection: {}", ex.getMessage(), ex);
        }
    }

    @Override
    public void onStart() {
        logger.info("WebSocket server started");
    }

    public void stopServer() {
        logger.info("Stopping WebSocket server");
        try {
            stop();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Clean up resources here if needed
    }
}
