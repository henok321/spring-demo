package eu.henok.springdemo.messaging;

import eu.henok.springdemo.dto.MessageCreatedOrUpdated;
import eu.henok.springdemo.dto.MessageDeleted;
import eu.henok.springdemo.repository.MessageRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "message-topic")
public class KafkaConsumer {

  private final MessageRepository messageRepository;

  private final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

  public KafkaConsumer(final MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  @KafkaHandler
  public void handleMessageCreatedOrUpdated(@Payload final MessageCreatedOrUpdated message) {
    LOGGER.info("handle create or update for message {}", message.toString());

    final Optional<MessageCreatedOrUpdated> maybeMessage =
        messageRepository.findMessageById(message.id());

    if (maybeMessage.isEmpty()) {
      messageRepository.insertMessage(message);
    } else {
      messageRepository.updateMessage(message);
    }
  }

  @KafkaHandler
  public void handleMessageDelete(@Payload final MessageDeleted messageDeleted) {
    LOGGER.info("handle delete for message {}", messageDeleted.toString());
    messageRepository.deleteMessage(messageDeleted.id());
  }
}
