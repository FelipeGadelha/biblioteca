package br.com.portfolio.biblioteca.api.controller;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.portfolio.biblioteca.api.dto.request.ExemplarRq;
import br.com.portfolio.biblioteca.domain.entity.Exemplar;
import br.com.portfolio.biblioteca.domain.entity.Livro;
import br.com.portfolio.biblioteca.domain.repository.LivroRepository;

@RestController
@RequestMapping(value = "/v1")
public class ExemplarController {
	
	private final LivroRepository livroRepository;
	
	@Autowired
	private EntityManager manager;
	
	
	
	@Autowired
	public ExemplarController(LivroRepository livroRepository) {
		this.livroRepository = livroRepository;
	}

	@PostMapping(value = "/livro/{isbn}/exemplares")
	@Transactional
	public ResponseEntity<?> save(@PathVariable("isbn") String isbn, @RequestBody @Valid ExemplarRq exemplarRq) {
		Optional<Livro> livro = livroRepository.findByIsbn(isbn);
//		Assert.state(Objects.isNull(livro), "Este Livro não existe");
		return livro.map(entity -> {
				Exemplar exemplar = exemplarRq.toModel(entity);
				manager.persist(exemplar);
				return ResponseEntity.ok()
						//.cacheControl(CacheControl.maxAge(Duration.ofSeconds(30)))
						.body(exemplar.getId());				
		}).orElse(ResponseEntity.notFound().build());
	}
	
}
