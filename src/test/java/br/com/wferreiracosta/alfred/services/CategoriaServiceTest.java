package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.models.dto.CategoriaDTO;
import br.com.wferreiracosta.alfred.repositories.CategoriaRepository;
import br.com.wferreiracosta.alfred.services.impl.CategoriaServiceImpl;
import br.com.wferreiracosta.alfred.utils.ServicesTestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Test
    @DisplayName("Deve alterar categoria")
    void deveAlterarCategoria(){
        Categoria categoriaOld = Categoria.builder().id(1).nome("Informatica").build();
        Categoria categoria = Categoria.builder().id(1).nome("Tecnologia").build();
        CategoriaDTO categoriaNew = CategoriaDTO.builder().id(1).nome("Tecnologia").build();

        Mockito.when(this.categoriaRepository.findById(categoriaOld.getId())).thenReturn(Optional.of(categoriaOld));
        Mockito.when(this.categoriaRepository.save(categoria)).thenReturn(categoria);

        CategoriaDTO categoriaUpdate = this.service.update(categoriaNew);

        assertThat(categoriaUpdate.getId()).isEqualTo(categoriaNew.getId());
        assertThat(categoriaUpdate.getNome()).isEqualTo(categoriaNew.getNome());
    }

    @Test
    @DisplayName("Deve apagar categoria")
    void deveApagarCategoria(){
        Categoria categoria = Categoria.builder().id(1).nome("Informatica").build();

        this.service.delete(categoria.getId());
        Optional<Categoria> categoriaOptional = this.service.findById(categoria.getId());

        assertThat(categoriaOptional).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar todas as categorias")
    void deveRetornarTodasAsCategorias(){
        Categoria cat1 = new Categoria(1, "Informática");
        Categoria cat2 = new Categoria(2, "Escritório");

        List<Categoria> categoriaList = List.of(cat1,cat2);

        Mockito.when(this.categoriaRepository.findAll()).thenReturn(categoriaList);

        List<CategoriaDTO> categoriaDTOList = this.service.findAll();

        assertThat(categoriaList.size()).isEqualTo(categoriaDTOList.size());
        assertThat(categoriaList).contains(cat1);
        assertThat(categoriaList).contains(cat2);
    }

    @Test
    @DisplayName("Deve buscar todas as categorias de forma paginada")
    void deveBuscarTodasAsCategoriasDeFormaPaginada(){
        Categoria cat1 = new Categoria(1, "Informática");
        Categoria cat2 = new Categoria(2, "Escritório");

        List<Categoria> categoriaList = List.of(cat1, cat2);

        Page<Categoria> categoriaPage = new PageImpl<>(categoriaList);

        Integer page = 0;
        Integer linesPerPage = 2;
        String orderBy = "id";
        String direction = "DESC";
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Mockito.when(this.categoriaRepository.findAll(pageRequest))
                .thenReturn(categoriaPage);

        Page<CategoriaDTO> returnCategoriaDTOPage = this.service.findAllWithPagination(page, linesPerPage, orderBy, direction);

        assertThat(returnCategoriaDTOPage.isFirst()).isTrue();
        assertThat(returnCategoriaDTOPage.isEmpty()).isFalse();
        assertThat(returnCategoriaDTOPage.getTotalElements()).isEqualTo(2);
    }

}
