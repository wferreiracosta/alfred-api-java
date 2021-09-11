package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.dto.ClienteDTO;

import java.util.Optional;

public interface ClienteService {

    public Optional<Cliente> findById(Integer id);

    public Cliente update(ClienteDTO objDTO);

}
