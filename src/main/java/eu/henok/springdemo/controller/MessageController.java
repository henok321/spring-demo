package eu.henok.springdemo.controller;

import eu.henok.springdemo.dto.MessageCreatedOrUpdated;
import eu.henok.springdemo.messaging.KafkaProducer;
import eu.henok.springdemo.repository.MessageRepository;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

  private final KafkaProducer kafkaProducer;
  private final MessageRepository messageRepository;

  public MessageController(
      final KafkaProducer kafkaProducer, final MessageRepository messageRepository) {
    this.kafkaProducer = kafkaProducer;
    this.messageRepository = messageRepository;
  }

  @PostMapping("message")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity createMessage(@RequestBody final MessageCreatedOrUpdated message) {
    kafkaProducer.publishMessageCreatedOrUpdated(message);
    return ResponseEntity.created(URI.create("message/%s".formatted(message.id()))).build();
  }

  @PutMapping("message/{messageId}")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity updateMessage(
      @RequestBody final MessageCreatedOrUpdated message, @PathVariable final String messageId) {
    kafkaProducer.publishMessageCreatedOrUpdated(message);
    return ResponseEntity.created(URI.create("message/%s".formatted(messageId))).build();
  }

  @GetMapping("message/{messageId}")
  public ResponseEntity<MessageCreatedOrUpdated> getMessage(@PathVariable final UUID messageId) {
    return ResponseEntity.of(messageRepository.findMessageById(messageId));
  }

  @GetMapping("message")
  public List<MessageCreatedOrUpdated> getMessage() {
    return messageRepository.getAllMessages();
  }

  @DeleteMapping("message/{messageId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteMessage(@PathVariable final UUID messageId) {
    kafkaProducer.publishMessageDeleted(messageId);
  }
}
