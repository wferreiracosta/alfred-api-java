package br.com.wferreiracosta.alfred.services.impl;

import br.com.wferreiracosta.alfred.models.Categoria;
import br.com.wferreiracosta.alfred.models.Produto;
import br.com.wferreiracosta.alfred.repositories.CategoriaRepository;
import br.com.wferreiracosta.alfred.repositories.ProdutoRepository;
import br.com.wferreiracosta.alfred.services.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Optional<Produto> findById(Integer id) {
        return this.produtoRepository.findById(id);
    }

    @Override
    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }

}
