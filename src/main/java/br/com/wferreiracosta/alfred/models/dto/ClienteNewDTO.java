package br.com.wferreiracosta.alfred.models.dto;

import br.com.wferreiracosta.alfred.validation.annotation.ClienteInsert;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ClienteInsert
public class ClienteNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento obrigatório!!!")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatório!!!")
    @Email(message = "E-mail invalido")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório!!!")
    private String cpfOuCnpj;

    private Integer tipo;

    @NotEmpty(message = "Preenchimento obrigatório!!!")
    private String logradouro;

    @NotEmpty(message = "Preenchimento obrigatório!!!")
    private String numero;

    private String complemento;

    private String bairro;

    @NotEmpty(message = "Preenchimento obrigatório!!!")
    private String cep;

    private Integer cidadeId;

    @NotEmpty(message = "Preenchimento obrigatório!!!")
    private String telefone1;

    private String telefone2;

    private String telefone3;
}
