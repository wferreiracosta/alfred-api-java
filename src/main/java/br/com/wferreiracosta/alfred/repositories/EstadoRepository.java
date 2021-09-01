package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
}
