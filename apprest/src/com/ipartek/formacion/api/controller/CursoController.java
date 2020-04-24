package com.ipartek.formacion.api.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.ipartek.formacion.model.Curso;
import com.ipartek.formacion.repositorio.CursoMySQL;

@Path("/cursos")
@Produces("application/json")
@Consumes("application/json")
public class CursoController {
	
	private static final Logger LOGGER = Logger.getLogger(PersonaController.class.getCanonicalName());
	
	private ServletContext context;
	
	private static ArrayList<Curso> cursos = new ArrayList<Curso>();

	public CursoController() {
		super();
	}
	
	@GET
	public ArrayList<Curso> getAll() {	
		LOGGER.info("getAll");
		return (ArrayList<Curso>) CursoMySQL.getInstancia().obtenerTodos();
	}

}
