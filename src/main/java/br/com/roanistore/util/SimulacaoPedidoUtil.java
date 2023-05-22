package br.com.roanistore.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.roanistore.dao.ClienteDAO;
import br.com.roanistore.dao.PedidoDAO;
import br.com.roanistore.dao.ProdutoDAO;
import br.com.roanistore.helper.RelatorioVendaVO;
import br.com.roanistore.modelo.Cliente;
import br.com.roanistore.modelo.ItemPedido;
import br.com.roanistore.modelo.Pedido;
import br.com.roanistore.modelo.Produto;

public class SimulacaoPedidoUtil
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SimulacaoPedidoUtil.class);
	private static final Random random = new Random();
	
	private SimulacaoPedidoUtil()
	{
		throw new UnsupportedOperationException("Classe utilitária");
	}
	
	public static void testeDePedido(EntityManager em, ProdutoDAO produtoDAO) 
	{
		ClienteDAO clienteDAO = new ClienteDAO(em);
		PedidoDAO pedidoDAO = new PedidoDAO(em);
		
		LOGGER.info("------------- Iniciando persistência dos clientes -------------");
		simulaCliente(clienteDAO);
		LOGGER.info("Clientes persistidos com sucesso!");
		em.flush();
		
		LOGGER.info("------------- Iniciando persistência dos pedidos -------------");
		simulaPedidos(produtoDAO, clienteDAO, pedidoDAO);
		LOGGER.info("Pedidos persistidos com sucesso!");
		em.flush();
		
		LOGGER.info("------------- Calculando valor total vendido -------------");
		BigDecimal valorVendido = pedidoDAO.valorTotalVendido();
		LOGGER.info("Valor de todos os pedidos: {}", valorVendido);
		
		LOGGER.info("------------- Relatório de vendas -------------");
		List<RelatorioVendaVO> relatorio = pedidoDAO.relatorioDeVendas();
		relatorio.forEach(r -> LOGGER.info("Nome do Item: {}, Quantidade {}, Data da Última venda: {}", r.getNomeProduto(), r.getQuantidadeVendida(), r.getDataUltimaVenda()));
	}

	private static void simulaPedidos(
			ProdutoDAO produtoDAO, 
			ClienteDAO clienteDAO,
			PedidoDAO pedidoDAO)
	{
		List<Pedido> pedidos = new ArrayList<>();
		List<Cliente> clientes = clienteDAO.buscarTodosLimitado(50);
		List<Produto> produtos = produtoDAO.buscarTodosLimitado(50);
		
		clientes.forEach(c -> 
		{
			Pedido p = new Pedido(c);
			for (int i = 0; i < aleatorio(6); i++)
			{
				p.adicionarItem(new ItemPedido(aleatorio(3), p, produtos.get(aleatorio(produtos.size() - 1))));
			}
			pedidos.add(p);
		});
		
		pedidoDAO.cadastrarLista(pedidos);
	}

	private static void simulaCliente(ClienteDAO clienteDAO)
	{
		List<Cliente> clientes = new ArrayList<>();
		
		clientes.add(new Cliente("Lorelai Gilmor", "66321529028"));
		clientes.add(new Cliente("Quincas Borba", "94197106025"));
		clientes.add(new Cliente("Gregor Samsa", "62710253070"));
		clientes.add(new Cliente("Blair Waldorf", "42789405050"));
		clientes.add(new Cliente("Sansa Stark", "72310350001"));
		clientes.add(new Cliente("Brás Cubas", "85222561011"));
		
		clienteDAO.cadastrarLista(clientes);
	}
	
	private static int aleatorio(int max) 
	{
		return random.nextInt(max);
	}
}
