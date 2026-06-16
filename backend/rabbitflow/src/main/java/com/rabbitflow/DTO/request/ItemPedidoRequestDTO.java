package com.rabbitflow.DTO.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequestDTO(


        @NotNull(message = "O campo produtoId é obrigatório")
        Long produtoId,

        @NotNull(message = "O campo quantidade é obrigatório")
        @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
        Integer quantidade
) {
}
