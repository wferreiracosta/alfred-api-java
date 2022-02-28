package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.enums.TipoCliente;
import br.com.wferreiracosta.alfred.utils.RepositoriesTestsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteRepositoryTest extends RepositoriesTestsUtils {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Deve salvar um Cliente")
    void salvarCliente(){
        Cliente cliente = new Cliente(null, "Pedro Silva", "pedro@silva.com", "73094044085", TipoCliente.PESSOAFISICA);
        Cliente clienteSalva = this.clienteRepository.save(cliente);
        assertThat(clienteSalva.getId()).isNotNull();
        assertThat(clienteSalva).isEqualTo(cliente);
    }
}
