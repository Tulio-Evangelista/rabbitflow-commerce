package com.rabbitflow.entity;


import jakarta.persistence.*;

@Entity
@Table
public class Produto {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nome;
        private Double preco;
        private Integer estoque;



}
