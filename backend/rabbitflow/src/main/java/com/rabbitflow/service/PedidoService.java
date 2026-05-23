package com.rabbitflow.service;


import com.rabbitflow.DTO.PedidoDTO;
import com.rabbitflow.DTO.request.ItemPedidoRequestDTO;
import com.rabbitflow.DTO.request.PedidoRequestDTO;
import com.rabbitflow.DTO.response.ItemPedidoResponseDTO;
import com.rabbitflow.DTO.response.PedidoResponseDTO;
import com.rabbitflow.entity.ItemPedido;
import com.rabbitflow.entity.Pedido;
import com.rabbitflow.entity.Produto;
import com.rabbitflow.enums.StatusPedido;
import com.rabbitflow.producer.PedidoProducer;
import com.rabbitflow.repository.PedidoRepository;
import com.rabbitflow.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public PedidoResponseDTO criarPedido(PedidoRequestDTO requestDTO) {

        Pedido pedido = new Pedido();

        pedido.setStatus(StatusPedido.CRIADO);

        List<ItemPedido> itens = new ArrayList<>();

        double total = 0.0;

        for (ItemPedidoRequestDTO itemDTO : requestDTO.itens()) {

            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));


            if (produto.getEstoque() < itemDTO.quantidade()) {
                throw new RuntimeException(
                        "Estoque insuficiente para o produto: " + produto.getNome()
                );
            }

            if (itemDTO.quantidade() <= 0) {
                throw new RuntimeException(
                        "Quantidade deve ser maior que zero"
                );
            }

            ItemPedido item = new ItemPedido();

            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.setPedido(pedido);

            itens.add(item);

            total += produto.getPreco() * itemDTO.quantidade();
        }

        pedido.setItens(itens);
        pedido.setValorTotal(total);

        Pedido salvo = pedidoRepository.save(pedido);

        pedidoProducer.enviarPedido(
                new PedidoDTO(
                        salvo.getId(),
                        salvo.getValorTotal()
                )
        );

        return new PedidoResponseDTO(
                salvo.getId(),
                salvo.getValorTotal(),
                salvo.getStatus(),
                salvo.getItens()
                        .stream()
                        .map(item -> new ItemPedidoResponseDTO(
                                item.getProduto().getNome(),
                                item.getQuantidade(),
                                item.getPrecoUnitario()
                        ))
                        .toList()

        );
    }

}
