package beans;

import model.notification.Notification;
import model.payment.Payment;

import java.util.Random;

public final class NotificationBean {
    public Notification map(final Payment payment) {
        if (payment == null) return null;
        return new Notification(payment.getInvoiceId(), payment.getCustomerId(), new Random().nextBoolean() == true ? "PAID" : "FAILED", payment.getAmount());
    }
}
