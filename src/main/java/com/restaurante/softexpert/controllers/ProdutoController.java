package com.restaurante.softexpert.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.restaurante.softexpert.controllers.response.ProdutoResponse;
import com.restaurante.softexpert.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

	@Autowired // Injeção de Dependência //
	private ProdutoService produtoService;
	
	// EndPoint para recuperar todos os produtos cadastrados//
	@GetMapping
	public ResponseEntity<ProdutoResponse> findAll() {
		return ResponseEntity.ok().body(produtoService.findAllProdutosReponse());
	}
}
