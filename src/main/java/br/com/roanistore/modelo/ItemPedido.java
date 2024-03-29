package br.com.roanistore.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_pedido")
public class ItemPedido
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "preco_unitario")
	private BigDecimal precoUnitario;
	private Integer quantidade;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Pedido pedido;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Produto produto;

	
	public ItemPedido()
	{
	}
	
	public ItemPedido(Integer quantidade, Pedido pedido, Produto produto)
	{
		this.quantidade = quantidade;
		this.pedido = pedido;
		this.precoUnitario = produto.getPreco();
		this.produto = produto;
	}

	public BigDecimal getValor()
	{
		return precoUnitario.multiply(new BigDecimal(quantidade));
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public BigDecimal getPrecoUnitario()
	{
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario)
	{
		this.precoUnitario = precoUnitario;
	}

	public Integer getQuantidade()
	{
		return quantidade;
	}

	public void setQuantidade(Integer quantidade)
	{
		this.quantidade = quantidade;
	}

	public Pedido getPedido()
	{
		return pedido;
	}

	public void setPedido(Pedido pedido)
	{
		this.pedido = pedido;
	}

	public Produto getProduto()
	{
		return produto;
	}

	public void setProduto(Produto produto)
	{
		this.produto = produto;
	}
}
