package model.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class PaymentDeserializer implements Deserializer<Payment> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public Payment deserialize(String topic, byte[] data) {
        if (data == null) return null;
        final ObjectMapper om = new ObjectMapper();
        Payment payment = null;
        try {
            payment = om.readValue(data, Payment.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return payment;
    }

    @Override
    public void close() {}
}
