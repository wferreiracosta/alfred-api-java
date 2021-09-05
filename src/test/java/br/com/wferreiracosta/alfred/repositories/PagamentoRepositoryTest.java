package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.*;
import br.com.wferreiracosta.alfred.models.enums.EstadoPagamento;
import br.com.wferreiracosta.alfred.models.enums.TipoCliente;
import br.com.wferreiracosta.alfred.utils.RepositoriesTestsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PagamentoRepositoryTest extends RepositoriesTestsUtils {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    private Pagamento getPagamentoComCartao() {
        Cidade cidade = Cidade.builder().id(null).nome("São Paulo").build();
        Cliente cliente = new Cliente(null, "Pedro Silva", "pedro@silva.com", "73094044085", TipoCliente.PESSOAFISICA);
        Endereco endereco = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cliente, cidade);
        LocalDateTime ped1LocalDateTime = LocalDateTime.of(2017, 9, 30, 10, 32);
        Pedido pedido = new Pedido(null, ped1LocalDateTime, cliente, endereco);
        Pagamento pagamento = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido, 6);
        pedido.setPagamento(pagamento);
        return pagamento;
    }

    @Test
    @DisplayName("Deve salvar um pagamento")
    void devSalvarPagamento(){
        Pagamento pagamento = getPagamentoComCartao();

        Pagamento pagamentoSalva = this.pagamentoRepository.save(pagamento);
        assertThat(pagamentoSalva.getId()).isNotNull();
        assertThat(pagamentoSalva.getId()).isEqualTo(pagamento.getId());
    }
}
