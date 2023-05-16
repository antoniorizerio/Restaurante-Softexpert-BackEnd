package com.restaurante.softexpert.controllers.response;

import java.io.Serializable;

import com.restaurante.softexpert.domain.PedidoDomain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseExcluirDeterminadoPedido implements Serializable {
	
	private static final long serialVersionUID = 6266438593880834844L;
	
	private PedidoDomain pedidoDominio = new PedidoDomain();
}
