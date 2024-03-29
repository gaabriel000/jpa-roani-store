package br.com.roanistore.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate data = LocalDate.now();
	
	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itensPedido = new ArrayList<>();
	
	public Pedido()
	{
	}

	public Pedido(Cliente cliente)
	{
		this.cliente = cliente;
	}
	
	public void adicionarItem(ItemPedido item)
	{
		item.setPedido(this);
		this.itensPedido.add(item);
		this.valorTotal = this.valorTotal.add(item.getValor());
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public LocalDate getData()
	{
		return data;
	}

	public void setData(LocalDate data)
	{
		this.data = data;
	}

	public BigDecimal getValor()
	{
		return valorTotal;
	}

	public void setValor(BigDecimal valorTotal)
	{
		this.valorTotal = valorTotal;
	}

	public Cliente getCliente()
	{
		return cliente;
	}

	public void setCliente(Cliente cliente)
	{
		this.cliente = cliente;
	}

	public List<ItemPedido> getItensPedido()
	{
		return itensPedido;
	}

	public void setItensPedido(List<ItemPedido> itensPedido)
	{
		this.itensPedido = itensPedido;
	}
}
