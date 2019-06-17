package assignment5.rest;

/**
 * Data object holding the price of one unit of currencyFrom in currencyTo units.
 */
public class PriceInfo {
    private final String currencyFrom;
    private final String currencyTo;
    private final double price;

    public PriceInfo(String currencyFrom, String currencyTo, double price) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.price = price;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }
    public String getCurrencyTo() {
        return currencyTo;
    }
    public double getPrice() {
        return price;
    }
}
