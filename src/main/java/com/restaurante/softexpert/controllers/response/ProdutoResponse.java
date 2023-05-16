package com.restaurante.softexpert.controllers.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.restaurante.softexpert.domain.ProdutoDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponse implements Serializable {

	private static final long serialVersionUID = 8054599099027091225L;
	
	private List<ProdutoDomain> listaProdutosDominio = new ArrayList<>();
	
}