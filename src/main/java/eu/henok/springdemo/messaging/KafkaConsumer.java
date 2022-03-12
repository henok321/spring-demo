package eu.henok.springdemo.messaging;

import eu.henok.springdemo.dto.Message;
import eu.henok.springdemo.repository.MessageRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

  private final MessageRepository messageRepository;

  private final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

  public KafkaConsumer(final MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  @KafkaListener(topics = "message-created-or-updated")
  @KafkaHandler
  public void handleMessage(@Payload final Message message) {
    LOGGER.info(message.toString());

    final Optional<Message> maybeMessage = messageRepository.findMessageById(message.id());

    if (maybeMessage.isEmpty()) {
      messageRepository.insertMessage(message);
    } else {
      messageRepository.updateMessage(message);
    }
  }
}
