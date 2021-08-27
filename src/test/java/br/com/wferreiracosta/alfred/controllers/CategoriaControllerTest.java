package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.services.CategoriaService;
import br.com.wferreiracosta.alfred.utils.ControllersTestsUtils;
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

import java.util.Optional;

@WebMvcTest(controllers = CategoriaController.class)
public class CategoriaControllerTest extends ControllersTestsUtils {

    static String CATEGORIA_API = "/categorias";

    @Autowired
    MockMvc mvc;

    @MockBean
    CategoriaService service;

    @Test
    @DisplayName("Deve buscar uma categoria existente e retornar ela")
    public void deveBuscarERetornarUmaCategoriaExistente() throws Exception {
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
}
