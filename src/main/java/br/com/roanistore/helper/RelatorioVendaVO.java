package br.com.roanistore.helper;

import java.time.LocalDate;

public class RelatorioVendaVO
{
	private String nomeProduto;
	private Long quantidadeVendida;
	private LocalDate dataUltimaVenda;
	
	public RelatorioVendaVO(String nomeProduto, Long quantidadeVendida, LocalDate dataUltimaVenda)
	{
		super();
		this.nomeProduto = nomeProduto;
		this.quantidadeVendida = quantidadeVendida;
		this.dataUltimaVenda = dataUltimaVenda;
	}

	public String getNomeProduto()
	{
		return nomeProduto;
	}

	public Long getQuantidadeVendida()
	{
		return quantidadeVendida;
	}

	public LocalDate getDataUltimaVenda()
	{
		return dataUltimaVenda;
	}
}
