package br.com.wferreiracosta.alfred.models.dto;

import br.com.wferreiracosta.alfred.models.Categoria;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CategoriaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private Integer id;

    @NotEmpty(message="Preenchimento obrigatório")
    @Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
    private String nome;

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }
}
