package com.rabbitflow.controller;


import com.rabbitflow.DTO.request.PedidoRequestDTO;
import com.rabbitflow.DTO.response.PedidoResponseDTO;
import com.rabbitflow.entity.ItemPedido;
import com.rabbitflow.entity.Pedido;
import com.rabbitflow.service.PedidoService;
import jakarta.validation.Valid;
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
    @GetMapping("/{id}")
    public PedidoResponseDTO buscarPedidoPorId(@PathVariable Long id) {
        return pedidoService.buscarPedidoPorId(id);
    }

}
