package com.restaurante.softexpert.controllers.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.restaurante.softexpert.domain.PedidoDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseConsultarTodosPedidos implements Serializable {

	private static final long serialVersionUID = 3307412633180803410L;
	
	List<PedidoDomain> listPedidosDomain = new ArrayList<>();
}
