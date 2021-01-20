package br.com.portfolio.biblioteca.api.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class VerificacaoBasicaEmprestimoValidatorTest {
	
	@DisplayName("Deveria interromper a validação já exista um erro anterior")
	@Test
	void teste1() {
		EntityManager manager = Mockito.mock(EntityManager.class);
		var validador = new VerificacaoBasicaEmprestimoValidator(manager);

		Object target = new Object();
		Errors errors = new BeanPropertyBindingResult(target, "target");
		errors.reject("codigoErro");

		validador.validate(target, errors);

		assertEquals(1, errors.getErrorCount());

	}

}
