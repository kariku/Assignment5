package assignment5;

import java.io.PrintStream;
import java.util.Map;

/**
 * Render a portfolio on given PrintStream.
 *
 */
public class PortfolioRenderer {
    public void renderPortfolio(Portfolio portfolio, PrintStream outputStream) {
        double total = 0;
        String currencyTo = portfolio.getCurrencyTo();
        for (Map.Entry<String, Double> entry: portfolio.getValues().entrySet()) {
            String currencyFrom = entry.getKey();
            double value = entry.getValue();
            outputStream.println(String.format("%s: %f %s", currencyFrom, value, currencyTo));

            total += value;
        }

        outputStream.println();
        outputStream.println(String.format("Total value: %f %s", total, currencyTo));
    }
}
