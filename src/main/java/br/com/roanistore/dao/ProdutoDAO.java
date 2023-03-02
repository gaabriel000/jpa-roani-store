package br.com.roanistore.dao;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.roanistore.modelo.Produto;

public class ProdutoDAO
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoDAO.class);
	
	private EntityManager em;

	public ProdutoDAO(EntityManager em)
	{
		this.em = em;
	}
	
	public void cadastrar(Produto produto)
	{
		if(nonNull(produto)) {
			LOGGER.info("Cadastrando produto: {}", produto.getNome());
			this.em.persist(produto);
		}
		else
		{
			LOGGER.warn("Produto não cadastrado! Objeto nulo.");
		}
		
	}
	
	public void cadastrarLista(List<Produto> produtos)
	{
		if(isNotEmpty(produtos))
		{
			LOGGER.info("Cadastrando lista de produtos");
			produtos.forEach(this::cadastrar);
		}
		else
		{
			LOGGER.warn("Lista de produtos não cadastrada! Lista nula ou vazia.");
		}
	}
	
	public Produto atualizar(Produto produto)
	{
		if(nonNull(produto))
		{
			LOGGER.info("Retomando produto para ser atualizado. Produto: {}", produto.getNome());
			return this.em.merge(produto);
		}
		
		LOGGER.warn("categoria a ser atualizada está nula!");
		return null;
	}
	
	public void remover(Produto produto)
	{
		if(nonNull(produto))
		{
			LOGGER.info("Produto a ser removido: {}", produto.getNome());
			produto = this.em.merge(produto);
			this.em.remove(produto);
		}
		else
		{
			LOGGER.warn("Produto nulo! Não será removido.");
		}
	}
	
	public Produto buscarPorId(Long id)
	{
		if(nonNull(id))
		{
			LOGGER.info("Buscando produto pelo ID {}", id);
			return this.em.find(Produto.class, id);
		}
		
		LOGGER.warn("ID passado está nulo.");
		return null;
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
