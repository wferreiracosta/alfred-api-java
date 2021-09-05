package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.enums.TipoCliente;
import br.com.wferreiracosta.alfred.repositories.ClienteRepository;
import br.com.wferreiracosta.alfred.services.impl.ClienteServiceImpl;
import br.com.wferreiracosta.alfred.utils.ServicesTestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteServiceTest extends ServicesTestsUtils {

    ClienteService service;

    @MockBean
    ClienteRepository repository;

    @BeforeEach
    public void setUp() {
        this.service = new ClienteServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve buscar um cliente por id")
    void buscarCategoriaPorId(){
        Cliente cliente = new Cliente(1, "Pedro Silva", "pedro@silva.com", "88486319080", TipoCliente.PESSOAFISICA);

        Mockito.when(this.repository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        Optional<Cliente> clienteRetornada = this.service.findById(cliente.getId());

        assertThat(clienteRetornada).isPresent().contains(cliente);
    }

}
