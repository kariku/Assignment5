package assignment5.rest;

import assignment5.test.RestHttpServer;
import org.junit.jupiter.api.*;

import static assignment5.TestBase.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for RestPriceService using RestHttpServer as server.
 */
class RestPriceServiceTest {

    private static final String protocol = "http";
    private static final String host = "localhost";
    private static final String port = String.valueOf(RestHttpServer.HTTP_PORT);

    private static final RestHttpServer server = new RestHttpServer();
    // SUT
    private static final RestPriceService client = new RestPriceService(protocol, host, port);

    @BeforeAll
    static void setupServer() throws Exception {
        server.start();
    }

    @BeforeEach
    void clearPrices() {
        server.getPricer().clearPrices();
    }

    @Test
    void whenNoPrices_thenFail() {
        Assertions.assertThrows(IllegalStateException.class, () ->
            client.getPrice(BTC, EUR)
        );
    }

    @Test
    void whenNoCurrencyFrom_thenFail() {
        for (String currencyFrom : new String[] {ETH, XRP}) {
            for (String currencyTo : new String[] {EUR, USD, CHF}) {
                givenPrice(currencyFrom, currencyTo, PRICE);
            }
        }

        Assertions.assertThrows(IllegalStateException.class, () ->
                client.getPrice(BTC, EUR)
        );
    }

    @Test
    void whenNoCurrencyTo_thenFail() {
        for (String currencyFrom : new String[] {BTC, ETH, XRP}) {
            for (String currencyTo : new String[] {USD, CHF}) {
                givenPrice(currencyFrom, currencyTo, PRICE);
            }
        }

        Assertions.assertThrows(IllegalStateException.class, () ->
                client.getPrice(BTC, EUR)
        );
    }


    @Test
    void whenPriceExist_thenExpectCorrectPrice() {
        givenPrice(BTC, EUR, PRICE);

        PriceInfo priceInfo = client.getPrice(BTC, EUR);

        assertEquals(PRICE, priceInfo.getPrice(), DELTA);
    }

    @Test
    void whenMultiplePricesExist_thenExpectCorrectPrice() {
        int i = 1;
        for (String currencyFrom : new String[] {BTC, ETH, XRP}) {
            for (String currencyTo : new String[] {EUR, USD, CHF}) {
                givenPrice(currencyFrom, currencyTo, i * PRICE);
                i++;
            }
        }


        i = 1;
        for (String currencyFrom : new String[] {BTC, ETH, XRP}) {
            for (String currencyTo : new String[]{EUR, USD, CHF}) {
                PriceInfo priceInfo = client.getPrice(currencyFrom, currencyTo);
                assertEquals(PRICE * i, priceInfo.getPrice(), DELTA);
                i++;
            }
        }
    }

    private void givenPrice(String currencyFrom, String currencyTo, double price) {
        server.getPricer().setPrice(currencyFrom, currencyTo, price);
    }
}