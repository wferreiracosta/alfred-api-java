package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Produto;

import java.util.Optional;

public interface ProdutoService {

    public Optional<Produto> findById(Integer id);

}
