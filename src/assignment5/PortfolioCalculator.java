package assignment5;

import assignment5.rest.PriceInfo;
import assignment5.rest.PriceService;

import java.util.HashMap;
import java.util.Map;

/**
 * Calculate the values of a portfolio in the given currency.
 * For all currencies in the portfolio, get the price from the given price service and calculate quantity * price.
 * Returns calculation as a new Portfolio, since it's immutable.
 */
public class PortfolioCalculator {
    private final PriceService priceService;

    public PortfolioCalculator(PriceService priceService) {
        this.priceService = priceService;
    }

    public Portfolio calculatePortofolio(Portfolio portfolio, String currencyTo) {
        Map<String, Double> newValues = new HashMap<>();

        for (Map.Entry<String, Double> entry : portfolio.getValues().entrySet()) {
            final String currencyFrom = entry.getKey();
            final double quantity = entry.getValue();
            PriceInfo priceInfo = priceService.getPrice(currencyFrom, currencyTo);

            newValues.put(currencyFrom, quantity * priceInfo.getPrice());
        }

        return new Portfolio(currencyTo, newValues);
    }
}
