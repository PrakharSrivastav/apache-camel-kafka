package routes.customer;

import beans.CustomerBean;
import model.customer.CustomerSerializer;
import model.invoice.InvoiceDeserializer;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.kafka.KafkaEndpointBuilder;

public final class CustomerInvoicePayment extends RouteBuilder {
    final static Logger logger = LoggerFactory.getLogger(CustomerInvoicePayment.class);

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .routeId("CustomerInvoiceExceptionLogger")
                .log(LoggingLevel.ERROR, logger, exceptionMessage().toString())
                .throwException(new Exception(exceptionMessage().toString()));

        from(this.kafkaEndpoint("INVOICE_GENERATED", null, InvoiceDeserializer.class.getCanonicalName()))
                .routeId("ProcessCustomerInvoice")
                .bean(new CustomerBean())
                .log(LoggingLevel.INFO, logger, "Body is is :: ${body}")
                .to(this.kafkaEndpoint("PAY_INVOICE", CustomerSerializer.class.getCanonicalName(), null));
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
