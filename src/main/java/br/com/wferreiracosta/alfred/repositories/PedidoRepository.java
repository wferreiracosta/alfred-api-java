package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
