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

class CategoriaServiceTest extends ServicesTestsUtils {

    CategoriaService service;

    @MockBean
    CategoriaRepository categoriaRepository;

    @BeforeEach
    public void setUp() {
        this.service = new CategoriaServiceImpl(categoriaRepository);
    }

    @Test
    @DisplayName("Deve buscar uma categoria por id")
    void buscarCategoriaPorId(){
        Categoria categoria = Categoria.builder().id(1).nome("Informatica").build();

        Mockito.when(this.categoriaRepository.findById(categoria.getId())).thenReturn(Optional.of(categoria));

        Optional<Categoria> categoriaRetornada = this.service.findById(categoria.getId());

        assertThat(categoriaRetornada).isPresent();
        assertThat(categoriaRetornada.get().getId()).isEqualTo(categoria.getId());
        assertThat(categoriaRetornada.get().getNome()).isEqualTo(categoria.getNome());
    }

    @Test
    @DisplayName("Deve salvar uma categoria no banco de dados")
    void deveSalvarUmaCategoria(){
        Categoria categoria = Categoria.builder().id(null).nome("Informatica").build();
        Categoria categoriaSalva = Categoria.builder().id(1).nome("Informatica").build();

        Mockito.when(this.categoriaRepository.save(categoria)).thenReturn(categoriaSalva);

        Categoria categoriaRetornada = this.service.save(categoria);

        assertThat(categoriaRetornada.getId()).isEqualTo(categoriaSalva.getId());
        assertThat(categoriaRetornada.getNome()).isEqualTo(categoriaSalva.getNome());
    }

}
