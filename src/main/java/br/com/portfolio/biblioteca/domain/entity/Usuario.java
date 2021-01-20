package br.com.portfolio.biblioteca.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.modelmapper.internal.util.Assert;

import br.com.portfolio.biblioteca.api.dto.request.PedidoEmprestimoComTempo;
import br.com.portfolio.biblioteca.domain.enums.TipoUsuario;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private @NotNull TipoUsuario tipo;
	@OneToMany(mappedBy = "usuario")
	private List<Emprestimo> emprestimos = new ArrayList<>();
	
	@Deprecated
	public Usuario() {}
	
	public Usuario(@NotNull TipoUsuario tipo) {
		this.tipo = tipo;
	}
	
	public Long getId() {
		Assert.state(id!=null, "Entidade ainda não foi persistida do banco de dados");
		return id;
	}
	
	public TipoUsuario getTipo() {
		return tipo;
	}

	public List<Emprestimo> getEmprestimos() {
		return emprestimos;
	}
	
	public boolean tipo(TipoUsuario tipoBuscado) {
		return this.tipo.equals(tipoBuscado);
	}

	public boolean tempoEmprestimoValido(PedidoEmprestimoComTempo pedido) {
		return tipo.aceitaTempoEmprestimo(pedido);
	}

	public boolean aindaPodeSolicitarEmprestimo() {
		long quantidadeEmprestimoNãoDevolvido = this.emprestimos
				.stream()
				.filter(emprestimo -> !emprestimo.foiDevolvido())
				.count();
		int limiteDeEmprestimo = 5;
		return quantidadeEmprestimoNãoDevolvido < limiteDeEmprestimo;
	}
	
	public Emprestimo criaEmprestimo(@NotNull @Valid Livro livro, @Positive int tempo) {

		Assert.isTrue(livro.aceitaSerEmprestado(this), 
				"Você esta tentando gerar um empréstimo de livro que não aceita ser emprestado para o usuário " 
						+ this.getId());
		Assert.state(livro.estaDisponivelParaEmprestimo(), "Você não pode criar empréstimo para um livro que não tem exemplar disponível");
		Assert.isTrue(this.aindaPodeSolicitarEmprestimo(), "O usuário não pode mais solicitar empréstimo");
		
		
		Optional<Exemplar> exemplarSelecionado = livro.buscaExemplarDisponivel(this);
		
		Assert.state(exemplarSelecionado.isPresent(),
				"Nesta altura do código a busca pelo exemplar do livro deveria retornar alguma opção");
		
		Exemplar exemplar = exemplarSelecionado.get();
		
		Assert.state(exemplar.disponivelParaEmprestimo(), "Não deve tentar criar um emprestimo para um exemplar indisponível");
		
		Emprestimo emprestimo = exemplar.criaEmprestimo(this, tempo);
		
		emprestimos.add(emprestimo);
		
		return emprestimo;
	}

}
