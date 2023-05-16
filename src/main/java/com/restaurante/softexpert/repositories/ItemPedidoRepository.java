package com.restaurante.softexpert.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.restaurante.softexpert.entities.ItemPedido;

/**
 *  JpaRepository referente a Entidade ItemPedido.
 *  
 *  @author Antonio Rizério.
 *
 */

@Repository 
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

}
