package br.com.portfolio.biblioteca.api.dto.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import br.com.portfolio.biblioteca.domain.entity.Usuario;
import br.com.portfolio.biblioteca.domain.enums.TipoUsuario;

public class UsuarioRq {
	
	@NotNull
	private TipoUsuario tipo;
	
	@JsonCreator(mode = Mode.PROPERTIES)
	public UsuarioRq(@NotNull TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}
	
	public Usuario toModel() {
		return new Usuario(tipo);
	}
	
	

}
