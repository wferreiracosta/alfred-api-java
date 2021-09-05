package br.com.wferreiracosta.alfred.controllers;

import br.com.wferreiracosta.alfred.models.*;
import br.com.wferreiracosta.alfred.models.enums.EstadoPagamento;
import br.com.wferreiracosta.alfred.models.enums.TipoCliente;
import br.com.wferreiracosta.alfred.services.PedidoService;
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

import java.time.LocalDateTime;
import java.util.Optional;

@WebMvcTest(controllers = PedidoController.class)
public class PedidoControllerTest extends ControllersTestsUtils {

    static String PEDIDO_API = "/pedidos";

    @Autowired
    MockMvc mvc;

    @MockBean
    PedidoService service;

    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Pedido getPedidoComPagamentoComCartao() {
        Cidade cidade = Cidade.builder().id(null).nome("São Paulo").build();
        Cliente cliente = new Cliente(null, "Pedro Silva", "pedro@silva.com", "73094044085", TipoCliente.PESSOAFISICA);
        Endereco endereco = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cliente, cidade);
        LocalDateTime ped1LocalDateTime = LocalDateTime.of(2017, 9, 30, 10, 32);
        Pedido pedido = new Pedido(null, ped1LocalDateTime, cliente, endereco);
        Pagamento pagamento = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido, 6);
        pedido.setPagamento(pagamento);
        return pedido;
    }

    @Test
    @DisplayName("Deve buscar um pedido existente e retornar")
    public void deveBuscarERetornarUmPedidoExistente() throws Exception {
        Pedido pedido = this.getPedidoComPagamentoComCartao();
        pedido.setId(1);

        BDDMockito.given(this.service.findById(pedido.getId()))
                .willReturn(Optional.of(pedido));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PEDIDO_API.concat("/"+pedido.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.content().json(this.asJsonString(pedido)));
    }

    @Test
    @DisplayName("Deve buscar um pedido que não existe e retornar o status not_found")
    public void deveBuscarUmPedidoQueNaoExistente() throws Exception {
        Integer id = 1;
        String msg = "Pedido não encontrado! Id: " + id;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PEDIDO_API.concat("/"+id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("msg").value(msg));
    }

}
