package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.models.Produto;
import br.com.wferreiracosta.alfred.repositories.ProdutoRepository;
import br.com.wferreiracosta.alfred.services.ProdutoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Optional<Produto> findById(Integer id) {
        return this.produtoRepository.findById(id);
    }

}
