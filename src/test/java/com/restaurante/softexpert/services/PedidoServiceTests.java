package com.restaurante.softexpert.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.restaurante.softexpert.controllers.response.PedidoResponseConsultarDeterminadoPedido;
import com.restaurante.softexpert.controllers.response.PedidoResponseConsultarTodosPedidos;
import com.restaurante.softexpert.controllers.response.PedidoResponseCreatePedido;
import com.restaurante.softexpert.controllers.response.PedidoResponseExcluirDeterminadoPedido;
import com.restaurante.softexpert.domain.ClienteValorContaPorcentagem;
import com.restaurante.softexpert.domain.ItemPedidoDomain;
import com.restaurante.softexpert.domain.PedidoDomain;
import com.restaurante.softexpert.domain.ProdutoDomain;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
class PedidoServiceTests {

	@Mock
	private PedidoService pedidoServiceMock;
	
	@Autowired
	private PedidoService pedidoService;
	
	private final String CLIENTE_ANTONIO = "Antonio";
	private final String CLIENTE_FELIPE = "Felipe";
	
	
	@Test
	public void insertPedidoTest() {
		List<ItemPedidoDomain> itensPedidoDominio = getListaItensPedido();
			
		PedidoDomain pedidoDominio = new PedidoDomain(Instant.now(), 20.0, 8.0, 0.0, Boolean.FALSE, itensPedidoDominio);
		PedidoResponseCreatePedido pedidoResponse = pedidoService.insertPedido(pedidoDominio);
		
		assertNotNull("Objeto de Resposta do Pedido está NULLO", pedidoResponse);
		assertNotNull("Codigo do Pedido NULLO", pedidoResponse.getCodigoPedido());
		assertEquals("Valor Total do Pedido Incorreto", 50.0, pedidoResponse.getValorTotal(), 0);
		assertFalse("Não foram Calculadas as contas dos Clientes", 
				Objects.isNull(pedidoResponse.getListaClientesComValorConta()));
		
		for(ClienteValorContaPorcentagem clienteValor : pedidoResponse.getListaClientesComValorConta()) {
			assertNotNull("Nome do Cliente está NULLO", clienteValor.getCliente());
			assertNotNull("Valor Total da Conta do Cliente NULLO", clienteValor.getValorContaFinal());
			if(clienteValor.getCliente().equals(CLIENTE_ANTONIO)) {
				assertEquals("Valor Total do Cliente: " + CLIENTE_ANTONIO +" Incorreto.", 31.92, clienteValor.getValorContaFinal(), 0);
			} else {
				assertEquals("Valor Total do Cliente: " + CLIENTE_FELIPE +" Incorreto.", 6.08, clienteValor.getValorContaFinal(), 0);
			}			
		}
	}
	
	@Test
	public void getDeterminadoPedidoTest() {
		PedidoResponseConsultarDeterminadoPedido pedidoResponse = pedidoService.getDeterminadoPedido(1L);
		PedidoDomain pedido = pedidoResponse.getPedidoDominio();
		assertNotNull("Pedido pesquisado está NULLO", pedido);
		
		verificacaoCamposPedidoDomain(pedido);
		
		assertFalse("Não foi calculado os valores do Pedido do Cliente", 
				Objects.isNull(pedido.getItensPedidoDominio()));
		
		for(ItemPedidoDomain itemPedido : pedido.getItensPedidoDominio()) {
			verificacaoCamposItemPedido(itemPedido);
		}
	}
	
	@Test
	public void getTodosPedidosCadastradosTest() {
		PedidoResponseConsultarTodosPedidos pedidoResponse = pedidoService.getTodosPedidosCadastrados();
		assertNotNull("Resposta da Consulta por todos pedidos Cadastrados está NULLA", pedidoResponse);
		
		for(PedidoDomain pedidoDomain : pedidoResponse.getListPedidosDomain()) {
			assertNotNull("Código do Pedido está NULLO", pedidoDomain.getCodigoPedido());	
		}
	}
	
	@Test
	public void deleteDeterminadoPedidoTest() {
		PedidoResponseExcluirDeterminadoPedido pedidoResponse = pedidoService.deleteDeterminadoPedido(1L);
		assertNotNull("Resposta da Exclusão de determinado pedido está NULLA", pedidoResponse);
		
		PedidoDomain pedido = pedidoResponse.getPedidoDominio();
		
		verificacaoCamposPedidoDomain(pedido);
		
		assertFalse("Não foi calculado os valores do Pedido do Cliente", 
				Objects.isNull(pedido.getItensPedidoDominio()));
				
		for(ItemPedidoDomain itemPedido : pedido.getItensPedidoDominio()) {
			verificacaoCamposItemPedido(itemPedido);	
		}	
	}
	

	
	/**
	 * Métodos de suporte para os Testes.
	 * 
	 */
	
	private List<ItemPedidoDomain> getListaItensPedido() {
		List<ItemPedidoDomain> listaItensPedidoDominio = new ArrayList<>();
		listaItensPedidoDominio.add(new ItemPedidoDomain(new ProdutoDomain(1L), 1, CLIENTE_ANTONIO));
		listaItensPedidoDominio.add(new ItemPedidoDomain(new ProdutoDomain(2L), 1, CLIENTE_ANTONIO));
		listaItensPedidoDominio.add(new ItemPedidoDomain(new ProdutoDomain(3L), 1, CLIENTE_FELIPE));
		return listaItensPedidoDominio;
	}
	
	private void verificacaoCamposItemPedido(ItemPedidoDomain itemPedido) {
		assertNotNull("Produto do Pedido está NULLO", itemPedido.getProdutoDominio());
		assertNotNull("Código do Produto está NULLO", itemPedido.getProdutoDominio().getId());
		assertNotNull("Nome do Produto está NULLO", itemPedido.getProdutoDominio().getName());
		assertNotNull("Descrição do Produto está NULLA", itemPedido.getProdutoDominio().getDescription());
		assertNotNull("Preço do Produto está NULLO", itemPedido.getProdutoDominio().getPrice());
		assertNotNull("Quantidade do Pedido NULLA", itemPedido.getQuantity());
		assertNotNull("Cliente do Pedido está NULLO", itemPedido.getCliente());	
	}
	
	private void verificacaoCamposPedidoDomain(PedidoDomain pedido) {
		assertNotNull("A Data de Cadastro do Pedido está NULLA", pedido.getMoment());
		assertEquals("Valor da Taxa de Desconto, Incorreto", 20.0, pedido.getDesconto(), 0);
		assertEquals("Valor da Taxa de Entrega, Incorreto", 8.0, pedido.getTaxaEntrega(), 0);
		assertEquals("Valor do Codigo do Pedido, Incorreto", 1, pedido.getCodigoPedido(), 0);
		assertEquals("Valor Total do Pedido, Incorreto", 50.0, pedido.getValorTotalPedido(), 0);
		assertEquals("Valor da Taxa de Serviço, Incorreto", 0.0, pedido.getTaxaServico(), 0);
		assertEquals("Pedido Presencial, Incorreto", Boolean.FALSE, pedido.getPedidoPresencial());
	}
}