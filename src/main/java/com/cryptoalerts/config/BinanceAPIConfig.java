package com.cryptoalerts.config;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class BinanceAPIConfig {
    private String baseUrl;

    public BinanceAPIConfig(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPrice(String symbol) throws Exception {
        String url = baseUrl + "/api/v3/ticker/price?symbol=" + symbol;
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            throw new Exception("GET request failed");
        }
    }
}
