package com.rabbitflow.DTO.response;

public record ItemPedidoResponseDTO(
        String produto,
        Integer quantidade,
        Double precoUnitario
) {
}
