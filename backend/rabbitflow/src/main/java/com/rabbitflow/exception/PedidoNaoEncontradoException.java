package com.rabbitflow.exception;

public class PedidoNaoEncontradoException extends RuntimeException{

    public PedidoNaoEncontradoException(Long id) {
        super("pedido com id " + id + " não encontrado");
    }
}
