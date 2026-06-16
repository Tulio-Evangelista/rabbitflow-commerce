package com.rabbitflow.DTO.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PedidoRequestDTO(


        Long clienteId,

        @NotEmpty(message = "A lista de itens não pode estar vazia")
        List<ItemPedidoRequestDTO> itens
) {
}
