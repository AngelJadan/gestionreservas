package ec.edu.ups.ejb;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entity.Reserva;

@Stateless
public class ReservaFacade extends AbstractFacade<Reserva>{
	@PersistenceContext(unitName = "gestionreservasPersistenceUnit")
	private EntityManager em;
	
	public ReservaFacade() {
		super(Reserva.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	/**
	 * 
	 * @param cedula Numero de cedula del cliente
	 * @return lista de reservas del cliente ingresado.
	 * @throws SQLException
	 */
	public List<Reserva> listReservaCliente(String cedula)throws SQLException {
		List<Reserva> lista = new ArrayList<Reserva>();
		String sql = "SELECT res FROM Reserva res, Cliente cli "
				+ "WHERE cli.cedula=:cedula AND res.cliente=cli.id";
		lista = em.createQuery(sql, Reserva.class).setParameter("cedula", cedula).getResultList();
		return lista;
	}
	/**
	 * 
	 * @param cedula Numero de cedula del cliente
	 * @return lista de reservas del cliente ingresado.
	 * @throws SQLException
	 */
	public List<Reserva> listReservaRest(String nombre, LocalDate fecha)throws SQLException {
		System.out.println("rest: "+nombre+" fecha: "+fecha);
		List<Reserva> lista = new ArrayList<Reserva>();
		String sql = "SELECT rev FROM Reserva rev, Restaurante res "
				+ "WHERE res.nombre =:nombre AND res.id = rev.restaurante AND rev.fecha=:fecha";
		lista = em.createQuery(sql, Reserva.class)
				.setParameter("nombre", nombre)
				.setParameter("fecha", fecha)
				.getResultList();
		return lista;
	}
	/**
	 * Metodo para obtener el aforo reservado para esa fecha y hora por todos los clientes de un restaurante.
	 * @param nombre Nombre del restaurante
	 * @param fecha 
	 * @param hora
	 * @return numero de asistenes para esta hora y fecha
	 */
	public int getAforo(String nombre, LocalDate fecha, LocalTime hora) {
		List<Reserva> lista = new ArrayList<Reserva>();
		int aforousado = 0;
		String sql = "SELECT res FROM Reserva res, Restaurante ret"
				+ " WHERE ret.nombre=:nombre AND res.restaurante=ret.id AND res.fecha=:fecha "
				+ "AND res.hora=:hora";
		lista = em.createQuery(sql, Reserva.class).setParameter("nombre", nombre)
				.setParameter("fecha", fecha).setParameter("hora", hora).getResultList();
		for (Reserva reserva : lista) {
			aforousado = aforousado + reserva.getNasistentes();
		}
		return aforousado;
	}
}
