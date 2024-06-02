package com.cryptoalerts.config;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class BinanceAPIConfig {
    private static final String GET_METHOD = "GET";
    private static final int HTTP_OK = 200;

    private final String baseUrl;

    public BinanceAPIConfig(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPrice(String symbol) throws Exception {
        // Validate base URL
        if (!isValidUrl(baseUrl)) {
            throw new IllegalArgumentException("Invalid base URL: " + baseUrl);
        }

        String url = baseUrl + "/api/v3/ticker/price?symbol=" + symbol;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod(GET_METHOD);
        int responseCode = con.getResponseCode();

        // Handle response
        if (responseCode == HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            throw new Exception("GET request failed with response code: " + responseCode);
        }
    }

    private boolean isValidUrl(String url) {
        // Perform URL validation logic
        // For example, check if the URL is not empty and has a valid format
        return url != null && !url.isEmpty() && url.startsWith("http");
    }
}
