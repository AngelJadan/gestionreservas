package ec.edu.ups.ejb;

import java.sql.SQLException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entity.Cliente;

@Stateless
public class ClienteFacade extends AbstractFacade<Cliente>{
	@PersistenceContext(unitName = "gestionreservasPersistenceUnit")
	private EntityManager em;
	
	public ClienteFacade() {
		super(Cliente.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	public Cliente search(String cedula)throws SQLException{
		Cliente cliente = new Cliente();
		String sql = "SELECT cli FROM Cliente cli"
				+ " WHERE cli.cedula=:cedula";
		cliente = em.createQuery(sql, Cliente.class).setParameter("cedula", cedula).getSingleResult();
		return cliente;
	}
}
