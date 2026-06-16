package com.rabbitflow.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{

    public   ProdutoNaoEncontradoException(Long id) {
        super("produto não encontrado com id: " + id);
    }






}
