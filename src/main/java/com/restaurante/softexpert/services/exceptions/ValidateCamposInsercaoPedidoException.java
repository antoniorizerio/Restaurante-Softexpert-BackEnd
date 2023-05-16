package com.restaurante.softexpert.services.exceptions;

public class ValidateCamposInsercaoPedidoException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public ValidateCamposInsercaoPedidoException(String conteudo) {
		super(conteudo);
	}
}
