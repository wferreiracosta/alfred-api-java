package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.enums.Perfil;
import br.com.wferreiracosta.alfred.exception.AuthorizationException;
import br.com.wferreiracosta.alfred.exception.DataIntegrityException;
import br.com.wferreiracosta.alfred.models.Cidade;
import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.Endereco;
import br.com.wferreiracosta.alfred.models.dto.ClienteDTO;
import br.com.wferreiracosta.alfred.models.dto.ClienteNewDTO;
import br.com.wferreiracosta.alfred.enums.TipoCliente;
import br.com.wferreiracosta.alfred.repositories.ClienteRepository;
import br.com.wferreiracosta.alfred.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Optional<Cliente> findById(Integer id) {
        final var user = UserServiceImpl.authenticated();

        if(isNull(user) || !id.equals(user.getId())){
            throw new AuthorizationException("Acesso Negado");
        }

        return this.clienteRepository.findById(id);
    }

    @Override
    public Cliente update(ClienteDTO objDTO) {
        var newObj = this.findById(objDTO.getId()).get();

        newObj.setNome(objDTO.getNome());
        newObj.setEmail(objDTO.getEmail());

        return this.clienteRepository.save(newObj);
    }

    @Override
    public Optional<Cliente> delete(Integer id) {
        var clienteOptional = this.findById(id);

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
        var cliente = this.fromDTO(clienteNewDTO);
        return this.clienteRepository.save(cliente);
    }

    public Cliente fromDTO(ClienteNewDTO objNewDTO) {
        var cli = new Cliente(null,
                objNewDTO.getNome(),
                objNewDTO.getEmail(),
                objNewDTO.getCpfOuCnpj(),
                TipoCliente.toEnum(objNewDTO.getTipo()),
                bCryptPasswordEncoder.encode(objNewDTO.getSenha())
        );

        var cid = new Cidade(objNewDTO.getCidadeId(), null, null);

        var end = new Endereco(null,
                objNewDTO.getLogradouro(),
                objNewDTO.getNumero(),
                objNewDTO.getComplemento(),
                objNewDTO.getBairro(),
                objNewDTO.getCep(),
                cli,
                cid
        );

        cli.getEnderecos().add(end);
        cli.getTelefones().add(objNewDTO.getTelefone1());

        if (objNewDTO.getTelefone2()!=null) {
            cli.getTelefones().add(objNewDTO.getTelefone2());
        }
        if (objNewDTO.getTelefone3()!=null) {
            cli.getTelefones().add(objNewDTO.getTelefone3());
        }
        return cli;
    }

}
