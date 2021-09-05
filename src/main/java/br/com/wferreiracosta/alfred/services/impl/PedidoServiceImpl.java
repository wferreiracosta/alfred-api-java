package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.models.Pedido;
import br.com.wferreiracosta.alfred.repositories.PedidoRepository;
import br.com.wferreiracosta.alfred.services.PedidoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repository;

    public PedidoServiceImpl(PedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pedido save(Pedido pedido) {
        return this.repository.save(pedido);
    }

    @Override
    public Optional<Pedido> findById(Integer id) {
        return this.repository.findById(id);
    }

}
