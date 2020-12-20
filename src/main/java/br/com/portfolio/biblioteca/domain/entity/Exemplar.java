package br.com.portfolio.biblioteca.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.internal.util.Assert;

import br.com.portfolio.biblioteca.domain.enums.Tipo;

@Entity
public class Exemplar { //exemplary
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotNull Tipo tipo;
	@ManyToOne
	private @NotNull @Valid Livro livro;

	@Deprecated
	public Exemplar() { }	
	
	public Exemplar(@NotNull Tipo tipo, @NotNull @Valid Livro livro) {
		this.tipo = tipo;
		this.livro = livro;
	}

	public Long getId() {
		Assert.state(id!=null, "Entidade ainda não foi persistida do banco de dados");
		return id;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public Livro getLivro() {
		return livro;
	}

//	public boolean aceita(Usuario usuario) {
//		return this.tipo.aceita(usuario);
//	}
	

}
