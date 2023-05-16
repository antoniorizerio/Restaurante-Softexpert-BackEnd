package com.restaurante.softexpert.controllers.request;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.restaurante.softexpert.domain.ItemPedidoDomain;
import com.restaurante.softexpert.domain.PedidoDomain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatePedidoRequest implements Serializable {
	
	private static final long serialVersionUID = 5664504554449150908L;

	private Double desconto;
	
	private Double taxaEntrega;
	
	private Double taxaServico;
	
	private Boolean pedidoPresencial;
	
	private List<ItemPedidoDomain> itemsPedidoDominio;
	
	public PedidoDomain toPedidoDomain() {
        return new PedidoDomain(Instant.now(), getDesconto(), 
        		getTaxaEntrega(), getTaxaServico(), getPedidoPresencial(), getItemsPedidoDominio());
    }
}