package com.rabbitflow.service;


import com.rabbitflow.DTO.PedidoDTO;
import com.rabbitflow.entity.ItemPedido;
import com.rabbitflow.entity.Pedido;
import com.rabbitflow.entity.Produto;
import com.rabbitflow.producer.PedidoProducer;
import com.rabbitflow.repository.PedidoRepository;
import com.rabbitflow.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {


    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoProducer pedidoProducer;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, PedidoProducer pedidoProducer) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoProducer = pedidoProducer;
    }


    @Transactional
    public Pedido criarPedido(List<ItemPedido> itens) {

        Pedido pedido = new Pedido();
        pedido.setItens(itens);

        double total = 0.0;

        for (ItemPedido item : itens) {

            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
            item.setPedido(pedido);

            total += produto.getPreco() * item.getQuantidade();
        }

        pedido.setValorTotal(total);

        Pedido salvo = pedidoRepository.save(pedido);
        pedidoProducer.enviarPedido(new PedidoDTO(salvo.getId(), salvo.getValorTotal()));

        return salvo;


    }


}
