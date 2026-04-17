package com.rabbitflow.repository;

import com.rabbitflow.entity.Pedido;
import com.rabbitflow.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {



}
