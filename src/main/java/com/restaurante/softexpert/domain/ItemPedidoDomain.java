package com.restaurante.softexpert.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.restaurante.softexpert.entities.ItemPedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDomain implements Serializable {

	private static final long serialVersionUID = 6994775016611232233L;

	private ProdutoDomain produtoDominio = new ProdutoDomain();
	
	private Integer quantity;
	
	private String cliente;
	
	public static List<ItemPedidoDomain> getListItemPedidoDomainFromListItemPedido(List<ItemPedido> listItemPedido) {
		List<ItemPedidoDomain> listItemPedidoDomain = new ArrayList<>();
		if(!Objects.isNull(listItemPedido) && !listItemPedido.isEmpty()) {
			for(ItemPedido itemPedido : listItemPedido) {
				listItemPedidoDomain.add( new ItemPedidoDomain(
					ProdutoDomain.getProdutoDomainFromEntityProduto(itemPedido.getProduto()),
						itemPedido.getQuantity(), itemPedido.getCliente()));
			}
		}
		return listItemPedidoDomain;
	}

}
