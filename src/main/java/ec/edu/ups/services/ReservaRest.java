package ec.edu.ups.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.icu.text.SimpleDateFormat;

import ec.edu.ups.ejb.ClienteFacade;
import ec.edu.ups.ejb.ReservaFacade;
import ec.edu.ups.ejb.RestauranteFacade;
import ec.edu.ups.entity.Cliente;
import ec.edu.ups.entity.Reserva;
import ec.edu.ups.entity.Restaurante;

@Path("/reserva")
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Stateless
public class ReservaRest {
	@Inject
	private ReservaFacade ejbReserva;
	@Inject
	private ClienteFacade ejbCliente;
	@Inject
	private RestauranteFacade ejbRestaurante;

	/**
	 * Metodo para reservar un restaurante, en base de hora y fecha.
	 * @param fecha A reservar
	 * @param hora A reservar
	 * @param cedula Numero de cedula del cliente.
	 * @param asistentes Numero de asistentes a ocupar el restaurante en la hora y fecha indicada.
	 * @param restaurant Nombre del restaurante.
	 * @return
	 */
	@POST
	@Path("/save_reserva")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveReserva(@FormParam("fecha") String fecha, @FormParam("hora") String hora,
			@FormParam("cedula") String cedula, @FormParam("asistentes") int asistentes,
			@FormParam("restaurante") String restaurant) {

		Cliente cliente = new Cliente();
		Restaurante restaurante = new Restaurante();
		Reserva reserva = new Reserva();
		int aforoocupado =0;
		int afodisponible = 0;

		try {
			cliente = ejbCliente.search(cedula);
			if (cliente != null) {
				restaurante = ejbRestaurante.searchToName(restaurant);
				if (restaurante != null) {
					aforoocupado = ejbReserva.getAforo(restaurant, LocalDate.parse(fecha), LocalTime.parse(hora));
					afodisponible = restaurante.getAforo()-aforoocupado;
					if (afodisponible>asistentes) {
						reserva = new Reserva(0, LocalDate.parse(fecha), LocalTime.parse(hora), cliente, asistentes,
								restaurante);
						ejbReserva.create(reserva);
						return Response.ok("Reserva registrada exitosamente, para la fecha: "+fecha+" hora: "+hora).build();
					}else {
						return Response.ok("El aforo es mayor al número de asistentes ingresaos para esta hora y fecha,"
								+ "\n aforo disponible "+afodisponible).build();
					}
					
				} else {
					return Response.ok("No se ha podido regitrar la reserva, no existe el restaurante " + restaurant)
							.build();
				}
			} else {
				return Response.ok(
						"No se ha podido registrar la reserva, \n" + " no existe el cliente con el numero de cedula "
								+ cedula + " \n" + " primero debe de crear el cliente.")
						.build();
			}
		} catch (Exception e) {
			System.out.println("Error reserva. " + e.getLocalizedMessage());
			return Response.serverError().build();
		}
	}

	/**
	 * Metodo para listar las reservas del restaurante en una fecha.
	 * @param nombre Nombre del restaurante.
	 * @param fecha Fecha a consultar las reservas.
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list_reserva_restaurante")
	public Response listReservaRestaurante(@QueryParam("nombre") String nombre,
			@QueryParam("fecha") String fecha) {
		List<Reserva> lista = new ArrayList<Reserva>();
		try {
			lista = ejbReserva.listReservaRest(nombre, LocalDate.parse(fecha));
			return Response.ok(lista).build();
		} catch (SQLException e) {
			System.out.println("Error consulta lista por restaurante. " + e.getLocalizedMessage());
			return Response.serverError().build();
		}
	}
	/**
	 * Metodo para consular las reservas del cliente.
	 * @param cedula Numero de cedula del cliente.
	 * @return Listado de reservas del cliente.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list_reserva_cliente")
	public Response listReservaCliente(@QueryParam("cedula")String cedula) {
		List<Reserva>lista = new ArrayList<Reserva>();
		try {
			lista = ejbReserva.listReservaCliente(cedula);
			return Response.ok(lista).build();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			return Response.serverError().build();
		}
		
	}
}
