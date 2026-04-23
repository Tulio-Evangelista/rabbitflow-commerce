package com.rabbitflow.consumer;


import com.rabbitflow.entity.Pedido;
import com.rabbitflow.repository.PedidoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {




    private final PedidoRepository pedidoRepository;

    public PedidoConsumer(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }



    @RabbitListener(queues = "pedido-queue")
    public void receberPedido(@Payload Pedido  pedido) {
        System.out.println("Pedido recebido: " + pedido);

        pedidoRepository.save(pedido);
            }



}
