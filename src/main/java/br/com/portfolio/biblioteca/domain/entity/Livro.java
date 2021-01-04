package br.com.portfolio.biblioteca.domain.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.ISBN.Type;
import org.springframework.util.Assert;

@Entity
public class Livro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotBlank String titulo;
	private @NotNull @Positive BigDecimal preco;
	private @NotBlank @ISBN(type = Type.ISBN_10) String isbn;
	@OneToMany(mappedBy = "livro")
	private List<Exemplar> exemplares = new ArrayList<>();

	@Deprecated
	public Livro() {}
	
	public Livro(@NotBlank String titulo, @NotNull @Positive BigDecimal preco,
			@NotBlank @ISBN(type = Type.ISBN_10) String isbn) {
				this.titulo = titulo;
				this.preco = preco;
				this.isbn = isbn;
	}

	public Long getId() {
		Assert.state(id!=null, "Entidade ainda não foi persistida do banco de dados");
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public boolean aceitaSerEmprestado(Usuario usuario) {
		return exemplares.stream().anyMatch(exemplar -> exemplar.getTipo().aceita(usuario));
	}

	public Emprestimo criaEmprestimo(@NotNull @Valid Usuario usuario, @Positive int tempo) {

		Assert.isTrue(this.aceitaSerEmprestado(usuario), 
				"Você esta tentando gerar um empréstimo de livro que não aceita ser emprestado para o usuário " 
						+ usuario.getId());
		Assert.state(this.estaDisponivelParaEmprestimo(), "Você não pode criar empréstimo para um livro que não tem exemplar disponível");
		Assert.isTrue(usuario.aindaPodeSolicitarEmprestimo(), "O usuário não pode mais solicitar empréstimo");
		
		Exemplar exemplarSelecionado = exemplares.stream()
		.filter(exemplar -> exemplar.disponivel(usuario))
		.findFirst().get();
		
		Assert.state(exemplarSelecionado.disponivelParaEmprestimo(), "Não deve tentar criar um emprestimo para um exemplar indisponível");
		
		return exemplarSelecionado.criaEmprestimo(usuario, tempo);
	}

	public boolean estaDisponivelParaEmprestimo() {
		return exemplares.stream().anyMatch(exemplar -> exemplar.disponivelParaEmprestimo());
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + ", preco=" + preco + ", isbn=" + isbn + ", exemplares="
				+ exemplares + "]";
	}
	
	
	
	

}
