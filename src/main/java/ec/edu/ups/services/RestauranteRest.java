package ec.edu.ups.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ec.edu.ups.ejb.RestauranteFacade;
import ec.edu.ups.entity.Restaurante;

@Path("/restaurante")
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Stateless
public class RestauranteRest {
	
	@Inject
	private RestauranteFacade ejbrestaurante;
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/save_restaurante")
	public Response saveRestaurant(@FormParam("nombre")String nombre, @FormParam("direccion") String direccion,
			@FormParam("telefono")String telefono, @FormParam("aforo") int aforo) {
		Message sms = new Message();
		Restaurante restaurante = new Restaurante(0, nombre, direccion, telefono, aforo, null);
		try {
			ejbrestaurante.create(restaurante);
			sms.setCode(200);
			sms.setMessaje("Creado el restaurante codigo: "+restaurante.getId()+" \n Nombre: "+nombre+"., Aforo diario: "+aforo);
			return Response.ok(sms).build();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			sms.setCode(404);
			sms.setMessaje("Verifique los datos, no se pudo registrar el restaurante.");
			return Response.ok(sms).build();
		}
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list_restaurante")
	public Response listRestaurante() {
		List<Restaurante> lista = new ArrayList<Restaurante>();
		try {
			lista = ejbrestaurante.findAll();
			return Response.ok(lista).build();
		} catch (SQLException e) {
			System.out.println("Error: "+e.getLocalizedMessage());
			return Response.serverError().build();
		}
	}
}
