package assignment5.test;

import assignment5.rest.PriceInfo;
import assignment5.rest.PriceService;

/**
 * Mock price service, for unit tests.
 * Uses Pricer as a price source, which can be manipulated from tests.
 */
public class MockPriceService implements PriceService {
    private final Pricer pricer;

    public MockPriceService(Pricer pricer) {
        this.pricer = pricer;
    }


    @Override
    public PriceInfo getPrice(String currencyFrom, String currencyTo) {
        double price = pricer.getPrice(currencyFrom, currencyTo);
        return new PriceInfo(currencyFrom, currencyTo, price);
    }
}
