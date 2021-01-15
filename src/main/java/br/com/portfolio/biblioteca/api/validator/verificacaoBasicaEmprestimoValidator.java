package br.com.portfolio.biblioteca.api.validator;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.portfolio.biblioteca.api.dto.request.EmprestimoRq;
import br.com.portfolio.biblioteca.domain.entity.Livro;
import br.com.portfolio.biblioteca.domain.entity.Usuario;

@Component
public class verificacaoBasicaEmprestimoValidator implements Validator{
	
	private final EntityManager manager;
	
	public verificacaoBasicaEmprestimoValidator(EntityManager manager) {
		super();
		this.manager = manager;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return EmprestimoRq.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (errors.hasErrors()) {
			return ;
		}
		
		EmprestimoRq emprestimoRq = (EmprestimoRq) target;
		Usuario usuario = manager.find(Usuario.class, emprestimoRq.getIdUsuario());
		Livro livro = manager.find(Livro.class, emprestimoRq.getIdLivro());
		
		Assert.state(usuario!=null, "O usuário tem que ser diferente de nulo para validar");
		Assert.state(livro!=null, "O livro tem que ser diferente de nulo para validar");
		
		new ValidaLivroParaEmprestimo().valida(usuario, livro, errors);
		ValidaUsuarioParaEmprestimo.valida(usuario, emprestimoRq, errors);
		
		
//		isbn
//		8535932879
//		8571838267
		
		
		
	}

}
