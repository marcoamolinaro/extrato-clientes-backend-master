package com.db.extrato.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javassist.tools.rmi.ObjectNotFoundException;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({EmptyResultDataAccessException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), "Não há informações para esta pesquisa."), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		
	}
	
	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, new ErrorMessage(new Date(), HttpStatus.BAD_REQUEST.value(), "Não foi encontrado nenhum serviço para esta URI."), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		
	}
	
	@ExceptionHandler({DateTimeParseException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleDateTimeParseExceptionException(DateTimeParseException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, new ErrorMessage(new Date(), HttpStatus.BAD_REQUEST.value(),  "Insira a data referencia no formato AAAA-MM-YY."), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		
	}

	@ExceptionHandler({ObjectNotFoundException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), "Não foram encontradas informações para essa pesquisa."), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		
	}

	@ExceptionHandler({SQLIntegrityConstraintViolationException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleSQLIntegrityConstraintViolationExceptionException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, new ErrorMessage(new Date(), HttpStatus.NOT_FOUND.value(), "Existem campos obrigatórios não preenchidos"), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		
	}
	
	@ExceptionHandler({AutStatusException.class})
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ResponseEntity<Object> handleAutStatusExceptionException(AutStatusException ex, WebRequest request) {
		return handleExceptionInternal(ex, new ErrorMessage(new Date(), HttpStatus.NOT_ACCEPTABLE.value(),  ex.getMessage()), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}
	
	@ExceptionHandler({NotImplementedException.class})
	@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
	public ResponseEntity<Object> handleNotImplementedExceptionException(NotImplementedException ex, WebRequest request) {
		return handleExceptionInternal(ex, new ErrorMessage(new Date(), HttpStatus.NOT_IMPLEMENTED.value(), ex.getMessage()), new HttpHeaders(), HttpStatus.NOT_IMPLEMENTED, request);
	}
	
	@ExceptionHandler({GenericException.class})
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ResponseEntity<Object> handleGenericException(GenericException ex, WebRequest request) {
		
		return handleExceptionInternal(ex, new ErrorMessage(new Date(), HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage()), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);

	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
	  HttpStatus status, WebRequest request) {

	  List<String> errorList = ex
          .getBindingResult()
          .getFieldErrors()
          .stream()
          .map(fieldError -> fieldError.getDefaultMessage())
          .collect(Collectors.toList());
	  
      return handleExceptionInternal(ex, new ErrorMessage(new Date(), HttpStatus.NOT_ACCEPTABLE.value(), errorList.get(0)), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}
}
