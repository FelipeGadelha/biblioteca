package br.com.portfolio.biblioteca.api.controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.portfolio.biblioteca.api.dto.request.EmprestimoRq;
import br.com.portfolio.biblioteca.api.validator.verificacaoBasicaEmprestimoValidator;
import br.com.portfolio.biblioteca.domain.entity.Emprestimo;

@RestController
@RequestMapping(value = "/v1/emprestimos")
public class EmprestimoController {
	
	
	@Autowired
	private EntityManager manager;
	
	@Autowired
	private verificacaoBasicaEmprestimoValidator verificacaoBasicaEmprestimoValidator;
	
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(verificacaoBasicaEmprestimoValidator);
	}
	
	@PostMapping
	@Transactional
	public Long save(@RequestBody @Valid EmprestimoRq emprestimoRq) {
		
		Emprestimo emprestimo = emprestimoRq.toModel(manager);
		manager.persist(emprestimo);
		return emprestimo.getId();
		
	}

}
