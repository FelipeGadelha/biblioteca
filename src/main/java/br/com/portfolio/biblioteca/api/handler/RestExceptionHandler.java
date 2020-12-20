package br.com.portfolio.biblioteca.api.handler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

//	@ExceptionHandler(BadRequestException.class)
//	public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre) {
//
//		return new ResponseEntity<>(
//				BadRequestExceptionDetails.builder()
//					.timestamp(OffsetDateTime.now())
//					.status(HttpStatus.BAD_REQUEST.value())
//					.title("Bad Request Exception")
//					.details(bre.getMessage())
//					.developerMessage(bre.getClass().getName())
//					.build(),
//				HttpStatus.BAD_REQUEST);
//	}
	
	
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<BadRequestExceptionDetails> handleIllegalArgumentException(IllegalArgumentException exception) {

		return new ResponseEntity<>(
				BadRequestExceptionDetails.builder()
					.timestamp(OffsetDateTime.now())
					.status(HttpStatus.BAD_REQUEST.value())
					.title("Bad Request Exception, Check the Documentation")
					.details(exception.getMessage())
					.developerMessage(exception.getClass().getName())
					.build(),
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<BadRequestExceptionDetails> handleIllegalStateException(IllegalStateException exception) {

		return new ResponseEntity<>(
				BadRequestExceptionDetails.builder()
					.timestamp(OffsetDateTime.now())
					.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.title("Bad Request Exception, Check the Documentation")
					.details(exception.getMessage())
					.developerMessage(exception.getClass().getName())
					.build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<FieldError> field = exception.getBindingResult().getFieldErrors();

		Map<String, Set<String>> map = field.stream().collect(Collectors.groupingBy(FieldError::getField,
				Collectors.mapping(FieldError::getDefaultMessage, Collectors.toSet())));

		return new ResponseEntity<>(ValidationExceptionDetails.builder()
				.timestamp(OffsetDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.title("Bad Request Exception")
				.details("Check the error field(s)")
				.developerMessage(exception.getClass().getName())
				.fieldError(map)
				.build(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

		System.out.println(status.name());
//		InvalidDataAccessApiUsageException
		return new ResponseEntity<>(ExceptionDetails.builder()
				.timestamp(OffsetDateTime.now())
				.status(status.value())
				.title(ex.getCause().getMessage())
				.details(ex.getMessage())
				.developerMessage(ex.getClass().getName())
				.build(), headers, status);
	}

}
