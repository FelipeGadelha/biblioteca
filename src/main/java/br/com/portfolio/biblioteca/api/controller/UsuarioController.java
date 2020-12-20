package br.com.portfolio.biblioteca.api.controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.portfolio.biblioteca.api.dto.request.UsuarioRq;
import br.com.portfolio.biblioteca.domain.entity.Usuario;

@RestController
@RequestMapping(value = "/v1/usuarios")
public class UsuarioController {

	@Autowired
	private EntityManager manager;
	
	@PostMapping
	@Transactional
	public Long save(@RequestBody @Valid UsuarioRq usuarioRq) {
		Usuario usuario = usuarioRq.toModel();
		manager.persist(usuario);
		return usuario.getId();
	}
	
}
