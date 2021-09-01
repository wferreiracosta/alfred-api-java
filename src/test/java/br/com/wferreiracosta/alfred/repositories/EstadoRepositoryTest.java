package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Cidade;
import br.com.wferreiracosta.alfred.models.Estado;
import br.com.wferreiracosta.alfred.utils.RepositoriesTestsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class EstadoRepositoryTest extends RepositoriesTestsUtils {

    @Autowired
    private EstadoRepository estadoRepository;

    @Test
    @DisplayName("Deve salvar uma Estado")
    public void salvarEstado(){
        Estado estado = Estado.builder().id(null).nome("São Paulo").build();
        Estado estadoSalva = this.estadoRepository.save(estado);
        assertThat(estadoSalva.getId()).isNotNull();
        assertThat(estadoSalva.getId()).isEqualTo(estado.getId());
        assertThat(estadoSalva.getNome()).isEqualTo(estado.getNome());
    }
}
