package br.com.roanistore.dao;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.roanistore.modelo.Categoria;

public class CategoriaDAO extends GenericoDAO
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaDAO.class);
	
	public CategoriaDAO(EntityManager em)
	{
		super(em);
	}
	
	public List<Categoria> buscarTodosLimitado(Integer limite)
	{
		if (nonNull(limite))
		{
			LOGGER.info("Buscando categorias. Limite de busca: {}", limite);
			String query = "SELECT c FROM Categoria c";
			return em.createQuery(query, Categoria.class)
					.setMaxResults(limite)
					.getResultList();
		}
		
		LOGGER.warn("Limite está nulo!");
		return new ArrayList<>();
	}
	
	public List<Categoria> buscarPorNome(String nome)
	{
		if(isNotBlank(nome))
		{
			LOGGER.info("Buscando categoria pelo nome {}", nome);
			String query = "SELECT c FROM Categoria c WHERE c.nome = :nome";
			return em.createQuery(query, Categoria.class)
					.setParameter("nome", nome)
					.getResultList();
		}
		
		LOGGER.warn("Nome está nulo ou em branco");
		return new ArrayList<>();
	}
}
