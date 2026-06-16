package com.rabbitflow.exception;

public class ProdutoInativoException extends RuntimeException{

    public ProdutoInativoException(Long id) {
        super("produto com id " + id + " está inativo");
    }
}
