package br.com.roanistore.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil
{
	private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("loja");
	
	private JPAUtil()
	{
		throw new IllegalStateException("Classe utilitária");
	}
	
	public static EntityManager getEntityManager()
	{
		return FACTORY.createEntityManager();
	}
}
