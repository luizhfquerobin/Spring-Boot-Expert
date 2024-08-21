package io.github.luizhfquerobin.rest.controller;

import io.github.luizhfquerobin.domain.entity.Pedido;
import io.github.luizhfquerobin.rest.dto.PedidoDTO;
import io.github.luizhfquerobin.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    public void testarDevTools() {}
}
