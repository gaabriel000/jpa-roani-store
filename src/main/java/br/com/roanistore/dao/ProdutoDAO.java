package br.com.roanistore.dao;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
		if (nonNull(limite))
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
	
	public List<Produto> buscarComFiltro(
			String nome, 
			BigDecimal preco,
			LocalDate data)
	{
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
		Root<Produto> from = query.from(Produto.class);
		Predicate filtros = builder.and();
		
		if (isNotBlank(nome))
			builder.and(filtros, builder.equal(from.get("nome"), nome));
		
		if (nonNull(preco))
			builder.and(filtros, builder.equal(from.get("preco"), preco));
			
		if (nonNull(data))
			builder.and(filtros, builder.equal(from.get("dataCadastro"), data));
		
		query.where(filtros);
		return em.createQuery(query).getResultList();
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
