package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
