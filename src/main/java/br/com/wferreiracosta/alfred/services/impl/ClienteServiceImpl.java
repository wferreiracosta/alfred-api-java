package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.dto.ClienteDTO;
import br.com.wferreiracosta.alfred.repositories.ClienteRepository;
import br.com.wferreiracosta.alfred.services.ClienteService;
import org.springframework.stereotype.Service;

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

}
