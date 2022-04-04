package eu.henok.springdemo.messaging;

import eu.henok.springdemo.event.MessageCreatedOrUpdated;
import eu.henok.springdemo.event.MessageDeleted;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

  private final KafkaTemplate<Long, MessageCreatedOrUpdated> messageKafkaTemplate;
  private final KafkaTemplate<Long, MessageDeleted> messageDeletedKafkaTemplate;

  public KafkaProducer(
      final KafkaTemplate<Long, MessageCreatedOrUpdated> messageKafkaTemplate,
      final KafkaTemplate<Long, MessageDeleted> messageDeletedKafkaTemplate) {
    this.messageKafkaTemplate = messageKafkaTemplate;
    this.messageDeletedKafkaTemplate = messageDeletedKafkaTemplate;
  }

  public void publishMessageCreatedOrUpdated(final MessageCreatedOrUpdated message) {
    this.messageKafkaTemplate.send("message-topic", message.id(), message);
  }

  public void publishMessageDeleted(final Long messageId) {
    this.messageDeletedKafkaTemplate.send(
        "message-topic", messageId, new MessageDeleted(messageId));
  }
}
