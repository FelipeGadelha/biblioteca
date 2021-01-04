package br.com.portfolio.biblioteca.api.validator;

import org.springframework.validation.Errors;

import br.com.portfolio.biblioteca.domain.entity.Livro;
import br.com.portfolio.biblioteca.domain.entity.Usuario;

public class ValidaLivroParaEmprestimo {

	public static void valida(Usuario usuario, Livro livro, Errors errors) {
		
		if (!livro.aceitaSerEmprestado(usuario)) {
			errors.reject("Usuário",  "Este usuário não pode pegar este livro");
		}
		
		if (!livro.estaDisponivelParaEmprestimo()) {
			errors.reject("Livro", "este livro não está disponível para emprestimo");
		}
	}
}
