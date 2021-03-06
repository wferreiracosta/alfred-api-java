package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Produto;
import br.com.wferreiracosta.alfred.repositories.CategoriaRepository;
import br.com.wferreiracosta.alfred.repositories.ProdutoRepository;
import br.com.wferreiracosta.alfred.services.impl.ProdutoServiceImpl;
import br.com.wferreiracosta.alfred.utils.ServicesTestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProdutoServiceTest  extends ServicesTestsUtils {

    ProdutoService produtoService;

    @MockBean
    ProdutoRepository produtoRepository;

    @MockBean
    CategoriaRepository categoriaRepository;

    @BeforeEach
    public void setUp(){
        this.produtoService = new ProdutoServiceImpl(produtoRepository, categoriaRepository);
    }

    @Test
    @DisplayName("Deve retorna um produto por id")
    void deveRetornarProduto(){
        Produto produto = new Produto(1, "Celular", 1.000);

        Mockito.when(this.produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));

        Optional<Produto> produtoRetornado = this.produtoService.findById(produto.getId());

        assertThat(produtoRetornado).isPresent();
        assertThat(produtoRetornado.get().getId()).isEqualTo(produto.getId());
        assertThat(produtoRetornado.get().getNome()).isEqualTo(produto.getNome());
        assertThat(produtoRetornado.get().getPreco()).isEqualTo(produto.getPreco());
    }

}
