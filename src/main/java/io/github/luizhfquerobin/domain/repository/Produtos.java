package io.github.luizhfquerobin.domain.repository;

import io.github.luizhfquerobin.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto,Integer> {
}
