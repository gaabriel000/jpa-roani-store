package br.com.roanistore.dao;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.roanistore.modelo.Produto;

public class ProdutoDAO extends GenericoDAO
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoDAO.class);
	
	public ProdutoDAO(EntityManager em)
	{
		super(em);
	}
	
	public List<Produto> buscarTodosLimitado(Integer limite)
	{
		if(nonNull(limite))
		{
			LOGGER.info("Buscando produtos. Limite de busca: {}", limite);
			String query = "SELECT c FROM Produto c";
			return em.createQuery(query, Produto.class)
					.setMaxResults(limite)
					.getResultList();
		}
		
		LOGGER.warn("Limite está nulo!");
		return new ArrayList<>();
	}
	
	public List<Produto> buscarPorNome(String nome)
	{
		if(isNotBlank(nome))
		{
			LOGGER.info("Buscando produto pelo nome: {}", nome);
			String query = "SELECT p FROM Produto p WHERE p.nome = :nome";
			return em.createQuery(query, Produto.class)
					.setParameter("nome", nome)
					.getResultList();
		}
		
		LOGGER.warn("Nome está nulo ou em branco");
		return new ArrayList<>();
	}
	
	public List<Produto> buscarPorCategoria(String categoria)
	{
		if(isNotBlank(categoria))
		{
			LOGGER.info("Buscando produtos pela categoria: {}", categoria);
			String query = "SELECT p FROM Produto p WHERE p.categoria.nome = :categoria";
			return em.createQuery(query, Produto.class)
					.setParameter("categoria", categoria)
					.getResultList();
		}
		
		LOGGER.warn("Nome da categoria está nulo ou em branco.");
		return new ArrayList<>();
	}
}
