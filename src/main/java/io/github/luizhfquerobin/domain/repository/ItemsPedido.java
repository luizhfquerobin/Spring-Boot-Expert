package io.github.luizhfquerobin.domain.repository;

import io.github.luizhfquerobin.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository<ItemPedido, Integer> {
}
