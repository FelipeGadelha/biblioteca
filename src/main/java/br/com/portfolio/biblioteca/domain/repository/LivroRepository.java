package br.com.portfolio.biblioteca.domain.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.portfolio.biblioteca.domain.entity.Livro;

@Repository
public interface LivroRepository extends CrudRepository<Livro, Long>{

	Optional<Livro> findByIsbn(String isbn);

}
