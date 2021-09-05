package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Produto;
import br.com.wferreiracosta.alfred.utils.RepositoriesTestsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ProdutoRepositoryTest extends RepositoriesTestsUtils {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    @DisplayName("Deve obter um produto por id")
    public void deveRetornaProduto() {
        Produto produto = new Produto(null, "Celular", 1.000);
        Produto produtoSalvo = this.entityManager.persist(produto);
        Optional<Produto> produtoRetornado = this.produtoRepository.findById(produtoSalvo.getId());
        assertThat(produtoRetornado).isPresent();
    }

    @Test
    @DisplayName("Deve salvar uma Produto")
    public void deveSalvarProduto(){
        Produto produto = new Produto(null, "Celular", 1.000);
        Produto produtoSalvo = this.produtoRepository.save(produto);
        assertThat(produtoSalvo.getId()).isNotNull();
        assertThat(produtoSalvo.getId()).isEqualTo(produto.getId());
        assertThat(produtoSalvo.getNome()).isEqualTo(produto.getNome());
        assertThat(produtoSalvo.getPreco()).isEqualTo(produto.getPreco());
    }
}
