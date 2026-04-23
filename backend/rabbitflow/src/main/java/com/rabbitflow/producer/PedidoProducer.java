package com.rabbitflow.producer;


import com.rabbitflow.entity.Pedido;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PedidoProducer {


    private final RabbitTemplate rabbitTemplate;

    public PedidoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }



    public void enviarPedido(Pedido pedido) {
        rabbitTemplate.convertAndSend("pedido-exchange", "pedido-criado", pedido);
    }


}
