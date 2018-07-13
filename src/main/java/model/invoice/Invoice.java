package model.invoice;

import java.util.Random;
import java.util.UUID;

public final class Invoice {
    private String id;
    private String customerId;
    private Float amount;
    private String state;

    public Invoice() { }

    public Invoice(String id, String customerId, Float amount, String state) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.state = state;
    }

    public String getId() { return id; }

    public String getCustomerId() { return customerId; }

    public Float getAmount() { return amount; }

    public String getState() { return state; }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Invoice{")
                .append("id='")
                .append(id)
                .append('\'')
                .append(", customerId='")
                .append(customerId)
                .append('\'')
                .append(", amount=")
                .append(amount)
                .append(", state='")
                .append(state)
                .append('\'')
                .append('}').toString();
    }

    public static Invoice newInvoice() {
        return new Invoice(UUID.randomUUID().toString(), UUID.randomUUID().toString(), new Random().nextFloat() * 100, "NEW");
    }
}
