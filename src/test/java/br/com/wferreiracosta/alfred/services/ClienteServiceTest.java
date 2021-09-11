package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.dto.ClienteDTO;
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

    @Test
    @DisplayName("Deve Atualizar cliente")
    void deveAtualizarCliente(){
        Cliente cliente = new Cliente(1, "Andre Silva", "Andre@silva.com", "88486319080", TipoCliente.PESSOAFISICA);
        Cliente clienteNew = new Cliente(1, "Pedro Silva", "pedro@silva.com", "88486319080", TipoCliente.PESSOAFISICA);
        ClienteDTO clienteDTO = ClienteDTO.builder().id(1).nome("Pedro Silva").email("pedro@silva.com").build();

        Mockito.when(this.repository.findById(clienteDTO.getId())).thenReturn(Optional.of(cliente));
        Mockito.when(this.repository.save(clienteNew)).thenReturn(clienteNew);

        Cliente clienteUpdate = this.service.update(clienteDTO);

        assertThat(clienteUpdate.getId()).isEqualTo(clienteDTO.getId());
        assertThat(clienteUpdate.getNome()).isEqualTo(clienteDTO.getNome());
        assertThat(clienteUpdate.getEmail()).isEqualTo(clienteDTO.getEmail());
    }

    @Test
    @DisplayName("Deve apagar cliente da base")
    void deveApagarClienteDaBase(){
        Cliente cliente = new Cliente(1, "Andre Silva", "Andre@silva.com", "88486319080", TipoCliente.PESSOAFISICA);

        Mockito.when(this.repository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        this.repository.deleteById(cliente.getId());
        Optional<Cliente> clienteOptional = this.service.delete(cliente.getId());

        assertThat(clienteOptional.get().getId()).isEqualTo(cliente.getId());
        assertThat(clienteOptional.get().getNome()).isEqualTo(cliente.getNome());
        assertThat(clienteOptional.get().getEmail()).isEqualTo(cliente.getEmail());
    }

}
