package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.dto.ClienteDTO;
import br.com.wferreiracosta.alfred.models.dto.ClienteNewDTO;

import java.util.Optional;

public interface ClienteService {

    Optional<Cliente> findById(Integer id);
    Cliente update(ClienteDTO objDTO);
    Optional<Cliente> delete(Integer id);
    Cliente insert(ClienteNewDTO clienteNewDTO);
    Cliente findByEmail(String email);
    Cliente insert(Cliente cliente);
}
