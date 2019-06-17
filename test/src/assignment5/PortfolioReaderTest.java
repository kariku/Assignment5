package assignment5;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static assignment5.TestBase.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PortfolioReader
 */
class PortfolioReaderTest {

    // SUT
    private final PortfolioReader reader = new PortfolioReader();

    @Test
    void readPortfolio1() {
        final String filePath = ClassLoader.getSystemClassLoader().getResource("bobs_crypto.txt").getPath();
        Portfolio portfolio = reader.read(filePath);

        Map<String, Double> values = portfolio.getValues();

        assertEquals(10, values.get(BTC));
        assertEquals(5, values.get(ETH));
        assertEquals(2000, values.get(XRP));
    }
}