package beans;

import model.customer.Customer;
import model.invoice.Invoice;

import java.util.UUID;

public final class CustomerBean {
    public Customer map(final Invoice invoice) {
        if (invoice == null) return null;
        return new Customer(invoice.getCustomerId(), invoice.getId(), UUID.randomUUID().toString(), "PAY", invoice.getAmount());
    }
}
