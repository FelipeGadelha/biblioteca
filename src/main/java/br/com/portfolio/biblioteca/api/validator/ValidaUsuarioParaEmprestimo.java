package br.com.portfolio.biblioteca.api.validator;

import org.springframework.validation.Errors;

import br.com.portfolio.biblioteca.api.dto.request.EmprestimoRq;
import br.com.portfolio.biblioteca.domain.entity.Usuario;

public class ValidaUsuarioParaEmprestimo {

	public static void valida(Usuario usuario, EmprestimoRq emprestimoRq, Errors errors) {

		if (!usuario.tempoEmprestimoValido(emprestimoRq)) {
			errors.reject("Tempo", "Necessário definir tempo de emprestimo");
		}
		
		if (!usuario.aindaPodeSolicitarEmprestimo()) {
			errors.reject("Usuário", "Você chegou no limite dos empréstimos, este limite é 5.");
		}
	}

}
