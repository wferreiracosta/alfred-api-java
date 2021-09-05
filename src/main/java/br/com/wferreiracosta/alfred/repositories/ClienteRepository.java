package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
