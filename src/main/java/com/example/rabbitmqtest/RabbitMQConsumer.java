package com.example.rabbitmqtest;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

  private int customer1 = 0;
  private int customer2 = 0;
  private int customer3 = 0;

  @RabbitListener(queuesToDeclare = @Queue("collector1.queue1"))
  public void consumer1(RabbitMQMessage rabbitMQMessage) {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    customer1 += 1;
    System.out.println("customer1 = " + customer1);
    System.out.println("[consumer1] " + rabbitMQMessage);
  }

  @RabbitListener(queuesToDeclare = @Queue("collector1.queue2"))
  public void consumer2(RabbitMQMessage rabbitMQMessage) {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    customer2 += 1;
    System.out.println("customer2 = " + customer2);
    System.out.println("[consumer2] " + rabbitMQMessage);
  }

  @RabbitListener(queuesToDeclare = @Queue("collector1.queue3"))
  public void consumer3(RabbitMQMessage rabbitMQMessage) {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    customer3 += 1;
    System.out.println("customer3 = " + customer3);
    System.out.println("[consumer3] " + rabbitMQMessage);
  }
}
