package com.restaurante.softexpert.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurante.softexpert.entities.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDomain implements Serializable {
	

	private static final long serialVersionUID = 3327096236892491428L;

	private Instant moment;
	
	private Double desconto;
	
	private Double taxaEntrega;
	
	private Long codigoPedido;
	
	private Double valorTotalPedido;
	
	private Double taxaServico;
	
	private Boolean pedidoPresencial;
	
	private List<ItemPedidoDomain> itensPedidoDominio = new ArrayList<>();

	public PedidoDomain(Instant moment, Double desconto, Double taxaEntrega, Double taxaServico, Boolean pedidoPresencial,
			List<ItemPedidoDomain> itensPedidoDominio) {
		super();
		this.moment = moment;
		this.desconto = desconto;
		this.taxaEntrega = taxaEntrega;
		this.taxaServico = taxaServico;
		this.pedidoPresencial = pedidoPresencial;
		this.itensPedidoDominio = itensPedidoDominio;
	}
	
	@JsonIgnore
	public boolean isEmptyItensPedido() {
		if(getItensPedidoDominio() != null && !getItensPedidoDominio().isEmpty()) {
			return false;
		}
		return true;
	}
	
	@JsonIgnore
	public Double calculaValorTotalPedido() {
		Double valorTotalPedidos = 0.0;
		if(!isEmptyItensPedido()) {
			for(ItemPedidoDomain itemPedido : getItensPedidoDominio()) {
				valorTotalPedidos += itemPedido.getQuantity() * itemPedido.getProdutoDominio().getPrice();
			}
		}
		setValorTotalPedido(valorTotalPedidos);
		return valorTotalPedidos;
	}
	
	@JsonIgnore
	public Pedido getEntityPedido() {
		if(Objects.isNull(getDesconto())) {
			setDesconto(0.0);
		}
		if(Objects.isNull(getTaxaEntrega())) {
			setTaxaEntrega(0.0);
		}
		if(Objects.isNull(getTaxaServico())) {
			setTaxaServico(0.0);
		}
		return new Pedido(null, getMoment(), getDesconto(), getTaxaEntrega(), calculaValorTotalPedido(), 
				getTaxaServico(), getPedidoPresencial());
	}
	
	@JsonIgnore
	public static PedidoDomain getPedidoDomainFromEntityPedido(Pedido pedido) {
		if(!Objects.isNull(pedido)) {
			return new PedidoDomain(pedido.getMoment(), pedido.getDesconto(), pedido.getTaxaEntrega(),
					pedido.getId(), pedido.getValorTotalPedido(), pedido.getTaxaServico(), pedido.getPedidoPresencial(),
					     ItemPedidoDomain.getListItemPedidoDomainFromListItemPedido(pedido.getItems()));
		}
		return new PedidoDomain();
	}
	
}
