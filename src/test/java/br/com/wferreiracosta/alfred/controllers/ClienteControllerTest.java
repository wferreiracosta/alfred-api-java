package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.models.Cidade;
import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.Endereco;
import br.com.wferreiracosta.alfred.models.Estado;
import br.com.wferreiracosta.alfred.models.enums.TipoCliente;
import br.com.wferreiracosta.alfred.services.ClienteService;
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
import java.util.Optional;

@WebMvcTest(controllers = ClienteController.class)
public class ClienteControllerTest extends ControllersTestsUtils {

    static String CLIENTE_API = "/clientes";

    @Autowired
    MockMvc mvc;

    @MockBean
    ClienteService service;

    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Cliente getCliente() {
        Estado estado = new Estado(null, "São Paulo");
        Cidade cidade = new Cidade(null, "Uberlândia", estado);
        estado.getCidades().addAll(Arrays.asList(cidade));
        Cliente cliente = new Cliente(1, "Pedro Silva", "pedro@silva.com", "88486319080", TipoCliente.PESSOAFISICA);
        Endereco endereco = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cliente, cidade);
        cliente.getEnderecos().addAll(Arrays.asList(endereco));
        return cliente;
    }

    @Test
    @DisplayName("Deve buscar e retornar os dados de um cliente por id")
    public void deveBuscarERetornarOsDadosDeUmClientePeloId() throws Exception {
        Cliente cliente = getCliente();

        BDDMockito.given(this.service.findById(cliente.getId()))
                .willReturn(Optional.of(cliente));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CLIENTE_API.concat("/"+cliente.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.content().json(this.asJsonString(cliente), true));
    }

    @Test
    @DisplayName("Deve buscar um cliente que não existe e retornar o status not_found")
    public void deveBuscarUmClienteQueNaoExistente() throws Exception {
        Integer id = 1;
        String msg = "Cliente não encontrado! Id: " + id;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CLIENTE_API.concat("/"+id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value(msg));
    }

}
