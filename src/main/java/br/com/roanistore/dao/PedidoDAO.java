package br.com.roanistore.dao;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.roanistore.modelo.Pedido;

public class PedidoDAO extends GenericoDAO
{
	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoDAO.class);
	
	public PedidoDAO(EntityManager em)
	{
		super(em);
	}
	
	public List<Pedido> buscarTodosLimitado(Integer limite)
	{
		if(nonNull(limite))
		{
			LOGGER.info("Buscando pedidos. Limite de busca: {}", limite);
			String query = "SELECT c FROM Pedido c";
			return em.createQuery(query, Pedido.class)
					.setMaxResults(limite)
					.getResultList();
		}
		
		LOGGER.warn("Limite está nulo!");
		return new ArrayList<>();
	}
}
