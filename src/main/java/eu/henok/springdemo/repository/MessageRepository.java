package eu.henok.springdemo.repository;

import eu.henok.springdemo.dto.Message;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final RowMapper<Message> messageRowMapper;

  public MessageRepository(final DataSource dataSource) {
    jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    this.messageRowMapper =
        ((rs, rowNum) -> new Message(rs.getObject("id", UUID.class), rs.getString("content")));
  }

  public boolean insertMessage(final Message message) {
    return jdbcTemplate.update(
            "insert into message (id,content) values (:id,:content)",
            Map.of("id", message.id(), "content", message.content()))
        == 1;
  }

  public boolean updateMessage(Message message) {
    return jdbcTemplate.update(
            "update message set content = :content where id =:id",
            Map.of("id", message.id(), "content", message.content()))
        == 1;
  }

  public Optional<Message> findMessageById(final UUID messageId) {
    return jdbcTemplate
        .query(
            "select id as id,content as content from message where id = :messageId",
            Map.of("messageId", messageId),
            messageRowMapper)
        .stream()
        .findFirst();
  }

  public List<Message> getAllMessages() {
    return jdbcTemplate.query("select id,content from message", messageRowMapper);
  }
}
