package eu.henok.springdemo.repository;

import eu.henok.springdemo.dto.MessageCreatedOrUpdated;
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
  private final RowMapper<MessageCreatedOrUpdated> messageRowMapper;

  public MessageRepository(final DataSource dataSource) {
    jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    this.messageRowMapper =
        ((rs, rowNum) ->
            new MessageCreatedOrUpdated(rs.getObject("id", UUID.class), rs.getString("content")));
  }

  public boolean insertMessage(final MessageCreatedOrUpdated messageCreatedOrUpdated) {
    return jdbcTemplate.update(
            "insert into message (id,content) values (:id,:content)",
            Map.of(
                "id", messageCreatedOrUpdated.id(), "content", messageCreatedOrUpdated.content()))
        == 1;
  }

  public boolean updateMessage(MessageCreatedOrUpdated messageCreatedOrUpdated) {
    return jdbcTemplate.update(
            "update message set content = :content where id =:id",
            Map.of(
                "id", messageCreatedOrUpdated.id(), "content", messageCreatedOrUpdated.content()))
        == 1;
  }

  public Optional<MessageCreatedOrUpdated> findMessageById(final UUID messageId) {
    return jdbcTemplate
        .query(
            "select id as id,content as content from message where id = :messageId",
            Map.of("messageId", messageId),
            messageRowMapper)
        .stream()
        .findFirst();
  }

  public List<MessageCreatedOrUpdated> getAllMessages() {
    return jdbcTemplate.query("select id,content from message", messageRowMapper);
  }

  public boolean deleteMessage(final UUID messageId) {
    return jdbcTemplate.update(
            "delete from message where id = :messageId", Map.of("messageId", messageId))
        == 1;
  }
}
