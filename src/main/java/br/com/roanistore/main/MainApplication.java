package br.com.roanistore.main;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.roanistore.dao.CategoriaDAO;
import br.com.roanistore.dao.ProdutoDAO;
import br.com.roanistore.modelo.Categoria;
import br.com.roanistore.modelo.Produto;
import br.com.roanistore.util.JPAUtil;
import br.com.roanistore.util.SimulacaoCadastroUtil;
import br.com.roanistore.util.SimulacaoPedidoUtil;

public class MainApplication
{
	static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);
	
	public static void main(String[] args)
	{
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDAO = new ProdutoDAO(em);
		CategoriaDAO categoriaDAO = new CategoriaDAO(em);
		
		List<Categoria> categorias = SimulacaoCadastroUtil.simulaCategorias();
		List<Produto> produtos = SimulacaoCadastroUtil.simulaProdutos(categorias);
		
		em.getTransaction().begin();
		
		LOGGER.info("************* Cadastro de produtos e categorias *************");
		SimulacaoCadastroUtil.testesDeCategoria(em, categoriaDAO, categorias);
		LOGGER.info("*********************************************************************");
		SimulacaoCadastroUtil.testesDeProduto(em, produtoDAO, categorias, produtos);
		
		LOGGER.info("************* Simulação de clientes e pedidos *************");
		SimulacaoPedidoUtil.testeDePedido(em, produtoDAO);
		
		em.getTransaction().commit();
		em.close();
	}
}
