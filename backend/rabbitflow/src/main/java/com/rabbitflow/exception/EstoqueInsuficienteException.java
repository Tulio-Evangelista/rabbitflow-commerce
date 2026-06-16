package com.rabbitflow.exception;

public class EstoqueInsuficienteException extends  RuntimeException{

    public EstoqueInsuficienteException(Long id) {
        super("estoque insuficiente para o produto com id " + id);
    }
}
