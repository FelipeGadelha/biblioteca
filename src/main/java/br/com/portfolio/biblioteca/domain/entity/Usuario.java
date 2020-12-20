package br.com.portfolio.biblioteca.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.modelmapper.internal.util.Assert;

import br.com.portfolio.biblioteca.api.dto.request.PedidoEmprestimoComTempo;
import br.com.portfolio.biblioteca.domain.enums.TipoUsuario;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private @NotNull TipoUsuario tipo;

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

	public boolean tipo(TipoUsuario tipoBuscado) {
		return this.tipo.equals(tipoBuscado);
	}

	public boolean tempoEmprestimoValido(PedidoEmprestimoComTempo pedido) {
		return tipo.aceitaTempoEmprestimo(pedido);
	}


	

}
