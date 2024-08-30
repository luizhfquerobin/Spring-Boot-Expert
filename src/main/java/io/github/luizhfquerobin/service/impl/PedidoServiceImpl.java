package io.github.luizhfquerobin.service.impl;

import io.github.luizhfquerobin.domain.entity.Cliente;
import io.github.luizhfquerobin.domain.entity.ItemPedido;
import io.github.luizhfquerobin.domain.entity.Pedido;
import io.github.luizhfquerobin.domain.entity.Produto;
import io.github.luizhfquerobin.domain.enums.StatusPedido;
import io.github.luizhfquerobin.domain.repository.Clientes;
import io.github.luizhfquerobin.domain.repository.ItemsPedido;
import io.github.luizhfquerobin.domain.repository.Pedidos;
import io.github.luizhfquerobin.domain.repository.Produtos;
import io.github.luizhfquerobin.exception.PedidoNaoEncontradoException;
import io.github.luizhfquerobin.exception.RegraNegocioException;
import io.github.luizhfquerobin.rest.dto.ItemsPedidoDTO;
import io.github.luizhfquerobin.rest.dto.PedidoDTO;
import io.github.luizhfquerobin.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCLiente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCLiente).orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALISADO);

        List<ItemPedido> itemPedidos = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemPedidos);
        pedido.setItens(itemPedidos);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository
                .findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemsPedidoDTO> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtosRepository.findById(idProduto).orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());
    }
}
