package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.exception.DataIntegrityException;
import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.models.Produto;
import br.com.wferreiracosta.alfred.models.dto.CategoriaDTO;
import br.com.wferreiracosta.alfred.services.CategoriaService;
import br.com.wferreiracosta.alfred.utils.ControllersTestsUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebMvcTest(controllers = CategoriaController.class)
class CategoriaControllerTest extends ControllersTestsUtils {

    static String CATEGORIA_API = "/categorias";

    @Autowired
    MockMvc mvc;

    @MockBean
    CategoriaService service;

    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Deve buscar uma categoria existente e retornar ela")
    void deveBuscarERetornarUmaCategoriaExistente() throws Exception {
        Categoria categoria = Categoria.builder().id(1).nome("Informatica").build();

        BDDMockito.given(this.service.findById(categoria.getId()))
                .willReturn(Optional.of(categoria));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CATEGORIA_API.concat("/"+categoria.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(categoria.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(categoria.getNome()));
    }

    @Test
    @DisplayName("Deve buscar uma categoria que não existe e retornar o status not_found")
    void deveBuscarUmaCategoriaQueNaoExistente() throws Exception {
        Integer id = 1;
        String msg = "Objeto não encontrado! Id: " + id;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CATEGORIA_API.concat("/"+id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value(msg));
    }

    @Test
    @DisplayName("Deve salvar uma categoria no banco de dados")
    void deveSalvarUmaCategoria() throws Exception {
        Categoria categoria = Categoria.builder().id(null).nome("Informatica").build();

        BDDMockito.given(this.service.save(categoria))
                .willReturn(Categoria.builder().id(1).nome("Informatica").build());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CATEGORIA_API)
                .content(asJsonString(categoria))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(categoria.getNome()));
    }

    @Test
    @DisplayName("Deve alterar uma categoria no banco de dados")
    void deveAlterarUmaCategoria() throws Exception {
        CategoriaDTO categoriaNew = CategoriaDTO.builder().nome("Tecnologia").build();
        CategoriaDTO categoria = CategoriaDTO.builder().id(1).nome("Tecnologia").build();

        BDDMockito.given(this.service.update(categoria))
                .willReturn(categoria);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(CATEGORIA_API.concat("/"+categoria.getId()))
                .content(asJsonString(categoriaNew))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(categoria.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(categoriaNew.getNome()));
    }

    @Test
    @DisplayName("Deve apagar uma categoria")
    void deveApagarCategoria() throws Exception {
        Categoria categoria = Categoria.builder().id(1).nome("Informatica").build();

        BDDMockito.given(this.service.findById(categoria.getId()))
                .willReturn(Optional.of(categoria));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(CATEGORIA_API.concat("/"+categoria.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar erro quando tentar apagar categoria com produto")
    void deveRetornarErroQuandoTentarApagarCategoriaComProduto() throws Exception {
        BDDMockito
                .given(this.service.delete(1))
                .willThrow(new DataIntegrityException("Não é possivel apagar categoria que possui produtos"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(CATEGORIA_API.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value("Não é possivel apagar categoria que possui produtos"));
    }

    @Test
    @DisplayName("Deve retornar todas as categorias")
    void deveRetornarTodasAsCategorias() throws Exception {
        Categoria cat1 = new Categoria(1, "Informática");
        Categoria cat2 = new Categoria(2, "Escritório");

        List<Categoria> categoriaList = List.of(cat1, cat2);
        List<CategoriaDTO> categoriaDTOList = categoriaList
                .stream()
                .map(obj -> new CategoriaDTO(obj))
                .collect(Collectors.toList());

        BDDMockito
                .given(this.service.findAll())
                .willReturn(categoriaDTOList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CATEGORIA_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.asJsonString(categoriaDTOList)));
    }

}
