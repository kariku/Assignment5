package assignment5;

import assignment5.rest.PriceService;
import assignment5.test.MockPriceService;
import assignment5.test.Pricer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static assignment5.TestBase.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PortfolioCalculator
 */
class PortfolioCalculatorTest {

    private final Pricer pricer = new Pricer();
    private final PriceService priceService = new MockPriceService(pricer);

    // SUT
    private PortfolioCalculator portfolioCalculator = new PortfolioCalculator(priceService);

    @Test
    void calculatePortofolioEmpty() {

        Portfolio inputPortfolio = new PortfolioBuilder().build();

        Portfolio calculatedPortofolio = portfolioCalculator.calculatePortofolio(inputPortfolio, EUR);

        assertEquals(0, calculatedPortofolio.getTotalValue(), DELTA);
    }

    @Test
    void calculatePortofolioWithOneCurrency() {
        final double quantity = 50;

        pricer.setPrice(BTC, EUR, PRICE);

        Portfolio inputPortfolio = new PortfolioBuilder()   //
            .withCurrencyQuantity(BTC, quantity)            //
            .build();


        Portfolio calculatedPortofolio = portfolioCalculator.calculatePortofolio(inputPortfolio, EUR);

        assertEquals(EUR, calculatedPortofolio.getCurrencyTo());
        assertEquals(quantity * PRICE, calculatedPortofolio.getTotalValue(), DELTA);
        assertEquals(quantity * PRICE, calculatedPortofolio.getValues().get(BTC), DELTA);
    }

    @Test
    void calculatePortofolioWithMultipleCurrencies() {
        final String[] currencies = {BTC, ETH, XRP};
        final double[] quantities = {12.5, 25.0, 50.5};
        final double[] prices = {1.25, 250.0, 150.15};
        final String currencyTo = EUR;

        final int n = currencies.length;

        PortfolioBuilder portfolioBuilder = new PortfolioBuilder();
        double expectedTotal = 0;

        for (int i = 0; i < n; i++) {
            pricer.setPrice(currencies[i], currencyTo, prices[i]);
            portfolioBuilder.withCurrencyQuantity(currencies[i], quantities[i]);
            expectedTotal += prices[i] * quantities[i];
        }

        Portfolio inputPortfolio = portfolioBuilder.build();

        Portfolio calculatedPortofolio = portfolioCalculator.calculatePortofolio(inputPortfolio, currencyTo);

        assertEquals(expectedTotal, calculatedPortofolio.getTotalValue(), DELTA);
        for (int i = 0; i < n; i++) {
            assertEquals(currencyTo, calculatedPortofolio.getCurrencyTo());
            assertEquals(quantities[i] * prices[i], calculatedPortofolio.getValues().get(currencies[i]), DELTA);
        }
    }

    @Test
    void calculatePortofolioWhenNoPrices() {
        final double quantity = 50;

        pricer.setPrice(BTC, EUR, PRICE);
        // note missing price for ETH

        Portfolio inputPortfolio = new PortfolioBuilder()   //
                .withCurrencyQuantity(BTC, quantity)        //
                .withCurrencyQuantity(ETH, quantity)
                .build();

        Assertions.assertThrows(IllegalStateException.class, () ->
                portfolioCalculator.calculatePortofolio(inputPortfolio, EUR)
        );
    }

    @Test
    void calculatePortofolioWhenNoPricesInRequiredCurrency() {
        final double quantity = 50;

        pricer.setPrice(BTC, EUR, 6767);
        pricer.setPrice(BTC, USD, 16354);
        // note no price in CHF


        Portfolio inputPortfolio = new PortfolioBuilder()   //
                .withCurrencyQuantity(BTC, quantity)        //
                .build();

        Assertions.assertThrows(IllegalStateException.class, () ->
                portfolioCalculator.calculatePortofolio(inputPortfolio, CHF)
        );
    }

    @Test
    void calculatePortofolioWithMultipleTargets() {
        final double quantity = 501;

        final double priceEUR = 2544;
        final double priceUSD = 69247;

        pricer.setPrice(BTC, EUR, priceEUR);
        pricer.setPrice(BTC, USD, priceUSD);

        Portfolio inputPortfolio = new PortfolioBuilder()   //
                .withCurrencyQuantity(BTC, quantity)        //
                .build();

        Portfolio portofolioEUR = portfolioCalculator.calculatePortofolio(inputPortfolio, EUR);
        assertEquals(EUR, portofolioEUR.getCurrencyTo());
        assertEquals(quantity * priceEUR, portofolioEUR.getTotalValue(), DELTA);

        Portfolio portofolioUSD = portfolioCalculator.calculatePortofolio(inputPortfolio, USD);
        assertEquals(USD, portofolioUSD.getCurrencyTo());
        assertEquals(quantity * priceUSD, portofolioUSD.getTotalValue(), DELTA);
    }


}