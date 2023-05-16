package com.restaurante.softexpert.domain;

import java.io.Serializable;

import com.restaurante.softexpert.services.util.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteValorContaPorcentagem implements Serializable {

	private static final long serialVersionUID = -8283701718180355897L;
	
	private String cliente;
	private Double valorConta;
	private Double valorContaFinal;
	
	public ClienteValorContaPorcentagem(String cliente, Double valorConta) {
		super();
		this.cliente = cliente;
		this.valorConta = valorConta;
	}
	
	public void calculoPedidoDelivery(PedidoDomain pedidoDominio) {
		Double porcentagemCliente = (getValorConta()*100)/pedidoDominio.getValorTotalPedido();
		Double taxaEntrega = pedidoDominio.getTaxaEntrega()*(porcentagemCliente/100);
		Double desconto = pedidoDominio.getDesconto()*(porcentagemCliente/100);
		setValorContaFinal(Util.getDoubleFormatado(getValorConta() + taxaEntrega - desconto,2));
	}
	
	public void calculoPedidoPresencial(PedidoDomain pedidoDominio) {
		Double taxaServico = pedidoDominio.getTaxaServico()/100;
		Double valorContaFinal = (getValorConta()*taxaServico)+getValorConta();
		setValorContaFinal(Util.getDoubleFormatado(valorContaFinal,2));
	}
}
