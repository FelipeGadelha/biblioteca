package br.com.portfolio.biblioteca.api.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.ISBN.Type;

import br.com.portfolio.biblioteca.api.validator.UniqueValue;
import br.com.portfolio.biblioteca.domain.entity.Livro;


public class LivroRq implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String titulo;
	@NotNull
	@Positive
	private BigDecimal preco;
	@NotBlank
	@ISBN(type = Type.ISBN_10)
	@UniqueValue(domainClass = Livro.class, fieldName = "isbn", message = "já existe um ISBN com esse valor")
	private String isbn;
	
	@Deprecated
	private LivroRq() {}

	public LivroRq(@NotBlank String titulo, @NotNull @Positive BigDecimal preco,
			@NotBlank @ISBN(type = Type.ISBN_10) String isbn) {
		super();
		this.titulo = titulo;
		this.preco = preco;
		this.isbn = isbn;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public String getIsbn() {
		return isbn;
	}

	public Livro toModel() {
		return new Livro(titulo,preco,isbn);
	}
	
	
	
	
	

}
