package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.utils.RepositoriesTestsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CategoriaRepositoryTest extends RepositoriesTestsUtils {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private CategoriaRepository repository;

    @Test
    @DisplayName("Deve obter uma categoria por id")
    void deveRetornaCategoria(){
        Categoria categoria = Categoria.builder().id(null).nome("Informatica").build();
        Categoria categoriaSalva = this.entityManager.persist(categoria);
        Optional<Categoria> categoriaRetornada = this.repository.findById(categoriaSalva.getId());
        assertThat(categoriaRetornada).isPresent();
    }

    @Test
    @DisplayName("Deve salvar uma Categoria")
    void salvarCategoria(){
        Categoria categoria = Categoria.builder().id(null).nome("Informatica").build();
        Categoria categoriaSalva = this.repository.save(categoria);
        assertThat(categoriaSalva.getId()).isNotNull();
        assertThat(categoriaSalva.getId()).isEqualTo(categoria.getId());
        assertThat(categoriaSalva.getNome()).isEqualTo(categoria.getNome());
    }

}
