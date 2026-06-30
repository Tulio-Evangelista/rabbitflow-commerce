package com.rabbitflow.controller;


import com.rabbitflow.DTO.PedidoEdicaoDTO;
import com.rabbitflow.DTO.request.PedidoRequestDTO;
import com.rabbitflow.DTO.response.PedidoResponseDTO;
import com.rabbitflow.entity.ItemPedido;
import com.rabbitflow.entity.Pedido;
import com.rabbitflow.enums.StatusPedido;
import com.rabbitflow.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {


    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }


    @PostMapping
    public PedidoResponseDTO criarPedido(@RequestBody @Valid PedidoRequestDTO pedidoRequestDTO) {
        return pedidoService.criarPedido(pedidoRequestDTO);
    }

    @GetMapping("/listar")
    public Page<PedidoResponseDTO> buscarTodosPedidos(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return pedidoService.buscarTodosPedidos(pageable);
    }


    @GetMapping("/{id}")
    public PedidoResponseDTO buscarPedidoPorId(@PathVariable Long id) {
        return pedidoService.buscarPedidoPorId(id);
    }

    @GetMapping("/status/{status}")
    public Page<PedidoResponseDTO> buscarPedidoPorStatus(@PathVariable  StatusPedido status, Pageable pageable) {
        return pedidoService.buscarPedidoPorStatus(status, pageable);
    }


    @PutMapping("/{id}/editar")
    public PedidoResponseDTO editarPedido(@PathVariable Long id, @RequestBody PedidoEdicaoDTO pedidoEdicaoDTO) {
        return pedidoService.editarPedidoPorId(id, pedidoEdicaoDTO);
    }
    @DeleteMapping("/{id}/cancelar")
    public PedidoResponseDTO cancelarPedido(@PathVariable Long id) {
        return pedidoService.cancelarPedido(id);
    }

    @DeleteMapping("/{id}/itens/{itemId}")
    public PedidoResponseDTO deleteItemDoPedido(@PathVariable Long id, @PathVariable Long itemId) {
        return pedidoService.deleteItemDoPedido(id, itemId);
    }

    @PostMapping("/{id}/itens/{itemID}")
    public PedidoResponseDTO adicionarItemAoPedido(@PathVariable Long id, @PathVariable Long itemID ) {
        return pedidoService.adicionarItemAoPedido(id, itemID);
    }



}
