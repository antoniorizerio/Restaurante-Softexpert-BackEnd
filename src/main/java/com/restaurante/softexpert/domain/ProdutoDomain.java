package com.restaurante.softexpert.domain;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurante.softexpert.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDomain implements Serializable {

	private static final long serialVersionUID = -6823877430234969716L;
	
	private Long id;
	private String name;
	private String description;
	private Double price;
	
	public ProdutoDomain(Long id) {
		this.id = id;
	}
	
	public static ProdutoDomain getProdutoDomainFromEntityProduto(Produto produto) {
		if(!Objects.isNull(produto)) {
			return new ProdutoDomain(produto.getId(), produto.getName(), produto.getDescription(), 
					produto.getPrice());
		}
		return new ProdutoDomain();
	}
	
	public void configAttributes(Produto produto) {
		if(!Objects.isNull(produto)) {
			setId(produto.getId());
			setDescription(produto.getDescription());
			setName(produto.getName());
			setPrice(produto.getPrice());
		}
	}
	
	@JsonIgnore
	public Produto getEntityProduto() {
		return new Produto(getId(), getName(), getDescription(), getPrice());
	}
}
