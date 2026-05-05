package com.rabbitflow.consumer;


import com.rabbitflow.DTO.PedidoDTO;
import com.rabbitflow.entity.Pedido;
import com.rabbitflow.enums.StatusPedido;
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

import java.util.Random;

@Component
public class PedidoConsumer {


    private final PedidoRepository pedidoRepository;
    private final Random random = new Random();


    public PedidoConsumer(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @RabbitListener(queues = "pedido-queue")
    public void receberPedido(PedidoDTO pedidoDTO) {

        System.out.println("Pedido recebido: " + pedidoDTO.id());
        System.out.println("Valor total: " + pedidoDTO.valorTotal());

        Pedido pedido = pedidoRepository.findById(pedidoDTO.id())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));



        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedidoRepository.save(pedido);


        boolean pagamentoAprovado = random.nextBoolean();

        if (pagamentoAprovado) {
            pedido.setStatus(StatusPedido.PAGAMENTO_APROVADO);
            System.out.println("Pagamento aprovado para o pedido " + pedido.getId());
        } else {
            pedido.setStatus(StatusPedido.PAGAMENTO_RECUSADO);
            System.out.println("Pagamento recusado para o pedido " + pedido.getId());
        }

        pedidoRepository.save(pedido);

    }
}
