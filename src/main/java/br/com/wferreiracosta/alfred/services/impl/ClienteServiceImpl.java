package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.exception.DataIntegrityException;
import br.com.wferreiracosta.alfred.models.Cidade;
import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.Endereco;
import br.com.wferreiracosta.alfred.models.dto.ClienteDTO;
import br.com.wferreiracosta.alfred.models.dto.ClienteNewDTO;
import br.com.wferreiracosta.alfred.models.enums.TipoCliente;
import br.com.wferreiracosta.alfred.repositories.ClienteRepository;
import br.com.wferreiracosta.alfred.services.ClienteService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Optional<Cliente> findById(Integer id) {
        return this.clienteRepository.findById(id);
    }

    @Override
    public Cliente update(ClienteDTO objDTO) {
        Cliente newObj = this.findById(objDTO.getId()).get();
        newObj.setNome(objDTO.getNome());
        newObj.setEmail(objDTO.getEmail());
        return this.clienteRepository.save(newObj);
    }

    @Override
    public Optional<Cliente> delete(Integer id) {
        Optional<Cliente> clienteOptional = this.findById(id);
        try {
            clienteOptional.ifPresent(this.clienteRepository::delete);
            return clienteOptional;
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel apagar cliente que possui pedidos");
        }
    }

    @Transactional
    @Override
    public Cliente insert(ClienteNewDTO clienteNewDTO) {
        Cliente cliente = this.fromDTO(clienteNewDTO);
        cliente.setId(null);
        return this.clienteRepository.save(cliente);
    }

    public Cliente fromDTO(ClienteNewDTO objNewDTO) {
        Cliente cliente = new Cliente(null, objNewDTO.getNome(), objNewDTO.getEmail(), objNewDTO.getCpfOuCnpj(),
                TipoCliente.toEnum(objNewDTO.getTipo()));
        Cidade cidade = new Cidade(objNewDTO.getCidadeId(), null, null);
        Endereco endereco = new Endereco(null, objNewDTO.getLogradouro(), objNewDTO.getNumero(),
                objNewDTO.getComplemento(), objNewDTO.getBairro(), objNewDTO.getCep(), cliente, cidade);
        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(objNewDTO.getTelefone1());
        if (objNewDTO.getTelefone2() != null) {
            cliente.getTelefones().add(objNewDTO.getTelefone2());
        }
        if (objNewDTO.getTelefone3() != null) {
            cliente.getTelefones().add(objNewDTO.getTelefone3());
        }
        return cliente;
    }

}
