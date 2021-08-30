package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.models.Produto;
import br.com.wferreiracosta.alfred.services.ProdutoService;
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

import java.util.Optional;

@WebMvcTest(controllers = ProdutoController.class)
public class ProdutoControllerTest extends ControllersTestsUtils {

    final String PRODUTO_API = "/produtos";

    @Autowired
    MockMvc mvc;

    @MockBean
    ProdutoService produtoService;

    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Deve buscar um produto existente por id e retornar ele")
    public void deveBuscarProdutoPorId() throws Exception {
        Produto produto = Produto.builder().id(1).nome("Celular").preco(1.000).build();

        BDDMockito.given(this.produtoService.findById(produto.getId()))
                .willReturn(Optional.of(produto));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PRODUTO_API.concat("/"+produto.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(produto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(produto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("preco").value(produto.getPreco()));
    }
}
