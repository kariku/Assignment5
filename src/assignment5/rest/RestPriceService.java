package assignment5.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * REST service implementation of PriceService.
 * Configured with http or https host and port.
 * All checked exceptions are caught and re-thrown as IllegalStateException.
 *
 * Requests are sent as GET to the URL
 *  <protocol>://<host>:<port>/data/price?fsym=<currencyFrom>&tsyms=<currencyTo>
 *
 * The expected response from server is a string representation of a single double value, which is the price.
 * In case of error, it is expected that the server will return a HTTP error status code, e.g. 500.
 */
public class RestPriceService implements PriceService {
    private final String protocol;
    private final String serverHost;
    private final String serverPort;

    public RestPriceService(String protocol, String serverHost, String serverPort) {
        this.protocol = protocol;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    @Override
    public PriceInfo getPrice(String currencyFrom, String currencyTo) {
        URL url = buildUrl(currencyFrom, currencyTo);
        HttpURLConnection connection = setupConnection(url);
        try {
            final int httpResponse = connection.getResponseCode();
            if (httpResponse == 200) {
                double price = getResponse(connection);
                return new PriceInfo(currencyFrom, currencyTo, price);
            } else {
                throw new IllegalStateException("Unexpected server response " + httpResponse);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to get price from server", e);
        } finally {
            connection.disconnect();
        }
    }

    private URL buildUrl(String currencyFrom, String currencyTo) {
        String tail = String.format("/data/price?fsym=%s&tsyms=%s", currencyFrom, currencyTo);
        String head = String.format("%s://%s:%s", protocol, serverHost, serverPort);
        String urlString = head + tail;
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Bad url string: " + urlString, e);
        }
    }

    private HttpURLConnection setupConnection(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            return connection;
        } catch (Exception e) {
            throw new IllegalStateException("Connection failed", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private double getResponse(HttpURLConnection connection) {
        try {
            StringBuilder rawResponse = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String line;
            while ((line = reader.readLine()) != null) {
                rawResponse.append(line);
            }

            return decodeResponse(rawResponse.toString());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read from server", e);
        }
    }

    private double decodeResponse(String rawResponse) {
        try {
            return Double.parseDouble(rawResponse);
        } catch (Exception e) {
            throw new IllegalStateException("Bad response from server: " + rawResponse);
        }
    }
}
