package assignment5;

import java.util.HashMap;
import java.util.Map;

/**
 * Builds a portfolio.
 */
class PortfolioBuilder {
    private String currencyTo;
    private Map<String, Double> values = new HashMap<>();

    Portfolio build() {
        return new Portfolio(currencyTo, values);
    }

    PortfolioBuilder withCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
        return this;
    }

    PortfolioBuilder withValues(Map<String, Double> values) {
        this.values = values;
        return this;
    }

    PortfolioBuilder withCurrencyQuantity(String currencyFrom, double price) {
        values.put(currencyFrom, price);
        return this;
    }
}
