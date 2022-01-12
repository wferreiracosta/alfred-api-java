package br.com.wferreiracosta.alfred.models.dto;

import br.com.wferreiracosta.alfred.validation.annotation.ClienteUpdate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@Builder
@ClienteUpdate
public class ClienteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message="Preenchimento obrigatório!!!")
    @Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
    private String nome;

    @NotEmpty(message="Preenchimento obrigatório!!!")
    @Email(message="E-mail invalido")
    private String email;
}
