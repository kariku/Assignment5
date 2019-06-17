package assignment5.main;

import assignment5.Portfolio;
import assignment5.PortfolioCalculator;
import assignment5.PortfolioReader;
import assignment5.PortfolioRenderer;
import assignment5.rest.PriceService;
import assignment5.rest.RestPriceService;


/**
 * Main client application.
 *
 * Before running it, you need to start a REST price service. You can do it by starting ServerApp first,
 * which listens to http://localhost:2544
 *
 * Then, start this application like so:
 *      PortfolioApp http localhost 2544 <portfolio-file-path> EUR
 * where portfolio-file-path is the path to the portfolio file.
 *
 * Assuming the portfolio file contains the data in the assignment description, i.e.:
 *
 * BTC=10
 * ETH=5
 * XRP=2000
 *
 * then the expected result from this application is:
 *
 * BTC: 50.000000 EUR
 * ETH: 50000.000000 EUR
 * XRP: 75.000000 EUR
 *
 * Total value: 50125.000000 EUR
 */
public class PortfolioApp {
    private final PortfolioReader portfolioReader = new PortfolioReader();
    private final PortfolioRenderer portfolioRenderer = new PortfolioRenderer();
    private final PriceService priceService;
    private final PortfolioCalculator portfolioCalculator;

    public PortfolioApp(String protocol, String host, String port) {
        this.priceService = new RestPriceService(protocol, host, port);
        this.portfolioCalculator = new PortfolioCalculator(priceService);
    }

    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: PortfolioApp <protocol> <host> <port> <fileName> <currencyTo>");
            System.out.println("    protocol: REST price service protocol, http or https");
            System.out.println("    host: REST price service host name or address");
            System.out.println("    port: REST price service port");
            System.out.println("    fileName: path to portfolio file");
            System.out.println("    currencyTo: currency symbol for calculation, e.g. EUR");
            System.out.println("Make sure the REST server is running on given host + port");
            System.exit(1);
        }

        String protocol = args[0];
        String host = args[1];
        String port = args[2];
        String path = args[3];
        String currencyTo = args[4];

        new PortfolioApp(protocol, host, port).run(path, currencyTo);
    }

    private void run(String filePath, String currencyTo) {
        Portfolio portfolio = portfolioReader.read(filePath);
        Portfolio calculatedPortfolio = portfolioCalculator.calculatePortofolio(portfolio, currencyTo);
        portfolioRenderer.renderPortfolio(calculatedPortfolio, System.out);
    }
}
