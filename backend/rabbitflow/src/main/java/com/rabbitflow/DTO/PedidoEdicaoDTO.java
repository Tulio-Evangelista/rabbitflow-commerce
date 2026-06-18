package com.rabbitflow.DTO;

import java.util.List;

public record PedidoEdicaoDTO(
        List<ItemPedidoEdicaoDTO> itens
) {
}
