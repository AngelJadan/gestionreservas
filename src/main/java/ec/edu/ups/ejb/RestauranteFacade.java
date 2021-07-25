package ec.edu.ups.ejb;


import java.sql.SQLException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entity.Restaurante;

@Stateless
public class RestauranteFacade extends AbstractFacade<Restaurante>{
	@PersistenceContext(unitName = "gestionreservasPersistenceUnit")
	private EntityManager em;
	
	public RestauranteFacade() {
		super(Restaurante.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public Restaurante searchToName(String nombre)throws SQLException{
		Restaurante restaurante = new Restaurante();
		String sql = "SELECT res FROM Restaurante res "
				+ "WHERE res.nombre =:nombre";
		System.out.println(sql+" "+nombre);
		restaurante = em.createQuery(sql, Restaurante.class).setParameter("nombre", nombre).getSingleResult();
		return restaurante;
	}
}
