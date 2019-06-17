package assignment5.test;

import assignment5.rest.PriceInfo;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 * Simple REST endpoint used for testing.
 * Binds to localhost on the hardcoded port 2544 and hardcoded URL context /data/price.
 * Uses Pricer as a mock price source, which can be manipulated from tests.
 *
 * If a price is found for a request, then it is encoded as expected by RestPriceService and code 200 is returned.
 * Otherwise, code 500 is returned.
 */
public class RestHttpServer {
    public static final int HTTP_PORT = 2544;
    public static final String HTTP_CONTEXT = "/data/price";

    private HttpServer server;

    private static final URLDecoder urlDecoder = new URLDecoder();
    private static final ResponseEncoder responseEncoder = new ResponseEncoder();
    private static final Pricer pricer = new Pricer();


    public void start() throws Exception {
        if (server == null) {
            server = HttpServer.create(new InetSocketAddress(HTTP_PORT), 0);
            server.createContext(HTTP_CONTEXT, new MyHandler());
            server.start();
        }
    }

    public Pricer getPricer() {
        return pricer;
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            URI uri = exchange.getRequestURI();

            try {
                PriceInfo priceInfo = urlDecoder.decodeURI(uri);
                priceInfo = fillPrice(priceInfo);
                String response = responseEncoder.encodeResponse(priceInfo);
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                String response = "Internal error: " + e.getMessage();
                exchange.sendResponseHeaders(500, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

        private PriceInfo fillPrice(PriceInfo priceInfo) {
            final String currencyFrom = priceInfo.getCurrencyFrom();
            final String currencyTo = priceInfo.getCurrencyTo();
            double price = pricer.getPrice(currencyFrom, currencyTo);
            return new PriceInfo(currencyFrom, currencyTo, price);
        }
    }
}
