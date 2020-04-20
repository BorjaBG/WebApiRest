package com.ipartek.formacion.api.controller;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.*;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;

import com.ipartek.formacion.model.Persona;
import com.ipartek.formacion.repositorio.PersonaMySQL;

@Path("/personas")
@Produces("application/json")
@Consumes("application/json")
public class PersonaController {

	private static final Logger LOGGER = Logger.getLogger(PersonaController.class.getCanonicalName());
	
	private static int id = 1;

	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private Validator validator = factory.getValidator();
	
	@Context
	private ServletContext context;
	
	private static ArrayList<Persona> personas = new ArrayList<Persona>();
	
	
	/*static {
		personas.add( new Persona(1,"Ana","avatar1", "m") );
		personas.add( new Persona(2,"Veronica","avatar2", "m") );
		personas.add( new Persona(3,"Pepe","avatar3", "h") );
		personas.add( new Persona(4,"Julio","avatar4", "h") );
		personas.add( new Persona(5,"Marta","avatar5", "m") );
	}*/
	
	
	public PersonaController() {
		super();
	}


	@GET
	public ArrayList<Persona> getAll() {	
		LOGGER.info("getAll");
		return (ArrayList<Persona>) PersonaMySQL.getInstancia().obtenerTodos();
	}
	
	
	@POST
	public Response insert(Persona persona) {
		LOGGER.info("insert(" + persona + ")");
		Response response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(null).build();

		// validar pojo
		Set<ConstraintViolation<Persona>> violations = validator.validate(persona);
		
		if ( violations.isEmpty() ) {
		
			PersonaMySQL.getInstancia().agregar(persona);
			/*persona.setId(id);
			id++;
			personas.add(persona);*/
			response = Response.status(Status.CREATED).entity(persona).build();
			
		}else {
			ArrayList<String> errores = new ArrayList<String>();
			for ( ConstraintViolation<Persona> violation : violations ) { 
				errores.add( violation.getPropertyPath() + ": " + violation.getMessage() );
			}
			
			response = Response.status(Status.BAD_REQUEST).entity(errores).build();
		}

		return response;
		
	}
	
	
	@PUT
	@Path("/{id: \\d+}")
	public Persona update(@PathParam("id") int id, Persona persona) {
		LOGGER.info("update(" + id + ", " + persona + ")");

		// TODO validar objeto Persona javax.validation

		// TODO comprobar si no encuentra la persona

		PersonaMySQL.getInstancia().actualizar(persona);
		return persona;
	}
	
	
	@DELETE
	@Path("/{id: \\d+}")
	public Response eliminar(@PathParam("id") int id) {
		LOGGER.info("eliminar(" + id + ")");

		Persona persona = null;

		PersonaMySQL.getInstancia().eliminar(id);

		return Response.status(Status.OK).entity(persona).build();
	}
	
	
}
