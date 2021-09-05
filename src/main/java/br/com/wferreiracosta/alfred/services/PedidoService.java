package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Pedido;

import java.util.Optional;

public interface PedidoService {

    public Pedido save(Pedido pedido);
    public Optional<Pedido> findById(Integer id);

}
