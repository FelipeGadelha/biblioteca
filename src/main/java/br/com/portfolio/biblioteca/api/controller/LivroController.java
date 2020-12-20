package br.com.portfolio.biblioteca.api.controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.portfolio.biblioteca.api.dto.request.LivroRq;
import br.com.portfolio.biblioteca.domain.entity.Livro;

@RestController
@RequestMapping(value = "/v1/livros")
public class LivroController {
	
	@Autowired
	private EntityManager manager;

	@PostMapping
	@Transactional
	public Long save(@RequestBody @Valid LivroRq livroRq) {
		Livro livro = livroRq.toModel();
		manager.persist(livro);
		return livro.getId();
		
	}
	
	@GetMapping
	public String find() {
		return "fala dev";
	}
	
}
