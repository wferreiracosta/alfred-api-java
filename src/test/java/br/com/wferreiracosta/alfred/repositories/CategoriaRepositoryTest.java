package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.utils.RepositoriesTestsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriaRepositoryTest extends RepositoriesTestsUtils {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private CategoriaRepository repository;

    @Test
    @DisplayName("Deve obter uma categoria por id")
    public void deveRetornaCategoria(){
        Categoria categoria = Categoria.builder().id(null).nome("Informatica").build();
        Categoria categoriaSalva = this.entityManager.persist(categoria);
        Optional<Categoria> categoriaRetornada = this.repository.findById(categoriaSalva.getId());
        assertThat(categoriaRetornada.isPresent()).isTrue();
    }

}
