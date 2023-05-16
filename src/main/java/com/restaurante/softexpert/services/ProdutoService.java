package com.restaurante.softexpert.services;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurante.softexpert.controllers.response.ProdutoResponse;
import com.restaurante.softexpert.domain.ProdutoDomain;
import com.restaurante.softexpert.entities.Produto;
import com.restaurante.softexpert.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired 
	private ProdutoRepository produtoRepository;
	
	public List<ProdutoDomain> findAll() {
		List<Produto> listaProdutos = produtoRepository.findAll();
		List<ProdutoDomain> listaProdutosDominio = listaProdutos.stream().map(produto -> {
			return new ProdutoDomain(produto.getId(), produto.getName(), 
					produto.getDescription(), produto.getPrice());
		}).collect(Collectors.toList());

		return listaProdutosDominio; 
	}
	
	public ProdutoResponse findAllProdutosReponse() {
		List<ProdutoDomain> listaProdutosDominio = findAll();
		return new ProdutoResponse(listaProdutosDominio);
	}
}
