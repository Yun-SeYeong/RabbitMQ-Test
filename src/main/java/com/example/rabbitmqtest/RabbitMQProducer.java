package com.example.rabbitmqtest;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.domain.BindingInfo;
import com.rabbitmq.http.client.domain.QueueInfo;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {
  private final RabbitTemplate rabbitTemplate;
  private final RabbitAdmin rabbitAdmin;
  private final Client c;

  synchronized public void sendMessage(String exchange, RabbitMQMessage rabbitMQMessage) {
    List<BindingInfo> bindingsBySource = c.getBindingsBySource("/", "device-exchange");
    List<QueueInfo> queues = c.getQueues();

    System.out.println("queues = " + queues);

    Map<String, Integer> queueRoutingCounts = new HashMap<>();
    Map<String, String> queueRoutings = new HashMap<>();

    for (QueueInfo queueInfo : queues) {
      queueRoutingCounts.put(queueInfo.getName(), 0);
    }

    for (BindingInfo bindingInfo : bindingsBySource) {
      queueRoutingCounts.put(bindingInfo.getDestination(),
          queueRoutingCounts.get(bindingInfo.getDestination()) + 1);

      queueRoutings.put(bindingInfo.getRoutingKey(), bindingInfo.getDestination());
    }

    if (queueRoutingCounts.size() > 0 && queueRoutings.get(rabbitMQMessage.getTargetAdminIp()) == null) {
      List<Map.Entry<String, Integer>> entries = new LinkedList(queueRoutingCounts.entrySet());
      entries.sort((Entry.comparingByValue()));

      Entry<String, Integer> entry = entries.get(0);

      rabbitAdmin.declareBinding(new Binding(entry.getKey(), DestinationType.QUEUE, "device-exchange", rabbitMQMessage.getTargetAdminIp(), null));
    }

    rabbitTemplate.convertAndSend(exchange, rabbitMQMessage.getTargetAdminIp(), rabbitMQMessage);

  }
}
