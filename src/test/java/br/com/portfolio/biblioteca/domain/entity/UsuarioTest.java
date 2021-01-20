package br.com.portfolio.biblioteca.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.portfolio.biblioteca.domain.enums.Tipo;
import br.com.portfolio.biblioteca.domain.enums.TipoUsuario;

public class UsuarioTest {
	
	@DisplayName("Situações onde usuario pode ou não solicitar empréstimo")
	@ParameterizedTest
	@CsvSource({
		"4, true",
		"5, false",
		"2, true"
	})
	void teste1(int numeroEmprestimos, boolean resultadoEsperado) {
		var livro = new Livro("titulo", BigDecimal.TEN, "8748956375");
		//cria exemplares para serem emprestados
		for (int i = 0; i < 6; i++) {
			livro.novoExemplar(Tipo.LIVRE);
		}
		var usuario = new Usuario(TipoUsuario.PADRAO);
		ReflectionTestUtils.setField(usuario, "id", 1l);
		
		//cria 4 emprestimos, que é o valor mais próximo do 
		for (int i = 0; i < numeroEmprestimos; i++) {
			usuario.criaEmprestimo(livro, 40);
		}
		assertEquals(resultadoEsperado, usuario.aindaPodeSolicitarEmprestimo());
	}
	
	
	
}
