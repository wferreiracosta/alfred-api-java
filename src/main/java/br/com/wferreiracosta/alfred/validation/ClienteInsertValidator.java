package br.com.wferreiracosta.alfred.validation;

import br.com.wferreiracosta.alfred.exception.FieldMessage;
import br.com.wferreiracosta.alfred.models.Cliente;
import br.com.wferreiracosta.alfred.models.dto.ClienteNewDTO;
import br.com.wferreiracosta.alfred.models.enums.TipoCliente;
import br.com.wferreiracosta.alfred.repositories.ClienteRepository;
import br.com.wferreiracosta.alfred.validation.annotation.ClienteInsert;
import br.com.wferreiracosta.alfred.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (clienteNewDTO.getTipo() == null) {
            list.add(new FieldMessage("tipo", "Tipo não pode ser null!"));
        } else {
            if (clienteNewDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(clienteNewDTO.getCpfOuCnpj())) {
                list.add(new FieldMessage("cpfOuCnpj", "CPF inválido!"));
            }

            if (clienteNewDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())) {
                list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido!"));
            }
        }

        Cliente cliente = clienteRepository.findByEmail(clienteNewDTO.getEmail());

        if(cliente != null) {
            list.add(new FieldMessage("email","O e-mail informado já existe!"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

}
