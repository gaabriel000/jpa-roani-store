package br.com.roanistore.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.roanistore.dao.CategoriaDAO;
import br.com.roanistore.dao.ProdutoDAO;
import br.com.roanistore.modelo.Categoria;
import br.com.roanistore.modelo.Produto;

public class SimulacaoCadastroUtil
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SimulacaoCadastroUtil.class);
	
	private SimulacaoCadastroUtil()
	{
		throw new UnsupportedOperationException("Classe utilitária");
	}

	public static void testesDeProduto(
			EntityManager em, 
			ProdutoDAO produtoDAO, 
			List<Categoria> categorias, 
			List<Produto> produtos)
	{
		LOGGER.info("------------- Iniciando persistência dos produtos -------------");
		produtoDAO.cadastrarLista(produtos);
		LOGGER.info("Produtos persistidos com sucesso!");
		em.flush();
		
		LOGGER.info("------------- Listando produtos -------------");
		List<Produto> produtosBuscados = produtoDAO.buscarTodosLimitado(50);
		produtosBuscados.forEach(p -> LOGGER.info(p.getNome()));
		
		LOGGER.info("------------- Listando produtos por categoria -------------");
		categorias.forEach(c -> 
		{
			List<Produto> produtosCategoria = produtoDAO.buscarPorCategoria(c.getNome());
			produtosCategoria.forEach(p -> LOGGER.info("Produto: {}. Descrição: {}", p.getNome(), p.getDescricao()));
		});
		
		LOGGER.info("------------- Removendo produto -------------");
		Produto errado = new Produto("aiquefone", "aifone dez", new BigDecimal("9999"), categorias.get(0));
		produtoDAO.cadastrar(errado);
		em.flush();
		produtoDAO.remover(errado);
		
		LOGGER.info("------------- Buscando produto -------------");
		Long idBusca = produtosBuscados.get(0).getId();
		Produto produtoBuscaID = (Produto) produtoDAO.buscarPorId(Produto.class, idBusca);
		LOGGER.info("Produto com ID {}: {}", idBusca, produtoBuscaID.getNome());
		
		String nomeBusca = produtosBuscados.get(0).getNome();
		Produto produtoBuscaNome = produtoDAO.buscarPorNome(nomeBusca).get(0);
		LOGGER.info("ID do Produto com nome {}: {}", nomeBusca, produtoBuscaNome.getId());
	}

	public static void testesDeCategoria(
			EntityManager em, 
			CategoriaDAO categoriaDAO, 
			List<Categoria> categorias)
	{
		LOGGER.info("------------- Iniciando persistência das categorias -------------");
		categoriaDAO.cadastrarLista(categorias);
		LOGGER.info("Categorias persistidas com sucesso!");
		em.flush();
		
		LOGGER.info("------------- Listando categorias -------------");
		List<Categoria> categoriasBuscadas = categoriaDAO.buscarTodosLimitado(5);
		categoriasBuscadas.forEach(c -> LOGGER.info(c.getNome()));
		
		LOGGER.info("------------- Removendo categoria -------------");
		Categoria errada = new Categoria("livross");
		categoriaDAO.cadastrar(errada);
		em.flush();
		categoriaDAO.remover(errada);
		
		LOGGER.info("------------- Buscando categoria -------------");
		Long idBusca = categoriasBuscadas.get(0).getId();
		Categoria categoriaBuscaID = (Categoria) categoriaDAO.buscarPorId(Categoria.class, idBusca);
		LOGGER.info("Categoria com ID {}: {}", idBusca, categoriaBuscaID.getNome());
		
		String nomeBusca = categoriasBuscadas.get(0).getNome();
		Categoria categoriaBuscaNome = categoriaDAO.buscarPorNome(nomeBusca).get(0);
		LOGGER.info("ID da categoria com nome {}: {}", nomeBusca, categoriaBuscaNome.getId());
	}

	public static List<Produto> simulaProdutos(List<Categoria> categorias)
	{
		List<Produto> produtos = new ArrayList<>();
		
		categorias.forEach(c -> 
		{
			switch (c.getNome())
			{
			case "Celular":
				produtos.add(new Produto("Xiaomi Redmi9", "Redmi 2020 4GB RAM, 256 GB, 8 Core 1.9GHz, Dual SIM", new BigDecimal("1999"), c));
				produtos.add(new Produto("iPhone 13", "iPhone 13 2021 6GB RAM, 128 GB, 4 Core", new BigDecimal("5999"), c));
				break;
			case "Informática":
				produtos.add(new Produto("RAM 8GB KINGSTONE", "Memória RAM 8GB DDR4 KINGSTONE", new BigDecimal("299"), c));
				produtos.add(new Produto("Mouse Vertical VINIK", "Mouse Vertical Ortopédico VINIK", new BigDecimal("99"), c));
				produtos.add(new Produto("Estabilizador Bright", "Estabilizador 600 VA, Bivolt", new BigDecimal("199"), c));
				break;
			case "Livro":
				produtos.add(new Produto("Duna Completo", "Duna Completo - Frank Herbert", new BigDecimal("69"), c));
				produtos.add(new Produto("Guia do mochileiro das galaxias", "Guia do mochileiro das galaxias - Douglas Adams", new BigDecimal("39"), c));
				produtos.add(new Produto("O Senhor dos Anéis", "O Senhor dos Anéis - J. R. R. Tolkien", new BigDecimal("59"), c));
				break;
			default:
				break;
			}
		});
		
		return produtos;
	}

	public static List<Categoria> simulaCategorias()
	{
		List<Categoria> categorias = new ArrayList<>();
		List<String> categoriasNome = Arrays.asList("Celular", "Informática", "Livro");
		categoriasNome.forEach(c -> categorias.add(new Categoria(c)));
		return categorias;
	}
}
