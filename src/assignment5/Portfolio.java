package assignment5;

import java.util.Collections;
import java.util.Map;

/**
 * Encapsulation of a portfolio data.
 * Contains:
 *  - a set of currencies and the associated value
 *  - currencyTo (optional)
 *  - a method to calculate the total portfolio value
 *
 *  If currencyTo is null, the values represent quantities, otherwise they have been calculated as quantity * price.
 */
public class Portfolio {
    private final String currencyTo;
    private final Map<String, Double> values;

    public Portfolio(String currencyTo, Map<String, Double> values) {
        this.currencyTo = currencyTo;
        this.values = values;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public Map<String, Double> getValues() {
        return Collections.unmodifiableMap(values);
    }

    public double getTotalValue() {
        double total = 0;

        for (double val: values.values()) {
            total += val;
        }

        return total;
    }
}
