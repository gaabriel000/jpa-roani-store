package br.com.roanistore.modelo;

import javax.persistence.Entity;

@Entity
public class Livro extends Produto
{
	private String autor;
	private Integer numeroPaginas;
	
	public Livro()
	{
	}
	
	public Livro(String autor, Integer numeroPaginas)
	{
		super();
		this.autor = autor;
		this.numeroPaginas = numeroPaginas;
	}
	
	public String getAutor()
	{
		return autor;
	}
	public void setAutor(String autor)
	{
		this.autor = autor;
	}
	public Integer getNumeroPaginas()
	{
		return numeroPaginas;
	}
	public void setNumeroPaginas(Integer numeroPaginas)
	{
		this.numeroPaginas = numeroPaginas;
	}
	
	
}
