package com.restaurante.softexpert.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa o Produto de certo pedido.
 * 
 * @author Antonio Rizério Amorim Júnior.
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_PRODUTO")
public class Produto implements Serializable {
	

	private static final long serialVersionUID = -4456243642613008460L;

	// Estratégia para geração da chave primaria: IDENTITY //
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	private Double price;
	
}
