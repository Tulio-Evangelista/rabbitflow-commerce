package com.rabbitflow.repository;

import com.rabbitflow.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido , Long> {

    public Optional<ItemPedido> findById(Long id);
}
