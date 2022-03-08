package eu.henok.springdemo.controller;

import eu.henok.springdemo.dto.Message;
import eu.henok.springdemo.messaging.KafkaProducer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

  private final KafkaProducer kafkaProducer;

  public MessageController(KafkaProducer kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }

  @PostMapping("message")
  @ResponseStatus(HttpStatus.CREATED)
  public void postMessage(@RequestBody final Message message) {
    kafkaProducer.publishMessage(message);
  }
}
