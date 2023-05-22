package br.com.roanistore.dao;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
	
	public List<Categoria> buscarComFiltro(String nome)
	{
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Categoria> query = builder.createQuery(Categoria.class);
		Root<Categoria> from = query.from(Categoria.class);
		Predicate filtros = builder.and();
		
		if (isNotBlank(nome))
			builder.and(filtros, builder.equal(from.get("nome"), nome));
		
		query.where(filtros);
		return em.createQuery(query).getResultList();
	}
}
