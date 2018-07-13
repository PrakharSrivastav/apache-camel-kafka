import org.apache.camel.main.Main;
import org.apache.camel.main.MainListenerSupport;
import org.apache.camel.main.MainSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import routes.bank.BankProcessPayment;
import routes.customer.CustomerInvoicePayment;
import routes.customer.CustomerNotificationProcessor;
import routes.invoice.InvoiceGenerator;
import routes.invoice.InvoiceNotificationProcessor;

public final class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        final Main main = new Main();
        main.addMainListener(new Events());


        // Add routes
        main.addRouteBuilder(new InvoiceGenerator());
        main.addRouteBuilder(new CustomerInvoicePayment());
        main.addRouteBuilder(new BankProcessPayment());
        main.addRouteBuilder(new CustomerNotificationProcessor());
        main.addRouteBuilder(new InvoiceNotificationProcessor());

        try {
            main.run();
        } catch (Exception e) {
            logger.error("Error starting Camel Application ", e);
            e.printStackTrace();
        }
    }

    private static class Events extends MainListenerSupport {
        private static Logger logger = LoggerFactory.getLogger(Events.class);

        @Override
        public void afterStart(final MainSupport main) {logger.info("Camel app is now started!");}

        @Override
        public void beforeStop(final MainSupport main) {logger.info("Camel app is shutting down!");}
    }

}
