package com.rabbitflow.consumer;


import com.rabbitflow.DTO.PedidoDTO;
import com.rabbitflow.entity.Pedido;
import com.rabbitflow.repository.PedidoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    @RabbitListener(queues = "pedido-queue")
    public void receberPedido(PedidoDTO pedidoDTO) {

        System.out.println("Pedido recebido: " + pedidoDTO.id());
        System.out.println("Valor total: " + pedidoDTO.valorTotal());
    }
}
