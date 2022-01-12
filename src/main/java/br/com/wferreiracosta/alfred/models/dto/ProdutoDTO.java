package br.com.wferreiracosta.alfred.models.dto;

import br.com.wferreiracosta.alfred.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String nome;
    private Double preco;

    public ProdutoDTO(Produto obj) {
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.preco = obj.getPreco();
    }

}
