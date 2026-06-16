package com.rabbitflow.exception;

public class QuantidadeInsuficienteException extends  RuntimeException{

    public QuantidadeInsuficienteException(Long id) {
        super("quantidade insuficiente para o produto com id " + id);
    }
}
