package assignment5;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Read a portfolio from a text file.
 * Entries are of the form
 *      <currency>=<quantity>
 */
public class PortfolioReader {
    public Portfolio read(String filePath) {
        try {
            PortfolioBuilder portfolioBuilder = new PortfolioBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    (new FileInputStream(filePath))));

            String str;
            while ((str = reader.readLine()) != null) {
                String[] parts = str.split("=");
                if (parts.length == 2) {
                    String currencyFrom = parts[0];
                    double value = Double.valueOf(parts[1]);
                    portfolioBuilder.withCurrencyQuantity(currencyFrom, value);
                }
            }

            return portfolioBuilder.build();

        } catch (Exception e) {
            throw new IllegalStateException("Failed to load portfolio", e);
        }
    }
}
