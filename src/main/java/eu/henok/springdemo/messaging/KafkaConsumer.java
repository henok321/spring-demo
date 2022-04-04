package eu.henok.springdemo.messaging;

import eu.henok.springdemo.event.MessageCreatedOrUpdated;
import eu.henok.springdemo.event.MessageDeleted;
import eu.henok.springdemo.repository.Message;
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

    final Optional<Message> maybeMessage = messageRepository.findById(message.id());

    if (maybeMessage.isEmpty()) {
      final Message newMessage = new Message();
      newMessage.setContent(message.content());
      messageRepository.save(newMessage);
    } else {
      final Message existingMessage = maybeMessage.get();
      existingMessage.setContent(message.content());
      messageRepository.save(existingMessage);
    }
  }

  @KafkaHandler
  public void handleMessageDelete(@Payload final MessageDeleted messageDeleted) {
    LOGGER.info("handle delete for message {}", messageDeleted.toString());
    messageRepository.deleteById(messageDeleted.id());
  }

  @KafkaHandler
  public void handleDefault(@Payload final Object event) {
    LOGGER.warn("Cannot handle unknown event {}", event);
  }
}
