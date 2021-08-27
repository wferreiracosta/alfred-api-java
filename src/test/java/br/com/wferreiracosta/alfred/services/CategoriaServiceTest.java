package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.repositories.CategoriaRepository;
import br.com.wferreiracosta.alfred.services.impl.CategoriaServiceImpl;
import br.com.wferreiracosta.alfred.utils.ServicesTestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriaServiceTest extends ServicesTestsUtils {

    CategoriaService service;

    @MockBean
    CategoriaRepository categoriaRepository;

    @BeforeEach
    public void setUp() {
        this.service = new CategoriaServiceImpl(categoriaRepository);
    }

    @Test
    @DisplayName("Deve buscar uma categoria por id")
    public void buscarCategoriaPorId(){
        Categoria categoria = Categoria.builder().id(1).nome("Informatica").build();

        Mockito.when(this.categoriaRepository.findById(categoria.getId())).thenReturn(Optional.of(categoria));

        Optional<Categoria> categoriaRetornada = this.service.findById(categoria.getId());

        assertThat(categoriaRetornada.isPresent()).isTrue();
        assertThat(categoriaRetornada.get().getId()).isEqualTo(categoria.getId());
        assertThat(categoriaRetornada.get().getNome()).isEqualTo(categoria.getNome());
    }

}
