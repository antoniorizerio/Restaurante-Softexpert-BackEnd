package com.restaurante.softexpert.controllers.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.restaurante.softexpert.services.exceptions.PedidoNotFoundException;
import com.restaurante.softexpert.services.exceptions.ValidateCamposInsercaoPedidoException;

import jakarta.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(PedidoNotFoundException.class)
	public ResponseEntity<StandardError> pedidoNotFound(PedidoNotFoundException error, 
			HttpServletRequest request) {
		
		String msgError = "Pedido não encontrado";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError standardError = new StandardError(Instant.now(), status.value(), msgError, 
				error.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(standardError);
	}
	
	@ExceptionHandler(ValidateCamposInsercaoPedidoException.class)
	public ResponseEntity<StandardError> validateCamposInsercaoPedido(ValidateCamposInsercaoPedidoException error, 
			HttpServletRequest request) {
		
		String msgError = "Faltando informação para inserção de um Pedido.";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError standardError = new StandardError(Instant.now(), status.value(), msgError, 
				error.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(standardError);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardError> validateCamposInsercaoPedido(Exception error, 
			HttpServletRequest request) {
		
		String msgError = "Problema no Sistema.";
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		StandardError standardError = new StandardError(Instant.now(), status.value(), msgError, 
				error.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(standardError);
	}

}
