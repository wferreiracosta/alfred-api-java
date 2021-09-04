package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.models.*;
import br.com.wferreiracosta.alfred.models.enums.EstadoPagamento;
import br.com.wferreiracosta.alfred.models.enums.TipoCliente;
import br.com.wferreiracosta.alfred.repositories.*;
import br.com.wferreiracosta.alfred.services.DataBaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class DataBaseServiceImpl implements DataBaseService {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;

    public DataBaseServiceImpl(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository,
                               CidadeRepository cidadeRepository, EstadoRepository estadoRepository,
                               ClienteRepository clienteRepository, EnderecoRepository enderecoRepository,
                               PedidoRepository pedidoRepository, PagamentoRepository pagamentoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.pedidoRepository = pedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public void instantiateTestDatabase() {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Cama mesa e banho");
        Categoria cat4 = new Categoria(null, "Eletrônicos");

        Produto p1 = new Produto(null, "Computador", 2000.00);
        Produto p2 = new Produto(null, "Impressora", 800.00);
        Produto p3 = new Produto(null, "Toalha", 80.00);
        Produto p4 = new Produto(null, "Calculadora", 50.00);

        cat1.getProdutos().addAll(Arrays.asList(p1, p2));
        cat2.getProdutos().addAll(Arrays.asList(p2, p4));
        cat3.getProdutos().addAll(Arrays.asList(p3));
        cat4.getProdutos().addAll(Arrays.asList(p4));

        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat3));
        p4.getCategorias().addAll(Arrays.asList(cat4));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4));

        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = new Cidade(null, "Uberlândia", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente(null, "Pedro Silva", "pedro@silva.com", "88486319080", TipoCliente.PESSOAFISICA);
        Cliente cli2 = new Cliente(null, "Andre Santos", "andre@santos.com", "96345237000183", TipoCliente.PESSOAJURIDICA);

        cli1.getTelefones().addAll(Arrays.asList("27363323"));
        cli2.getTelefones().addAll(Arrays.asList("93838393"));

        Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
        Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli2, c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1));
        cli2.getEnderecos().addAll(Arrays.asList(e2));

        clienteRepository.saveAll(Arrays.asList(cli1,cli2));
        enderecoRepository.saveAll(Arrays.asList(e1, e2));

        LocalDateTime ped1LocalDateTime = LocalDateTime.of(2017, 9, 30, 10, 32);
        Pedido ped1 = new Pedido(null, ped1LocalDateTime, cli1, e1);

        LocalDateTime ped2LocalDateTime = LocalDateTime.of(2017, 10, 10, 19, 35);
        Pedido ped2 = new Pedido(null, ped2LocalDateTime, cli2, e2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);

        LocalDateTime pagto2LocalDateTime = LocalDateTime.of(2017, 10, 20, 00, 00);
        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, pagto2LocalDateTime,
                null);
        ped2.setPagamento(pagto2);

        cli2.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

    }

}
