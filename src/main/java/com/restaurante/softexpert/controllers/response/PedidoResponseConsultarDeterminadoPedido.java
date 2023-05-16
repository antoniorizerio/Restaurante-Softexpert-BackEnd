package com.restaurante.softexpert.controllers.response;

import java.io.Serializable;

import com.restaurante.softexpert.domain.PedidoDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseConsultarDeterminadoPedido implements Serializable {

	private static final long serialVersionUID = 4173458877116527579L;
	
	private PedidoDomain pedidoDominio = new PedidoDomain();
}
