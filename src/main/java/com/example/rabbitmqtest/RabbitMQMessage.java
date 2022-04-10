package com.example.rabbitmqtest;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMQMessage implements Serializable {

  String targetAdminIp;
  String message;
}
