package br.com.roanistore.dao;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.roanistore.modelo.Cliente;

public class ClienteDAO extends GenericoDAO
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteDAO.class);
	
	public ClienteDAO(EntityManager em)
	{
		super(em);
	}
	
	public List<Cliente> buscarTodosLimitado(Integer limite)
	{
		if(nonNull(limite))
		{
			LOGGER.info("Buscando clientes. Limite de busca: {}", limite);
			String query = "SELECT c FROM Cliente c";
			return em.createQuery(query, Cliente.class)
					.setMaxResults(limite)
					.getResultList();
		}
		
		LOGGER.warn("Limite está nulo!");
		return new ArrayList<>();
	}
}
