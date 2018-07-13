package routes.bank;

import beans.NotificationBean;
import beans.PaymentBean;
import model.customer.CustomerDeserializer;
import model.notification.NotificationSerializer;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.kafka.KafkaEndpointBuilder;

public final class BankProcessPayment extends RouteBuilder {
    private static Logger logger = LoggerFactory.getLogger(BankProcessPayment.class);

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .log(LoggingLevel.ERROR, logger, "${exception.message}")
                .throwException(new Exception(exceptionMessage().toString()));

        from(this.kafkaEndpoint("PAY_INVOICE", null, CustomerDeserializer.class.getCanonicalName()))
                .bean(new PaymentBean())
                .log(LoggingLevel.INFO, logger, "Payment Info is :: ${body}")
                .bean(new NotificationBean())
                .to(this.kafkaEndpoint("NOTIFICATION", NotificationSerializer.class.getCanonicalName(), null));
    }


    private String kafkaEndpoint(final String topic, final String serilaizerClass, final String deserializerClass) {
        final KafkaEndpointBuilder endpoint = new KafkaEndpointBuilder();
        endpoint.setBroker("localhost:9092");
        endpoint.setClientId("clientId");
        endpoint.setGroupId("groupId");
        endpoint.setKey("key");
        endpoint.setTopic(topic);
        if (serilaizerClass != null)
            endpoint.setSerializerClass(serilaizerClass);

        if (deserializerClass != null)
            endpoint.setValueDeserializer(deserializerClass);
        return endpoint.getEndpointUri();
    }
}
