package com.rabbitflow.exception.handler;
import com.rabbitflow.exception.EstoqueInsuficienteException;
import com.rabbitflow.exception.ProdutoInativoException;
import com.rabbitflow.exception.ProdutoNaoEncontradoException;
import com.rabbitflow.exception.dto.ErrorResponse;
import com.rabbitflow.exception.dto.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> tratarProdutoNaoEncontradoException
            (ProdutoNaoEncontradoException ex) {

        ErrorResponse erro = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    public ResponseEntity<ErrorResponse> tratarPedidoNaoEncontradoException
            (ProdutoNaoEncontradoException ex) {

        ErrorResponse erro = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(ProdutoInativoException.class)
    public ResponseEntity<ErrorResponse> tratarProdutoInativo(
            ProdutoInativoException ex) {

        ErrorResponse erro = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );

        return ResponseEntity.badRequest()
                .body(erro);
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<ErrorResponse> tratarEstoqueInsuficiente(
            EstoqueInsuficienteException ex) {

        ErrorResponse erro = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );

        return ResponseEntity.badRequest()
                .body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> tratarExceptionGenerica(
            Exception ex) {

        ErrorResponse erro = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> tratarMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {


        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        erros.put(error.getField(), error.getDefaultMessage()));

        ValidationErrorResponse response =
                new ValidationErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        erros
                );

        return ResponseEntity.badRequest()
                .body(response);
    
    }


}
