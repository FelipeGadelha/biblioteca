package br.com.portfolio.biblioteca.domain.entity;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
	
	@OneToMany(mappedBy = "exemplar")
	@OrderBy("instanteEmprestimo asc")
	private SortedSet<Emprestimo> emprestimos = new TreeSet<>();
	
	@Version
	private int versao;

	@Deprecated
	public Exemplar() { }	
	
	public Exemplar(@NotNull Tipo tipo, @NotNull @Valid Livro livro) {
		this.tipo = tipo;
		this.livro = livro;
	}

	public Long getId() {
		Assert.state(Objects.nonNull(id), "Entidade ainda não foi persistida do banco de dados");
		return id;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public Livro getLivro() {
		return livro;
	}

	public boolean disponivelParaEmprestimo() {
		return this.emprestimos .isEmpty() 
				|| 
				this.emprestimos.stream().allMatch(emprestimo -> emprestimo.foiDevolvido());
	}

	public Emprestimo criaEmprestimo(@NotNull @Valid Usuario usuario, @Positive int tempo) {
		Assert.state(this.disponivelParaEmprestimo(), "Este exemplar não está disponível para empréstimo");
		Emprestimo emprestimo = new Emprestimo(usuario, this , tempo);
		boolean add = this.emprestimos.add(emprestimo);
		Assert.state(add, "A adição do empréstimo falhou para este exemplar. possível concorrência de exemplar");
		this.versao++;
		return emprestimo;
	}

	public Boolean disponivel(@NotNull @Valid Usuario usuario) {
		return this.getTipo().aceita(usuario) && this.disponivelParaEmprestimo();
	}

//	public boolean aceita(Usuario usuario) {
//		return this.tipo.aceita(usuario);
//	}
	

}
