package com.rabbitflow.DTO.request;

import java.util.List;

public record PedidoRequestDTO(
        Long clienteId,
        List<ItemPedidoRequestDTO> itens
) {
}
