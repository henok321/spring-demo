package eu.henok.springdemo.repository;

import com.sun.istack.NotNull;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "message", schema = "demo")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "content")
  private String content;

  @NotNull
  @CreationTimestamp
  @Column(name = "created")
  private ZonedDateTime created;

  @NotNull
  @UpdateTimestamp
  @Column(name = "updated")
  private ZonedDateTime updated;

  @Id
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public ZonedDateTime getCreated() {
    return created;
  }

  public void setCreated(ZonedDateTime created) {
    this.created = created;
  }

  public ZonedDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(ZonedDateTime updated) {
    this.updated = updated;
  }
}
