package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.models.ItemPedido;
import br.com.wferreiracosta.alfred.models.PagamentoComBoleto;
import br.com.wferreiracosta.alfred.models.Pedido;
import br.com.wferreiracosta.alfred.models.enums.EstadoPagamento;
import br.com.wferreiracosta.alfred.repositories.ClienteRepository;
import br.com.wferreiracosta.alfred.repositories.ItemPedidoRepository;
import br.com.wferreiracosta.alfred.repositories.PagamentoRepository;
import br.com.wferreiracosta.alfred.repositories.PedidoRepository;
import br.com.wferreiracosta.alfred.services.BoletoService;
import br.com.wferreiracosta.alfred.services.EmailService;
import br.com.wferreiracosta.alfred.services.PedidoService;
import br.com.wferreiracosta.alfred.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private BoletoService boletoService;
    @Autowired
    private EmailService emailService;

    private final PedidoRepository pedidoRepository;

    public PedidoServiceImpl(PedidoRepository repository) {
        this.pedidoRepository = repository;
    }

    @Override
    public Pedido save(Pedido pedido) {
        return this.pedidoRepository.save(pedido);
    }

    @Override
    public Optional<Pedido> findById(Integer id) {
        return this.pedidoRepository.findById(id);
    }

    @Transactional
    @Override
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(LocalDateTime.now());
        obj.setCliente(clienteRepository.findById(obj.getCliente().getId()).get());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, LocalDateTime.now());
        }
        obj = pedidoRepository.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.findById(ip.getProduto().getId()).get());
            ip.setPreco(produtoService.findById(ip.getProduto().getId()).get().getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        emailService.sendOrderConfirmationEmail(obj);
        return obj;
    }

}
