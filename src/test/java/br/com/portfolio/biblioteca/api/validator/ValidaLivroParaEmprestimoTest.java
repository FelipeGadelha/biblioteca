package br.com.portfolio.biblioteca.api.validator;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import br.com.portfolio.biblioteca.domain.entity.Livro;
import br.com.portfolio.biblioteca.domain.entity.Usuario;
import br.com.portfolio.biblioteca.domain.enums.Tipo;
import br.com.portfolio.biblioteca.domain.enums.TipoUsuario;

class ValidaLivroParaEmprestimoTest {
	
	@Test
	@DisplayName("Deveria rejeitar o emprestimo caso o livro não possa ser emprestado")
	void test1() {
		ValidaLivroParaEmprestimo validador = new ValidaLivroParaEmprestimo();
		
		var livro = new Livro("titulo", BigDecimal.TEN, "897452386534");
		livro.novoExemplar(Tipo.RESTRITO);
		
		var usuario = new Usuario(TipoUsuario.PADRAO);
		Errors errors = Mockito.spy(new BeanPropertyBindingResult(new Object(), "target"));
		
		validador.valida(usuario, livro, errors);
		
		Assertions.assertEquals(1, errors.getErrorCount());
		Mockito.verify(errors, Mockito.never()).reject("Livro", "este livro não está disponível para emprestimo");
		
//		ex. com dados mockados
//		Livro livroMock = Mockito.mock(Livro.class);
//		Usuario usuarioMock = Mockito.mock(Usuario.class);
//		
//		Mockito.when(livroMock.aceitaSerEmprestado(usuario)).thenReturn(false);
	}
	
	@Test
	@DisplayName("Deveria rejeitar o emprestimo caso o livro não esteja disponivel para emprestimo")
	void test2() {
		var validador = new ValidaLivroParaEmprestimo();
		
		var livro = new Livro("titulo", BigDecimal.TEN, "897452386534");
		livro.novoExemplar(Tipo.LIVRE);
		var usuario = new Usuario(TipoUsuario.PADRAO);
		
		ReflectionTestUtils.setField(usuario, "id", 1l);
		
		usuario.criaEmprestimo(livro, 60);
		
		Errors errors = Mockito.spy(new BeanPropertyBindingResult(new Object(), "target"));
		
		validador.valida(usuario, livro, errors);
		
		Assertions.assertEquals(1, errors.getErrorCount());
		Mockito.verify(errors, Mockito.never()).reject("Usuário",  "Este usuário não pode pegar este livro");
	}

}
