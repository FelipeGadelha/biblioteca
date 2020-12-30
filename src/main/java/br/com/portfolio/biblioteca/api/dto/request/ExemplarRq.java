package br.com.portfolio.biblioteca.api.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import br.com.portfolio.biblioteca.domain.entity.Exemplar;
import br.com.portfolio.biblioteca.domain.entity.Livro;
import br.com.portfolio.biblioteca.domain.enums.Tipo;

public class ExemplarRq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Tipo tipo;

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Exemplar toModel(Livro livro) {
		return new Exemplar(tipo, livro);
	}
	
	
}
