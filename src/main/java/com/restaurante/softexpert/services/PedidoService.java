package com.restaurante.softexpert.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurante.softexpert.controllers.response.PedidoResponseConsultarDeterminadoPedido;
import com.restaurante.softexpert.controllers.response.PedidoResponseConsultarTodosPedidos;
import com.restaurante.softexpert.controllers.response.PedidoResponseCreatePedido;
import com.restaurante.softexpert.controllers.response.PedidoResponseExcluirDeterminadoPedido;
import com.restaurante.softexpert.domain.ClienteValorContaPorcentagem;
import com.restaurante.softexpert.domain.ItemPedidoDomain;
import com.restaurante.softexpert.domain.PedidoDomain;
import com.restaurante.softexpert.entities.ItemPedido;
import com.restaurante.softexpert.entities.Pedido;
import com.restaurante.softexpert.entities.Produto;
import com.restaurante.softexpert.repositories.ItemPedidoRepository;
import com.restaurante.softexpert.repositories.PedidoRepository;
import com.restaurante.softexpert.repositories.ProdutoRepository;
import com.restaurante.softexpert.services.exceptions.PedidoNotFoundException;
import com.restaurante.softexpert.services.exceptions.ValidateCamposInsercaoPedidoException;

/**
 * Poderíamos utilizar @Component, mas foi utilizado @Service por ter uma 
 * semântica melhor, indicando que é um camada de negócio que estende @Component 
 * 
 * @author Antonio Rizério Amorim Júnior.
 *
 */

@Service 
public class PedidoService {
	
	// Injeção de dependência //
	@Autowired 
	private PedidoRepository pedidoRepository;
	
	// Injeção de dependência //
	@Autowired 
	private ProdutoRepository produtoRepository;
	
	// Injeção de dependência //
	@Autowired 
	private ItemPedidoRepository itemPedidoRepository;
	
		
	public PedidoResponseConsultarTodosPedidos getTodosPedidosCadastrados() {
		List<Pedido> listaPedidos = pedidoRepository.findAll();
		List<PedidoDomain> listaPedidoDomain = new ArrayList<>();
		for(Pedido pedido : listaPedidos) {
			listaPedidoDomain.add(PedidoDomain.getPedidoDomainFromEntityPedido(pedido));
		}
		return new PedidoResponseConsultarTodosPedidos(listaPedidoDomain);
	}
	
	public PedidoResponseConsultarDeterminadoPedido getDeterminadoPedido(Long id) throws PedidoNotFoundException {
		Optional<Pedido> optPedido = pedidoRepository.findById(id);
		Pedido pedido = optPedido.orElseThrow(() -> new PedidoNotFoundException(id));
		return new PedidoResponseConsultarDeterminadoPedido(PedidoDomain.getPedidoDomainFromEntityPedido(pedido));
	}
	
	public PedidoResponseCreatePedido insertPedido(PedidoDomain pedidoDominio) {
		if(!Objects.isNull(pedidoDominio)) {
			
			validadeCamposInsercaoPedido(pedidoDominio);
			configInfoProduto(pedidoDominio);
			
			Pedido pedido = pedidoDominio.getEntityPedido();
			
			pedidoRepository.save(pedido);
			pedidoDominio.setCodigoPedido(pedido.getId());
			
			if(!pedidoDominio.isEmptyItensPedido()) {
				insertItemPedido(pedidoDominio.getItensPedidoDominio(), pedido);
			}
			return calculoPedido(pedidoDominio);		
		}
		return null;
	}

	public PedidoResponseExcluirDeterminadoPedido deleteDeterminadoPedido(Long id) throws PedidoNotFoundException {
		Optional<Pedido> optPedidoExcluir = pedidoRepository.findById(id);
		Pedido pedidoExcluir = optPedidoExcluir.orElseThrow(() -> new PedidoNotFoundException(id));
		pedidoRepository.delete(pedidoExcluir);
		return new PedidoResponseExcluirDeterminadoPedido(PedidoDomain.getPedidoDomainFromEntityPedido(pedidoExcluir));
	}
	
	
	
	
	
	
	
	
	
	
	
	// Métodos Auxiliares //
	
	private void validadeCamposInsercaoPedido(PedidoDomain pedidoDominio) {
		StringBuffer msgErro = new StringBuffer();
		List<ItemPedidoDomain> listaItemPedidoDominio = pedidoDominio.getItensPedidoDominio();
		
		if(Objects.isNull(pedidoDominio.getPedidoPresencial())) {
			msgErro.append("Indicativo de Pedido Presencial ou não, Obrigatório!");
		} 			
		
		for(ItemPedidoDomain itemPedido : listaItemPedidoDominio) {
			if(Objects.isNull(itemPedido.getProdutoDominio().getId()) ||
					Objects.isNull(itemPedido.getCliente()) || 	
					Objects.isNull(itemPedido.getQuantity())) {
				msgErro.append("Favor preencher as Informações de Produto / Quantidade / Cliente do Pedido!");
				break;
			}				
		}
		if(!msgErro.isEmpty()) {
			throw new ValidateCamposInsercaoPedidoException(msgErro.toString());
		}
	}
	
	private Map<String, Set<ItemPedidoDomain>> configMapaClienteItemPedido(List<ItemPedidoDomain> listaItemPedido) {
		Map<String, Set<ItemPedidoDomain>> hashMapClienteItemPedido = new HashMap<>();	

		Set<String> listaNomesClientes = new HashSet<>();
		listaItemPedido.forEach(itemPedido -> {
			listaNomesClientes.add(itemPedido.getCliente());
		});

		if(!listaNomesClientes.isEmpty()) {
			listaNomesClientes.forEach(cliente -> {
				
				Set<ItemPedidoDomain> listaItemPedidoFiltrado = listaItemPedido.stream().filter
						(itemPedido -> itemPedido.getCliente().equalsIgnoreCase(cliente)).collect(Collectors.toSet());
				
				hashMapClienteItemPedido.put(cliente, listaItemPedidoFiltrado);
			});
		}
		return hashMapClienteItemPedido;
	}
	
	private List<ClienteValorContaPorcentagem> getListaClienteComContaFinal(PedidoDomain pedidoDominio,
				List<ClienteValorContaPorcentagem> listaClientesContaPorcentg) {

		List<ClienteValorContaPorcentagem> listaClientesConta = new ArrayList<>();
		if(pedidoDominio.getPedidoPresencial()) {
			
			for(ClienteValorContaPorcentagem clienteConta: listaClientesContaPorcentg) {
				clienteConta.calculoPedidoPresencial(pedidoDominio);
				listaClientesConta.add(clienteConta);
			}
		
		} else {
	
			for(ClienteValorContaPorcentagem clienteConta: listaClientesContaPorcentg) {
				clienteConta.calculoPedidoDelivery(pedidoDominio);
				listaClientesConta.add(clienteConta);
			}
		}
		
		return listaClientesConta;
	}
	
	private void insertItemPedido(List<ItemPedidoDomain> listaPedidosDominio, Pedido pedido) {
		listaPedidosDominio.forEach(itemPedidoDominio -> {
										
			ItemPedido itemPedido = new ItemPedido(pedido, itemPedidoDominio.getProdutoDominio().getEntityProduto(), 
					itemPedidoDominio.getQuantity(), itemPedidoDominio.getCliente());
			itemPedidoRepository.save(itemPedido);
		
		});
	}
	
	private PedidoResponseCreatePedido calculoPedido(PedidoDomain pedidoDominio) {
		PedidoResponseCreatePedido pedidoResponse = 
				new PedidoResponseCreatePedido(pedidoDominio.getCodigoPedido());
		List<ItemPedidoDomain> listaItemPedido = pedidoDominio.getItensPedidoDominio();
		
		if(!Objects.isNull(listaItemPedido) && !listaItemPedido.isEmpty()) {
			
			// Crio e inicializo um HashMap de clientes com seus Itens de Pedido //
			Map<String, Set<ItemPedidoDomain>> hashMapClienteItemPedido = configMapaClienteItemPedido(listaItemPedido);
			
			Double valorTotalPedidos = pedidoDominio.calculaValorTotalPedido();
			
			List<ClienteValorContaPorcentagem> listaClientesContaPorcentg = new ArrayList<>();
			
			Double contaCliente = 0.0;
			
			for (Map.Entry<String, Set<ItemPedidoDomain>> entry : hashMapClienteItemPedido.entrySet()) {
				Set<ItemPedidoDomain> listaDePedidos = entry.getValue();
				if(!listaDePedidos.isEmpty()) {
					
					for(ItemPedidoDomain itemPedido : listaDePedidos) {
						contaCliente += itemPedido.getQuantity() * itemPedido.getProdutoDominio().getPrice();
					}
					
					listaClientesContaPorcentg.add(new ClienteValorContaPorcentagem(entry.getKey(), contaCliente));
					contaCliente = 0.0;
				}				
			}
			
			pedidoResponse.setValorTotal(valorTotalPedidos);			
			pedidoResponse.setListaClientesComValorConta(getListaClienteComContaFinal(pedidoDominio, listaClientesContaPorcentg));
		}
		return pedidoResponse;
	}
	
	private void configInfoProduto(PedidoDomain pedidoDominio) {
		if(!pedidoDominio.isEmptyItensPedido()) {	
			pedidoDominio.getItensPedidoDominio().forEach(itemPedido -> {
				
				Produto produto = null;
				Optional<Produto> optProduto = produtoRepository.findById(itemPedido.getProdutoDominio().getId());
				
				if(optProduto.isPresent()) {
					produto = optProduto.get();
					
					if(!Objects.isNull(produto)) {
						itemPedido.getProdutoDominio().configAttributes(produto);														
					}
				}
			});
		}	
	}
}
