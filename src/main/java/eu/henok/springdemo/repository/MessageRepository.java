package eu.henok.springdemo.repository;

import eu.henok.springdemo.dto.Message;
import java.util.*;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {

  final NamedParameterJdbcTemplate jdbcTemplate;

  public MessageRepository(final DataSource dataSource) {
    jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  public boolean insertMessage(final Message message) {
    return jdbcTemplate.update(
            "insert into message (id,value) values (:id,:value)",
            Map.of("id", message.id(), "value", message.value()))
        == 1;
  }

  public Optional<Message> findMessageById(final UUID messageId) {
    return jdbcTemplate
        .query(
            "select id,value from message where id = :messageId",
            Map.of("messageId", messageId),
            new BeanPropertyRowMapper<>(Message.class))
        .stream()
        .findFirst();
  }

  public List<Message> getAllMessages() {
    return jdbcTemplate.query(
        "select id,value from message", new BeanPropertyRowMapper<>(Message.class));
  }
}
