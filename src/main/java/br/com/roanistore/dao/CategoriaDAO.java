package br.com.roanistore.dao;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.roanistore.modelo.Categoria;

public class CategoriaDAO
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaDAO.class);
	
	private EntityManager em;

	public CategoriaDAO(EntityManager em)
	{
		this.em = em;
	}
	
	public void cadastrar(Categoria categoria)
	{
		if(nonNull(categoria))
		{
			LOGGER.info("Cadastrando categoria: {}", categoria.getNome());
			this.em.persist(categoria);
		}
		else
		{
			LOGGER.warn("Categoria não cadastrada! Objeto nulo.");
		}
	}
	
	public void cadastrarLista(List<Categoria> categorias)
	{
		if(isNotEmpty(categorias))
		{
			LOGGER.info("Cadastrando lista de categorias");
			categorias.forEach(this::cadastrar);
		}
		else
		{
			LOGGER.warn("Lista de categorias não cadastrada! Lista nula ou vazia.");
		}
	}
	
	public Categoria atualizar(Categoria categoria)
	{
		if(nonNull(categoria))
		{
			LOGGER.info("Retomando categoria para ser atualizada. Categoria: {}", categoria.getNome());
			return this.em.merge(categoria);
		}
		
		LOGGER.warn("categoria a ser atualizada está nula!");
		return null;
	}
	
	public void remover(Categoria categoria)
	{
		if(nonNull(categoria))
		{
			LOGGER.info("Categoria a ser removida: {}", categoria.getNome());
			categoria = this.em.merge(categoria);
			this.em.remove(categoria);
		}
		else
		{
			LOGGER.warn("Categoria nula! Não será removida.");
		}
	}
	
	public Categoria buscarPorId(Long id)
	{
		if(nonNull(id))
		{
			LOGGER.info("Buscando categoria pelo ID {}", id);
			return this.em.find(Categoria.class, id);
		}
		
		LOGGER.warn("ID passado está nulo.");
		return null;
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
