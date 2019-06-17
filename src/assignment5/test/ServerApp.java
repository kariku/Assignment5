package assignment5.test;

/**
 * To be used in conjunction with PortfolioApp, which needs a running REST price service.
 * This standalone app uses the test RestHttpServer object, which listens to http://localhost:2544
 * and provides some sample prices.
 */
public class ServerApp {
    private static final RestHttpServer server = new RestHttpServer();

    public static void main(String[] args) {
        try {
            Pricer pricer = server.getPricer();

            pricer.setPrice("BTC", "EUR", 5);
            pricer.setPrice("ETH", "EUR", 15);
            pricer.setPrice("XRP", "EUR", 25);

            pricer.setPrice("BTC", "USD", 5 * 0.75);
            pricer.setPrice("ETH", "USD", 15 * 0.75);
            pricer.setPrice("XRP", "USD", 25 * 0.75);

            pricer.setPrice("BTC", "CHF", 5 * 0.77);
            pricer.setPrice("ETH", "CHF", 15 * 0.77);
            pricer.setPrice("XRP", "CHF", 25 * 0.77);

            server.start();

            System.out.println("Server started");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
