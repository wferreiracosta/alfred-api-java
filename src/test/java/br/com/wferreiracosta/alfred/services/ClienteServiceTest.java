package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Cidade;
import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.Endereco;
import br.com.wferreiracosta.alfred.models.Estado;
import br.com.wferreiracosta.alfred.models.dto.ClienteDTO;
import br.com.wferreiracosta.alfred.models.dto.ClienteNewDTO;
import br.com.wferreiracosta.alfred.models.enums.TipoCliente;
import br.com.wferreiracosta.alfred.repositories.ClienteRepository;
import br.com.wferreiracosta.alfred.services.impl.ClienteServiceImpl;
import br.com.wferreiracosta.alfred.utils.ServicesTestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteServiceTest extends ServicesTestsUtils {

    ClienteService service;

    @MockBean
    ClienteRepository repository;

    private Cliente getCliente() {
        Estado estado = new Estado(null, "São Paulo");
        Cidade cidade = new Cidade(null, "Uberlândia", estado);
        estado.getCidades().addAll(Arrays.asList(cidade));
        Cliente cliente = new Cliente(1, "Pedro Silva", "pedro@silva.com", "88486319080", TipoCliente.PESSOAFISICA, "123");
        Endereco endereco = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cliente, cidade);
        cliente.getEnderecos().addAll(Arrays.asList(endereco));
        return cliente;
    }

    private ClienteNewDTO getClienteNewDTO() {
        return ClienteNewDTO.builder()
                .nome("Pedro Silva")
                .email("pedro@silva.com")
                .cpfOuCnpj("88486319080")
                .tipo(TipoCliente.PESSOAFISICA.getCod())
                .logradouro("Rua Flores")
                .numero("300")
                .complemento("Apto 303")
                .bairro("Jardim")
                .cep("38220834")
                .cidadeId(1)
                .telefone1("124578453")
                .build();
    }

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


    // TODO Corrigir teste de inserção de um cliente, o BCryptPasswordEncoder não está sendo inicializado
    @Test
    @DisplayName("Deve inserir um cliente na base")
    void deveInserirNovoCliente(){
        Cliente cliente = this.getCliente();
        cliente.setId(null);

        ClienteNewDTO clienteNewDTO = this.getClienteNewDTO();

        Cliente clienteReturn = this.getCliente();
        clienteReturn.setId(1);

        Mockito.when(this.repository.save(cliente)).thenReturn(clienteReturn);

        Cliente clienteInsert = this.service.insert(clienteNewDTO);

        assertThat(clienteInsert.getId()).isEqualTo(clienteReturn.getId());
        assertThat(clienteInsert.getNome()).isEqualTo(clienteNewDTO.getNome());
        assertThat(clienteInsert.getEmail()).isEqualTo(clienteNewDTO.getEmail());
    }

}
