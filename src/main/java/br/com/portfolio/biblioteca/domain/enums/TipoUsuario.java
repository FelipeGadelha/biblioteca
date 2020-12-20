package br.com.portfolio.biblioteca.domain.enums;

import br.com.portfolio.biblioteca.api.dto.request.PedidoEmprestimoComTempo;

public enum TipoUsuario {
	PADRAO {
		@Override
		public boolean aceitaTempoEmprestimo(PedidoEmprestimoComTempo pedido) {
			return pedido.temTempoEmprestimo();
		}
	}, PESQUISADOR {
		@Override
		public boolean aceitaTempoEmprestimo(PedidoEmprestimoComTempo pedido) {
			return true;
		}
	};

	public abstract boolean aceitaTempoEmprestimo(PedidoEmprestimoComTempo pedido);
}
