package com.example.rabbitmqtest;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.domain.BindingInfo;
import com.rabbitmq.http.client.domain.ExchangeInfo;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitMqTestApplicationTests {

  @Autowired
  private RabbitMQProducer rabbitMQProducer;

  @Autowired
  private RabbitAdmin rabbitAdmin;

  @Autowired
  private Client c;

  @Test
  @DisplayName(value = "")
  public void sendMessageTest() throws InterruptedException {

    for(int j=1; j<=10; j++) {
      int finalJ = j;
      new Thread(() -> rabbitMQProducer.sendMessage("device-exchange", new RabbitMQMessage(
          finalJ +"."+finalJ+"."+finalJ+"."+finalJ, ""))).start();
    }

    Thread.sleep(20000);
  }

  @Test
  @DisplayName(value = "")
  public void RabbitClientTest() throws Exception {

    List<BindingInfo> bindings = c.getBindings();

    for (BindingInfo bindingInfo: bindings) {
      System.out.println("bindingInfo = " + bindingInfo);
    }

    ExchangeInfo exchange = c.getExchange("/", "device-exchange");
    System.out.println("exchange = " + exchange);

    for (BindingInfo bindingInfo : c.getBindingsBySource("/", "device-exchange")) {
      System.out.println("bindingInfo = " + bindingInfo);
    }

    QueueInformation queueInfo = rabbitAdmin.getQueueInfo("collector1.queue1");
    System.out.println("queueInfo = " + queueInfo);

  }

}
