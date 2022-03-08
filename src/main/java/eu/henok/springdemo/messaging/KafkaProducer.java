package eu.henok.springdemo.messaging;

import eu.henok.springdemo.dto.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

  private final KafkaTemplate<String, Message> kafkaTemplate;

  public KafkaProducer(final KafkaTemplate<String, Message> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void publishMessage(final Message message) {
    this.kafkaTemplate.send("demo-topic", message.id(), message);
  }
}
