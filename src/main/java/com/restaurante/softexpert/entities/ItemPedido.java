package com.restaurante.softexpert.entities;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_ITEM_PEDIDO")
public class ItemPedido implements Serializable {
	
	private static final long serialVersionUID = -3721548444741735843L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer quantity;
	
	private String cliente;
	
	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
	
	public ItemPedido(Pedido pedido, Produto produto, Integer quantity, String cliente) {
		super();
		this.pedido = pedido;
		this.produto = produto;
		this.quantity = quantity;
		this.cliente = cliente;
	}
}

