package ec.edu.ups.services;

import java.sql.SQLException;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ec.edu.ups.ejb.ClienteFacade;
import ec.edu.ups.entity.Cliente;

@Path("/cliente")
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Stateless
public class ClienteRest {
	
	@Inject
	private ClienteFacade ejbCliente;
	
	@POST
	@Path("/save_coustumer")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(@FormParam("nombre") String nombre, @FormParam("apellido") String apellido,
			@FormParam("cedula") String cedula, @FormParam("correo")String correo,
			@FormParam("direccion")String direccion, @FormParam("telefono") String telefono) {
		System.out.println(nombre);
		Cliente cliente = new Cliente(nombre, apellido, cedula, correo,direccion, telefono);
		System.out.println(cliente.toString());
		try {
			ejbCliente.create(cliente);
			return Response.ok("Cliente guardado satisfactoriamente.").build();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			return Response.ok("Error datos duplicados.").build();
		}
	}
	@GET
	@Path("/search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@QueryParam("cedula")String cedula) {
		Cliente cliente = new Cliente();
		ObjectMapper mapper = new ObjectMapper();
		try {
			cliente = ejbCliente.search(cedula);
			String json = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(cliente);
			return Response.ok(json).build();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			return Response.serverError().build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
