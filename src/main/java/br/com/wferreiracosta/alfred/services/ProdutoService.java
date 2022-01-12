package br.com.wferreiracosta.alfred.services;

import br.com.wferreiracosta.alfred.models.Produto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {

    public Optional<Produto> findById(Integer id);

    Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction);
}
