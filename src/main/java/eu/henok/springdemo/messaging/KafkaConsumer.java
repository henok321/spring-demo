package eu.henok.springdemo.messaging;

import eu.henok.springdemo.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@KafkaListener(topics = "demo-topic")
@Component
public class KafkaConsumer {

  private final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

  @KafkaHandler
  public void handleMessage(@Payload final Message message) {
    LOGGER.info(message.toString());
  }
}
