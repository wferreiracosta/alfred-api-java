package br.com.wferreiracosta.alfred.repositories;

import br.com.wferreiracosta.alfred.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
