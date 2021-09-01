package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
}
