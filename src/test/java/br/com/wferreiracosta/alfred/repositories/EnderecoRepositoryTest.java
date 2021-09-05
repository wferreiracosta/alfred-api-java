package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Cidade;
import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.Endereco;
import br.com.wferreiracosta.alfred.models.Estado;
import br.com.wferreiracosta.alfred.models.enums.TipoCliente;
import br.com.wferreiracosta.alfred.utils.RepositoriesTestsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

class EnderecoRepositoryTest extends RepositoriesTestsUtils {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Test
    @DisplayName("Deve salvar um Endereço")
    void salvarEndereco(){
        Estado estado = new Estado(null, "Minas Gerais");
        Cidade cidade = new Cidade(null, "Uberlândia", estado);
        Cliente cliente = new Cliente(null, "Pedro Silva", "pedro@silva.com", "73094044085", TipoCliente.PESSOAFISICA);
        Endereco endereco = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cliente, cidade);
        Endereco enderecoSalva = this.enderecoRepository.save(endereco);
        assertThat(enderecoSalva.getId()).isNotNull();
        assertThat(enderecoSalva).isEqualTo(endereco);
    }

}
