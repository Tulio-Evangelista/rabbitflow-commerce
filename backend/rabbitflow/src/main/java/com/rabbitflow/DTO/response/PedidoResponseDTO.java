package com.rabbitflow.DTO.response;

import com.rabbitflow.enums.StatusPedido;

import java.util.List;

public record PedidoResponseDTO(
        Long id,
        Double valorTotal,
        StatusPedido status,
        List<ItemPedidoResponseDTO> itens
) {
}
