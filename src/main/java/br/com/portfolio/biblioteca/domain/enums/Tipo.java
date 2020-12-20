package br.com.portfolio.biblioteca.domain.enums;

import br.com.portfolio.biblioteca.domain.entity.Usuario;

public enum Tipo {
	LIVRE {
		@Override
		public boolean aceita(Usuario usuario) {
			return true;
		}
	}, RESTRITO {
		@Override
		public boolean aceita(Usuario usuario) {
			return usuario.tipo(TipoUsuario.PESQUISADOR);
		}
	};

	public abstract boolean aceita(Usuario usuario);

}
