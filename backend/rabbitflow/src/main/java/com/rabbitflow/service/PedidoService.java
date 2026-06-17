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
import com.rabbitflow.exception.EstoqueInsuficienteException;
import com.rabbitflow.exception.PedidoNaoEncontradoException;
import com.rabbitflow.exception.ProdutoInativoException;
import com.rabbitflow.exception.QuantidadeInsuficienteException;
import com.rabbitflow.producer.PedidoProducer;
import com.rabbitflow.repository.PedidoRepository;
import com.rabbitflow.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                    .orElseThrow(() -> new ProdutoInativoException(itemDTO.produtoId()
                    ));


            if (produto.getEstoque() < itemDTO.quantidade()) {
                throw new EstoqueInsuficienteException(itemDTO.produtoId()
                );
            }

            if (itemDTO.quantidade() <= 0) {
                throw new QuantidadeInsuficienteException(itemDTO.produtoId()
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





    public PedidoResponseDTO buscarPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getValorTotal(),
                pedido.getStatus(),
                pedido.getItens()
                        .stream()
                        .map(item -> new ItemPedidoResponseDTO(
                                item.getProduto().getNome(),
                                item.getQuantidade(),
                                item.getPrecoUnitario()
                        ))
                        .toList()
        );
    }

    public List<PedidoResponseDTO> buscarPedidoPorStatus( StatusPedido status) {
        List<Pedido> pedidos = pedidoRepository.findByStatus(status);

        return pedidos.stream()
                .map(pedido -> new PedidoResponseDTO(
                        pedido.getId(),
                        pedido.getValorTotal(),
                        pedido.getStatus(),
                        pedido.getItens()
                                .stream()
                                .map(item -> new ItemPedidoResponseDTO(
                                        item.getProduto().getNome(),
                                        item.getQuantidade(),
                                        item.getPrecoUnitario()
                                ))
                                .toList()
                ))
                .toList();





    }

}


