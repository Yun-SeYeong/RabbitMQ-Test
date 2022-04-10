package com.example.rabbitmqtest;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.ClientParameters;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());

    return rabbitTemplate;
  }

  @Bean
  public CustomExchange hashExchange() {
    return new CustomExchange("x-modulus-exchange", "x-modulus-hash", true, false);
  }

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange("device-exchange");
  }

  @Bean
  MessageConverter messageConverter() {
    return new SimpleMessageConverter();
  }

  @Bean
  RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  Client rabbitClient() throws MalformedURLException, URISyntaxException {
    return new Client(
        new ClientParameters()
            .url("http://localhost:15672/api/")
            .username("admin")
            .password("admin")
    );
  }
}
