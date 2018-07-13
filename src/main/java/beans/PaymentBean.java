package beans;

import model.customer.Customer;
import model.payment.Payment;

public final class PaymentBean {

    public Payment map(final Customer customer) {
        if (customer == null) return null;
        return new Payment(customer.getId(), customer.getInvoiceId(), customer.getStatus(), customer.getAmount());
    }
}
