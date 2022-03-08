package eu.henok.springdemo.repository;

import eu.henok.springdemo.dto.Message;
import java.util.Map;
import javax.sql.DataSource;
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
}
