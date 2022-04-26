package eu.henok.springdemo.messaging.amqp;

import eu.henok.springdemo.event.MessageCreatedOrUpdated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AmqpConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(AmqpConsumer.class);

  @RabbitListener(queues = "test")
  public void listen(final MessageCreatedOrUpdated in) {
    LOGGER.info(in.toString());
  }
}
