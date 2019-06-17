package assignment5.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock price source, used in tests.
 */
public class Pricer {
    private final Map<String, Map<String, Double>> prices = new HashMap<>();

    public void setPrice(String currencyFrom, String currencyTo, double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Prices must be positive");
        }

        Map<String, Double> pricesForCurrencyTo = prices.getOrDefault(currencyTo, new HashMap<>());
        pricesForCurrencyTo.put(currencyFrom, price);
        prices.put(currencyTo, pricesForCurrencyTo);
    }

    public void clearPrices() {
        prices.clear();
    }

    public double getPrice(String currencyFrom, String currencyTo) {
        Map<String, Double> pricesForCurrencyTo = prices.get(currencyTo);
        if (pricesForCurrencyTo == null) {
            throwNotFoundException(currencyFrom, currencyTo);
        }
        Double price = pricesForCurrencyTo.get(currencyFrom);
        if (price == null) {
            throwNotFoundException(currencyFrom, currencyTo);
        }
        return price;
    }

    private static void throwNotFoundException(String currencyFrom, String currencyTo) {
        throw new IllegalStateException(String.format("No price for currency pair %s/%s", currencyFrom, currencyTo));
    }
}
