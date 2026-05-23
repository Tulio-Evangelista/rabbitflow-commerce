package com.rabbitflow.repository;

import com.rabbitflow.entity.Pedido;
import com.rabbitflow.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("""
       SELECT p
       FROM Pedido p
       JOIN FETCH p.itens i
       JOIN FETCH i.produto
       WHERE p.id = :id
       """)
    Optional<Pedido> buscarPedidoCompleto(Long id);

}
