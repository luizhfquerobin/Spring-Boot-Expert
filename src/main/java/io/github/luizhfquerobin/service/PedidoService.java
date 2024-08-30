package io.github.luizhfquerobin.service;

import io.github.luizhfquerobin.domain.entity.Pedido;
import io.github.luizhfquerobin.domain.enums.StatusPedido;
import io.github.luizhfquerobin.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
