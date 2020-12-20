package br.com.portfolio.biblioteca.domain.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.modelmapper.internal.util.Assert;

@Entity
public class Emprestimo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private @NotNull @Valid Usuario usuario;
	@ManyToOne
	private @NotNull @Valid Exemplar exemplar;
	
	private @Positive int tempo;

	public Emprestimo(@NotNull @Valid Usuario usuario,@NotNull @Valid Exemplar exemplar, @Positive int tempo) {
		Assert.isTrue(exemplar.getTipo().aceita(usuario), "Você esta construindo um emprestimo com instancia que não aceita o usuario");
		this.usuario = usuario;
		this.exemplar = exemplar;
		this.tempo = tempo;

	}
	
	public Long getId() {
		Assert.state(Objects.nonNull(id), "Será que vc esqueceu de persistir o emprestimo");
		return id;
	}
	
	
	
	

}
