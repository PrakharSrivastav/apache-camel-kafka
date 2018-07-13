package beans;

import model.invoice.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InvoiceBean {
    private static final Logger logger = LoggerFactory.getLogger(InvoiceBean.class);
    public static Invoice newInvoice() { return Invoice.newInvoice(); }
}
