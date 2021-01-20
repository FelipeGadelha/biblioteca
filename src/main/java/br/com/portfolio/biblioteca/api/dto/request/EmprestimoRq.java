package br.com.portfolio.biblioteca.api.dto.request;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;
import org.modelmapper.internal.util.Assert;

import br.com.portfolio.biblioteca.domain.entity.Emprestimo;
import br.com.portfolio.biblioteca.domain.entity.Livro;
import br.com.portfolio.biblioteca.domain.entity.Usuario;

public class EmprestimoRq implements PedidoEmprestimoComTempo {
	
	@NotNull
	@Positive
	private Long idUsuario;
	@NotNull
	@Positive
	private Long idLivro;
	@Range(min = 1, max = 60)
	private Integer tempo;
	
	@Deprecated
	public EmprestimoRq() {}
	
	public EmprestimoRq(@NotNull Long idUsuario, @NotNull Long idLivro) {
		super();
		this.idUsuario = idUsuario;
		this.idLivro = idLivro;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public Long getIdLivro() {
		return idLivro;
	}

	public Integer getTempo() {
		return tempo;
	}
	
	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public boolean temTempoEmprestimo() {
//		return Optional.ofNullable(tempo).isPresent();
		return Objects.nonNull(tempo);
	}

	public Emprestimo toModel(EntityManager manager) {
		Livro livro = manager.find(Livro.class, idLivro);
		Usuario usuario = manager.find(Usuario.class, idUsuario);
		
		Assert.state(Objects.nonNull(livro), "O livro não pode ser Nulo");
		Assert.state(Objects.nonNull(usuario), "O usuario não pode ser Nulo");
		Assert.state(usuario.tempoEmprestimoValido(this), "Você está tentando criar um emprestimo com o tempo não liberado para este usuario");
		
		
		int limiteMaximoDeTempoDeEmprestimo = 60;
		int tempoDefinido = Optional.ofNullable(tempo).orElse(limiteMaximoDeTempoDeEmprestimo);
//		int tempoDefinido = tempo == null ? limiteMaximoDeTempoDeEmprestimo : tempo;
		return usuario.criaEmprestimo(livro, tempoDefinido);
		
	}
	
	
	
	
}
