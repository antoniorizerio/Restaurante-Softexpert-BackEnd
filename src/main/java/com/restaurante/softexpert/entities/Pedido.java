package com.restaurante.softexpert.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entidade Pedido.
 * 
 * @author Antonio Rizério.
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_PEDIDO")
public class Pedido implements Serializable {

	private static final long serialVersionUID = -7459099486394238489L;

	// Estratégia para geração da chave primaria: IDENTITY //
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Instant moment;
	
	private Double desconto;
	
	private Double taxaEntrega;
	
	private Double valorTotalPedido;
	
	private Double taxaServico;
	
	private Boolean pedidoPresencial;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> items = new ArrayList<>();
	
	
	
	public Pedido(Long id, Instant moment, Double desconto, Double taxaEntrega, Double valorTotalPedido,
					Double taxaServico, Boolean pedidoPresencial) {
		super();
		this.id = id;
		this.moment = moment;
		this.desconto = desconto;
		this.taxaEntrega = taxaEntrega;
		this.valorTotalPedido = valorTotalPedido;
		this.taxaServico = taxaServico;
		this.pedidoPresencial = pedidoPresencial;
	}

	public Pedido(Long id) {
		super();
		this.id = id;
	}
}