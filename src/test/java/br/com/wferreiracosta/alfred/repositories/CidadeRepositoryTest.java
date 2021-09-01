package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Cidade;
import br.com.wferreiracosta.alfred.utils.RepositoriesTestsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CidadeRepositoryTest extends RepositoriesTestsUtils {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Test
    @DisplayName("Deve salvar uma Cidade")
    public void salvarCidade(){
        Cidade cidade = Cidade.builder().id(null).nome("São Paulo").build();
        Cidade cidadeSalva = this.cidadeRepository.save(cidade);
        assertThat(cidadeSalva.getId()).isNotNull();
        assertThat(cidadeSalva.getId()).isEqualTo(cidade.getId());
        assertThat(cidadeSalva.getNome()).isEqualTo(cidade.getNome());
    }

}
