package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Pedido;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PedidoService {

    Pedido save(Pedido pedido);
    Optional<Pedido> findById(Integer id);
    Pedido insert(Pedido obj);
    Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction);

}
