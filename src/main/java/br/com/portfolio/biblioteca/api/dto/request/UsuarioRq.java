package br.com.portfolio.biblioteca.api.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import br.com.portfolio.biblioteca.domain.entity.Usuario;
import br.com.portfolio.biblioteca.domain.enums.TipoUsuario;

public class UsuarioRq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private TipoUsuario tipo;
	
	@Deprecated
	public UsuarioRq() {}
	

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}
	
//	@JsonCreator(mode = Mode.PROPERTIES)
//	public UsuarioRq(@NotNull TipoUsuario tipo) {
//		this.tipo = tipo;
//	}
//
//	public TipoUsuario getTipo() {
//		return tipo;
//	}
	
	public Usuario toModel() {
		return new Usuario(tipo);
	}
	
	

}
