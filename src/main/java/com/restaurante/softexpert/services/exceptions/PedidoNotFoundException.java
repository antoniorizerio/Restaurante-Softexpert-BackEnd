package com.restaurante.softexpert.services.exceptions;


public class PedidoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PedidoNotFoundException(Object id) {
		super("Pedido = " + id + " não encontrado.");
	}
}
