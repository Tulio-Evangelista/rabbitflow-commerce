package com.rabbitflow.service;


import com.rabbitflow.entity.ItemPedido;
import com.rabbitflow.entity.Pedido;
import com.rabbitflow.entity.Produto;
import com.rabbitflow.repository.PedidoRepository;
import com.rabbitflow.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {


    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;


    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }


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

        return pedidoRepository.save(pedido);

    }


}
