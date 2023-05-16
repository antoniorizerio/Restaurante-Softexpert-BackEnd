package com.restaurante.softexpert.controllers.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.restaurante.softexpert.domain.ClienteValorContaPorcentagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseCreatePedido implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double valorTotal;
	
	private Long codigoPedido;
	
	private List<ClienteValorContaPorcentagem> listaClientesComValorConta = new ArrayList<>();

	public PedidoResponseCreatePedido(Long codigoPedido) {
		super();
		this.codigoPedido = codigoPedido;
	}
	
}
