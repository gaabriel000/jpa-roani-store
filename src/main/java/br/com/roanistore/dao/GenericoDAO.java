package br.com.roanistore.dao;

import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericoDAO
{
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericoDAO.class);
	
	protected EntityManager em;

	public GenericoDAO(EntityManager em)
	{
		this.em = em;
	}
	
	public void cadastrar(Object objeto)
	{
		if(nonNull(objeto))
		{
			this.em.persist(objeto);
		}
		else
		{
			LOGGER.warn("Objeto não cadastrado! Objeto nulo.");
		}
	}
	
	public <T> void cadastrarLista(List<T> objetos)
	{
		if(isNotEmpty(objetos))
		{
			objetos.forEach(this::cadastrar);
		}
		else
		{
			LOGGER.warn("Lista de não cadastrada! Lista nula ou vazia.");
		}
	}
	
	public Object atualizar(Object objeto)
	{
		if(nonNull(objeto))
		{
			return this.em.merge(objeto);
		}
		
		LOGGER.warn("Objeto não atualizado! Objeto nulo.");
		return null;
	}
	
	public void remover(Object objeto)
	{
		if(nonNull(objeto))
		{
			objeto = this.em.merge(objeto);
			this.em.remove(objeto);
		}
		else
		{
			LOGGER.warn("Objeto nulo! Nada será removido.");
		}
	}
	
	
	public Object buscarPorId(Class<?> classe, Long id)
	{
		if(nonNull(id))
		{
			LOGGER.info("Buscando pelo ID {}", id);
			return this.em.find(classe, id);
		}
		
		LOGGER.warn("ID passado está nulo.");
		return null;
	}
}
