package routes.invoice;

import beans.InvoiceBean;
import model.invoice.InvoiceSerializer;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.kafka.KafkaEndpointBuilder;

public class InvoiceGenerator extends RouteBuilder {
    private static Logger logger = LoggerFactory.getLogger(InvoiceGenerator.class);

    @Override
    public void configure() {


        onException(Exception.class)
                .log(LoggingLevel.ERROR, logger, exceptionMessage().toString())
                .log(LoggingLevel.ERROR, logger, "${body}");

        from("timer:time?period=1000")
                .routeId("GenerateInvoices")
                .bean(new InvoiceBean())
                .log(LoggingLevel.INFO, logger, "${body}")
                .to(this.kafkaProducerEndpoint("123", InvoiceSerializer.class.getCanonicalName()));
    }

    private String kafkaProducerEndpoint(final String invoiceId, final String serializer) {
        final KafkaEndpointBuilder kafkaEndpoint = new KafkaEndpointBuilder();
        kafkaEndpoint.setBroker("localhost:9092");
        kafkaEndpoint.setClientId("InvoiceGenClient");
        kafkaEndpoint.setGroupId("InvoiceGroupId");
        kafkaEndpoint.setKey(invoiceId);
        kafkaEndpoint.setTopic("INVOICE_GENERATED");
        kafkaEndpoint.setAutoOffsetReset("earliest");
        kafkaEndpoint.setSerializerClass(serializer);
        return kafkaEndpoint.getEndpointUri();
    }
}
