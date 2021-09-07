package br.com.wferreiracosta.alfred.models.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class CategoriaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private Integer id;

    @NotEmpty(message="Preenchimento obrigatório")
    private String nome;

}
