package br.com.portfolio.biblioteca.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.portfolio.biblioteca.domain.enums.Tipo;
import br.com.portfolio.biblioteca.domain.enums.TipoUsuario;

public class ExemplarTest {
	
	@DisplayName("Deveria estar disponível para empréstimo se nunca foi emprestada")
	@Test
	void teste1() throws Exception {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Exemplar exemplar = new Exemplar(Tipo.LIVRE, livro);

		assertTrue(exemplar.disponivelParaEmprestimo());
	}
	
	@DisplayName("Deveria estar desponível para empréstimo se todos os empréstimos foram devolvidos")
	@Test
	void test2() {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Exemplar exemplar = new Exemplar(Tipo.LIVRE, livro);
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		Emprestimo emprestimo = exemplar.criaEmprestimo(usuario, 40);
		ReflectionTestUtils.setField(emprestimo, "instanteDevolucao", Instant.now());
		
		assertTrue(exemplar.disponivelParaEmprestimo());
		
		
	}
	
	@DisplayName("Não deveria estar desponível se tem empréstimo que ainda não foi devolvido")
	@Test
	void test3() {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Exemplar exemplar = new Exemplar(Tipo.LIVRE, livro);
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		exemplar.criaEmprestimo(usuario, 40);
		assertFalse(exemplar.disponivelParaEmprestimo());
		
	}
	
	@DisplayName("Deveria aceitar um usuario incompatível")
	@Test
	void test4() {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Exemplar exemplar = new Exemplar(Tipo.LIVRE, livro);
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		assertTrue(exemplar.disponivel(usuario));
		
	}
	
	@DisplayName("Deveria aceitar um usuario incompatível")
	@Test
	void test5() {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Exemplar exemplar = new Exemplar(Tipo.RESTRITO, livro);
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		assertFalse(exemplar.disponivel(usuario));
		
	}
	
	@DisplayName("Deveria aceitar um usuario compatível caso tenha empréstimo em aberto")
	@Test
	void test6() {
		Livro livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		Exemplar exemplar = new Exemplar(Tipo.LIVRE, livro);
		Usuario usuario = new Usuario(TipoUsuario.PADRAO);
		exemplar.criaEmprestimo(usuario, 40);
		assertFalse(exemplar.disponivel(usuario));
		
	}
}
