package eu.henok.springdemo.messaging.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;

@EnableKafka
@Configuration
public class KafkaConfig {
  @Bean
  public CommonErrorHandler errorHandler() {
    return new CommonLoggingErrorHandler();
  }
}
