package br.com.wferreiracosta.alfred.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message="Preenchimento obrigatório!!!")
    @Email(message="E-mail invalido")
    private String email;

}
