package br.com.roanistore.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DadosPessoais
{
	@Column
	private String nome;

	@Column
	private String cpf;
	
	public DadosPessoais()
	{
	}
	
	public DadosPessoais(String nome, String cpf)
	{
		this.nome = nome;
		this.cpf = cpf;
	}

	public String getNome()
	{
		return nome;
	}

	public String getCpf()
	{
		return cpf;
	}
}
