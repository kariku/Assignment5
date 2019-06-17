package assignment5;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static assignment5.TestBase.BTC;
import static assignment5.TestBase.EUR;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for PortfolioRenderer
 */
class PortfolioRendererTest {

    // SUT
    private final PortfolioRenderer renderer = new PortfolioRenderer();

    @Test
    void renderPortfolioWithOneCurrency() throws Exception {
        Portfolio portfolio = new PortfolioBuilder()    //
            .withCurrencyTo(EUR)                        //
            .withCurrencyQuantity(BTC, 50)        //
            .build();

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(baos, true, "UTF-8");

        renderer.renderPortfolio(portfolio, stream);

        String data = new String(baos.toByteArray(), StandardCharsets.UTF_8);

        assertEquals("BTC: 50.000000 EUR\n" +
                              "\n" +
                              "Total value: 50.000000 EUR\n", data);
    }
}