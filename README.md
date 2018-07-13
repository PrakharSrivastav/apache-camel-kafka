# apache-camel-kafka

Testing kafka integrations via Apache Camel framework. Kafka infrastructure is setup using docker. You must run kafka through its binaries (Kafka+ZooKeeper) or have docker and docker-compose installed. docker-compose with this code creates a minimal working configuration.


# Process flow

- `InvoiceGenerator` is a timer component. It generates and invoice every second to "INVOICE_GENERATED" topic
- `CustomerInvoicePayment` reads from "INVOICE_GENERATED" topic, transforms the message and sends it to "PAY_INVOICE" topic
- `BankProcessPayment` reads from "PAY_INVOICE" and sends notification for customer and invoice service on the topic "NOTIFICATION"
- `CustomerNotificationProcessor` and `InvoiceNotificationProcessor` read from "NOTIFICATION" topic and process the notification


# Routes 
All routes are defined under routes package

# Model

- All the data structures are under model package.
- All the Kafka consumers and producers require custom serializer and deserializer. The model includes the deserializer and serilaizer classes.

utils.kafka contains a simple builder class for setting KafkaEndpointUri.
