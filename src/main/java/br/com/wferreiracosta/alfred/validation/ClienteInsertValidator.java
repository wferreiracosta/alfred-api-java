package br.com.wferreiracosta.alfred.validation;

import br.com.wferreiracosta.alfred.exception.FieldMessage;
import br.com.wferreiracosta.alfred.models.dto.ClienteNewDTO;
import br.com.wferreiracosta.alfred.models.enums.TipoCliente;
import br.com.wferreiracosta.alfred.validation.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

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

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

}
