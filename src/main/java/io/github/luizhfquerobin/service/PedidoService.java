package io.github.luizhfquerobin.service;

import io.github.luizhfquerobin.domain.entity.Pedido;
import io.github.luizhfquerobin.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
}
