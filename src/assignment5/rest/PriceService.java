package assignment5.rest;

/**
 * Interface of service used to query prices
 */
public interface PriceService {
    /**
     * Get the price of one unit of currencyFrom in currencyTo units
     * @param currencyFrom the currency whose price is queried
     * @param currencyTo the currency in which the price is expressed
     * @return price of one unit of currencyFrom in currencyTo units
     */
    PriceInfo getPrice(String currencyFrom, String currencyTo);
}
