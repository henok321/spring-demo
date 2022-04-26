package eu.henok.springdemo.messaging.amqp;

import eu.henok.springdemo.event.MessageCreatedOrUpdated;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AmqpProducer {

  private final RabbitTemplate rabbitTemplate;

  public AmqpProducer(final RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(final MessageCreatedOrUpdated out) {
    rabbitTemplate.convertAndSend("test", out);
  }
}
