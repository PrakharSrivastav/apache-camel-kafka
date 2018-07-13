package routes.invoice;

import model.notification.NotificationDeserializer;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.kafka.KafkaEndpointBuilder;

public class InvoiceNotificationProcessor extends RouteBuilder {

    private final static Logger logger = LoggerFactory.getLogger(InvoiceNotificationProcessor.class);

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .log(LoggingLevel.ERROR, logger, "${exception.message}")
                .throwException(new Exception(exceptionMessage().toString()));


        from(this.kafkaEndpoint("NOTIFICATION", null, NotificationDeserializer.class.getCanonicalName()))
                .log(LoggingLevel.INFO, logger, "INVOICE SERVICE NOTIFICATION IS :: ${body}");

    }

    private String kafkaEndpoint(final String topic, final String serilaizerClass, final String deserializerClass) {
        final KafkaEndpointBuilder endpoint = new KafkaEndpointBuilder();
        endpoint.setBroker("localhost:9092");
        endpoint.setClientId("clientId2");
        endpoint.setGroupId("groupId2");
        endpoint.setTopic(topic);
        if (serilaizerClass != null)
            endpoint.setSerializerClass(serilaizerClass);

        if (deserializerClass != null)
            endpoint.setValueDeserializer(deserializerClass);
        endpoint.setAutoCommitEnable("false");

        return endpoint.getEndpointUri();
    }
}
