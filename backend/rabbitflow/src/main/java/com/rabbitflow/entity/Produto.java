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


        public Produto(Long id, Integer estoque, Double preco, String nome) {
                this.id = id;
                this.estoque = estoque;
                this.preco = preco;
                this.nome = nome;
        }

        public Produto() {
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public Double getPreco() {
                return preco;
        }

        public void setPreco(Double preco) {
                this.preco = preco;
        }

        public Integer getEstoque() {
                return estoque;
        }

        public void setEstoque(Integer estoque) {
                this.estoque = estoque;
        }
}
