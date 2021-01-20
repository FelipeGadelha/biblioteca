package br.com.portfolio.biblioteca.api.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import br.com.portfolio.biblioteca.api.dto.request.EmprestimoRq;
import br.com.portfolio.biblioteca.domain.entity.Livro;
import br.com.portfolio.biblioteca.domain.entity.Usuario;
import br.com.portfolio.biblioteca.domain.enums.Tipo;
import br.com.portfolio.biblioteca.domain.enums.TipoUsuario;

public class ValidaUsuarioParaEmprestimoTest {
	
	@Test
	@DisplayName("Deveria rejeitar o emprestimo caso o usuario padrão não tenha colocado o tempo de entrega")
	void test1() {
//		var validador = new ValidaUsuarioParaEmprestimo();
		
		var livro = new Livro("titulo", BigDecimal.TEN, "897452386534");
		ReflectionTestUtils.setField(livro, "id", 1l);
		livro.novoExemplar(Tipo.RESTRITO);		
		
		var usuario = new Usuario(TipoUsuario.PADRAO);
		ReflectionTestUtils.setField(usuario, "id", 1l);
		
		var emprestimoRq = new EmprestimoRq(usuario.getId(), livro.getId());
		
		Errors errors = Mockito.spy(new BeanPropertyBindingResult(new Object(), "target"));
		
		ValidaUsuarioParaEmprestimo.valida(usuario, emprestimoRq, errors);
		
		assertEquals(1, errors.getErrorCount());
		assertEquals("Necessário definir tempo de emprestimo", errors.getGlobalErrors().get(0).getDefaultMessage());
	}
	
	@DisplayName("Deveria validar o número máximo de emprestimos de um determinado usuário")
	@Test
	void test2() {

		var livro = new Livro("titulo", BigDecimal.TEN, "897452386534");
		ReflectionTestUtils.setField(livro, "id", 1l);
		
		//cria instancias para serem emprestadas
		for (int i = 0; i < 6; i++) {
			livro.novoExemplar(Tipo.LIVRE);		
		}
		
		var usuario = new Usuario(TipoUsuario.PADRAO);
		ReflectionTestUtils.setField(usuario, "id", 1l);
		
		//cria o máximo de empréstimos permitidos neste momento, 5.
		for (int i = 0; i < 5; i++) {
			usuario.criaEmprestimo(livro, 40);
		}
		
		var emprestimoRq = new EmprestimoRq(1l, 1l);
		emprestimoRq.setTempo(40);
		
		Errors errors = Mockito.spy(new BeanPropertyBindingResult(new Object(), "target"));
		
		ValidaUsuarioParaEmprestimo.valida(usuario, emprestimoRq, errors);
		System.out.println(errors.getErrorCount());
		errors.getGlobalErrors().stream().map(e -> e.getDefaultMessage()).forEach(System.out::println);
		
		assertEquals(1, errors.getErrorCount());
		assertEquals("Você chegou no limite dos empréstimos, este limite é 5.", errors.getGlobalErrors().get(0).getDefaultMessage());
	}
}
