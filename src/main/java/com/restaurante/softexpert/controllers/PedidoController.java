package com.restaurante.softexpert.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.restaurante.softexpert.controllers.request.CreatePedidoRequest;
import com.restaurante.softexpert.controllers.response.PedidoResponseConsultarDeterminadoPedido;
import com.restaurante.softexpert.controllers.response.PedidoResponseConsultarTodosPedidos;
import com.restaurante.softexpert.controllers.response.PedidoResponseCreatePedido;
import com.restaurante.softexpert.controllers.response.PedidoResponseExcluirDeterminadoPedido;
import com.restaurante.softexpert.services.PedidoService;


/**
 * Controlador responsável pelas requisições de Pedidos.
 * 
 * @author Antonio Rizério.
 *
 */
@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired // Injeção de Dependência //
	private PedidoService pedidoService;
	
	// EndPoint para recuperar todos os Pedidos cadastrados //
	@GetMapping
	public ResponseEntity<PedidoResponseConsultarTodosPedidos> findAll() {
		PedidoResponseConsultarTodosPedidos pedidoResponse = pedidoService.getTodosPedidosCadastrados();
		return ResponseEntity.ok().body(pedidoResponse);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<PedidoResponseConsultarDeterminadoPedido> findById(@PathVariable Long id) {
		PedidoResponseConsultarDeterminadoPedido pedidoResponse = pedidoService.getDeterminadoPedido(id);
		return ResponseEntity.ok().body(pedidoResponse);
	}
	
	@PostMapping
	public ResponseEntity<PedidoResponseCreatePedido> insert(@RequestBody CreatePedidoRequest createPedidoRequest){
		PedidoResponseCreatePedido pedidoCriado = pedidoService.insertPedido(createPedidoRequest.toPedidoDomain());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
					path("/{id}").buildAndExpand(pedidoCriado.getCodigoPedido()).toUri();
		return ResponseEntity.created(uri).body(pedidoCriado);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<PedidoResponseExcluirDeterminadoPedido> delete(@PathVariable Long id) {
		PedidoResponseExcluirDeterminadoPedido pedidoResponse = pedidoService.deleteDeterminadoPedido(id);
		return ResponseEntity.ok().body(pedidoResponse);
	}
}