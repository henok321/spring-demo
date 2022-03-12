package eu.henok.springdemo.messaging;

import eu.henok.springdemo.dto.MessageCreatedOrUpdated;
import eu.henok.springdemo.dto.MessageDeleted;
import java.util.UUID;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

  private final KafkaTemplate<UUID, MessageCreatedOrUpdated> messageKafkaTemplate;
  private final KafkaTemplate<UUID, MessageDeleted> messageDeletedKafkaTemplate;

  public KafkaProducer(
      final KafkaTemplate<UUID, MessageCreatedOrUpdated> messageKafkaTemplate,
      final KafkaTemplate<UUID, MessageDeleted> messageDeletedKafkaTemplate) {
    this.messageKafkaTemplate = messageKafkaTemplate;
    this.messageDeletedKafkaTemplate = messageDeletedKafkaTemplate;
  }

  public void publishMessageCreatedOrUpdated(final MessageCreatedOrUpdated message) {
    this.messageKafkaTemplate.send("message-topic", message.id(), message);
  }

  public void publishMessageDeleted(final UUID messageId) {
    this.messageDeletedKafkaTemplate.send(
        "message-topic", messageId, new MessageDeleted(messageId));
  }
}
