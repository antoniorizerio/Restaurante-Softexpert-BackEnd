package com.restaurante.softexpert.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import com.restaurante.softexpert.entities.Produto;
import com.restaurante.softexpert.repositories.ProdutoRepository;

@Configuration
public class ConfigApplication implements CommandLineRunner {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	@Override
	public void run(String... args) throws Exception {
		
		Produto produto1 = new Produto(null, "Hamburguer Picanha Gril", "Hamburguer chefe da casa, deliciosa Picanha.", 40.0);
		Produto produto2 = new Produto(null, "Milk Shake Flocos", "Deliciosa Sobremessa de sorvete: Milk Shake Flocos.", 2.0);
		Produto produto3 = new Produto(null, "Hamburguer Vegetariano com picles", "Para quem é vegetariano, temos o Hamburguer Vegetariano com picles.", 8.0);
		Produto produto4 = new Produto(null, "Hamburguer Bacon X", "Para quem gosta de bacon, temos o Hamburguer Bacon X.", 31.0);
		Produto produto5 = new Produto(null, "Salada Francesa", "Deliciosa Salada com Frango e Alface.", 39.90);
		Produto produto6 = new Produto(null, "Milk Shake Flocos", "Deliciosa Sobremessa de sorvete: Milk Shake Flocos.", 28.0);
		
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6));
	}
}