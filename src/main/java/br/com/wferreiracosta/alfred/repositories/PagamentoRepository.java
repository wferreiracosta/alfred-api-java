package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
