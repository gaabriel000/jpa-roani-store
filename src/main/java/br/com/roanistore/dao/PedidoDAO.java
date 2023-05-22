package br.com.roanistore.dao;

import static java.util.Objects.nonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.roanistore.helper.RelatorioVendaVO;
import br.com.roanistore.modelo.Pedido;

public class PedidoDAO extends GenericoDAO
{
	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoDAO.class);
	
	public PedidoDAO(EntityManager em)
	{
		super(em);
	}
	
	public List<Pedido> buscarTodosLimitado(Integer limite)
	{
		if(nonNull(limite))
		{
			LOGGER.info("Buscando pedidos. Limite de busca: {}", limite);
			String query = "SELECT p FROM Pedido p";
			return em.createQuery(query, Pedido.class)
					.setMaxResults(limite)
					.getResultList();
		}
		
		LOGGER.warn("Limite está nulo!");
		return new ArrayList<>();
	}
	
	public BigDecimal valorTotalVendido() 
	{
		String query = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return em.createQuery(query, BigDecimal.class)
				.getSingleResult();
	}
	
	public List<RelatorioVendaVO> relatorioDeVendas()
	{
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT new br.com.roanistore.helper.RelatorioVendaVO(");
		query.append("prd.nome, ");
		query.append("SUM(i.quantidade), ");
		query.append("MAX(p.data)) ");
		query.append("FROM Pedido p ");
		query.append("JOIN p.itensPedido i ");
		query.append("JOIN i.produto prd ");
		query.append("GROUP BY prd.nome, i.quantidade ");
		query.append("ORDER BY i.quantidade DESC ");
		
		return em.createQuery(query.toString(), RelatorioVendaVO.class)
				.getResultList();
	}
}
