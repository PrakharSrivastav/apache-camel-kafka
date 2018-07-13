package routes.customer;

import model.notification.NotificationDeserializer;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.kafka.KafkaEndpointBuilder;

public class CustomerNotificationProcessor extends RouteBuilder {
    final static private Logger logger = LoggerFactory.getLogger(CustomerNotificationProcessor.class);

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .log(LoggingLevel.ERROR, logger, "${exception.message}")
                .throwException(new Exception(exceptionMessage().toString()));


        from(this.kafkaEndpoint("NOTIFICATION", null, NotificationDeserializer.class.getCanonicalName()))
                .log(LoggingLevel.INFO, logger, "Customer Notification is :: ${body}");
    }


    private String kafkaEndpoint(final String topic, final String serilaizerClass, final String deserializerClass) {
        final KafkaEndpointBuilder endpoint = new KafkaEndpointBuilder();
        endpoint.setBroker("localhost:9092");
        endpoint.setClientId("clientId");
        endpoint.setGroupId("groupId");
        endpoint.setTopic(topic);
        if (serilaizerClass != null)
            endpoint.setSerializerClass(serilaizerClass);

        if (deserializerClass != null)
            endpoint.setValueDeserializer(deserializerClass);
        endpoint.setAutoCommitEnable("false");

        return endpoint.getEndpointUri();
    }
}
