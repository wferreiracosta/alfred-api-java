package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.*;
import br.com.wferreiracosta.alfred.models.enums.EstadoPagamento;
import br.com.wferreiracosta.alfred.models.enums.TipoCliente;
import br.com.wferreiracosta.alfred.repositories.PedidoRepository;
import br.com.wferreiracosta.alfred.services.impl.PedidoServiceImpl;
import br.com.wferreiracosta.alfred.utils.ServicesTestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class PedidoServiceTest extends ServicesTestsUtils {

    PedidoService service;

    @MockBean
    PedidoRepository repository;

    @BeforeEach
    public void setUp() {
        this.service = new PedidoServiceImpl(repository);
    }

    private Pedido getPedidoComPagamentoComCartao() {
        Cidade cidade = Cidade.builder().id(null).nome("São Paulo").build();
        Cliente cliente = new Cliente(null, "Pedro Silva", "pedro@silva.com", "73094044085", TipoCliente.PESSOAFISICA);
        Endereco endereco = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cliente, cidade);
        LocalDateTime ped1LocalDateTime = LocalDateTime.of(2017, 9, 30, 10, 32);
        Pedido pedido = new Pedido(null, ped1LocalDateTime, cliente, endereco);
        Pagamento pagamento = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido, 6);
        pedido.setPagamento(pagamento);
        return pedido;
    }

    @Test
    @DisplayName("Deve salvar um pedido")
    public void deveSalvarPedido(){
        Pedido pedido = this.getPedidoComPagamentoComCartao();
        Pedido pedidoSalvo = this.getPedidoComPagamentoComCartao();
        pedidoSalvo.setId(1);

        Mockito.when(this.repository.save(pedido)).thenReturn(pedidoSalvo);

        Pedido pedidoRetornado = this.service.save(pedido);

        assertThat(pedidoRetornado.getId()).isEqualTo(pedidoSalvo.getId());
    }

}
