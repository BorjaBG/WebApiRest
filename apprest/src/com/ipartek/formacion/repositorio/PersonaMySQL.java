package com.ipartek.formacion.repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ipartek.formacion.model.Persona;

public class PersonaMySQL implements Dao<Persona> {
	private static final String sqlSelect = "SELECT * FROM alumnos";
	private static final String sqlUpdate = "";
	String url;
	String usuario;
	String contraseña;

	//SINGLETON
	private PersonaMySQL(String url, String usuario, String contraseña) {
		this.url = url;
		this.usuario = usuario;
		this.contraseña = contraseña;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("No se ha encontrado el driver de JDBC");
		}
	}
	
	private static PersonaMySQL INSTANCIA = null;
	
	public static PersonaMySQL getInstancia() {
		if(INSTANCIA == null) {
			INSTANCIA = new PersonaMySQL("jdbc:mysql://localhost:3306/alumnos?serverTimezone=UTC",
					"root", "admin");
		}
		return INSTANCIA;
		
	}
	
	//FIN SINGLETON
	
	private Connection getConexion(){
		//System.out.println(url + "\n" + usuario + "\n" + contraseña + "\n");
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/alumnos?serverTimezone=UTC", "root", "admin");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Error al conectar con la base de datos", e);
		}
	}
	
	@Override
	public Iterable<Persona> obtenerTodos() {
		
		try(Connection con = getConexion()){
			try (PreparedStatement ps = con.prepareStatement(sqlSelect)) {
				try (ResultSet rs = ps.executeQuery()) {
					ArrayList<Persona> personas = new ArrayList<>();
					while (rs.next()) {
						personas.add(new Persona(rs.getInt("id"), rs.getString("nombre"), rs.getString("avatar"), rs.getString("sexo")));
					}
					return personas;
				}
			}
			
		}catch(SQLException e) {
			throw new RuntimeException("Error al obtener todos los registros", e);
		}
		
	}

	@Override
	public Persona obtenerPorId(int id) {
		throw new RuntimeException("Metodo no implementado");
		//return null;
	}

	@Override
	public Integer agregar(Persona persona) {
		try (Connection con = getConexion()) {
			try (PreparedStatement ps = con.prepareStatement("INSERT INTO alumnos(nombre,sexo,avatar) VALUES (?,?,?)")) {
				 ps.setString(1, persona.getNombre());
				 ps.setString(2, persona.getSexo());
				 ps.setString(3, persona.getAvatar());

				int numeroRegistrosModificados = ps.executeUpdate();

				if (numeroRegistrosModificados != 1) {
					throw new RuntimeException("Resultado no esperado en la INSERT: " + numeroRegistrosModificados);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al obtener todos los registros", e);
		}
		return null;
			
	}

	@Override
	public void eliminar(int id) {
		try(Connection con = getConexion()){
			try(PreparedStatement ps = con.prepareStatement("DELETE FROM alumnos WHERE id = ?")){
				ps.setLong(1, id);	
				int numeroRegistrosModificados = ps.executeUpdate();
				if(numeroRegistrosModificados != 1) {
					throw new RuntimeException("Resultado no esperado en la DELETE: " +
							numeroRegistrosModificados);
				}
			}
			
		}catch(SQLException e) {
			throw new RuntimeException("Error al obtener todos los registros", e);
		}
	}

	@Override
	public void actualizar(Persona persona) {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement("UPDATE alumnos SET nombre=?, avatar=?, sexo=? WHERE id=?")) {
				ps.setString(1, persona.getNombre());
				ps.setString(2, persona.getAvatar());
				ps.setString(3, persona.getSexo());
				ps.setLong(4, persona.getId());

				int numeroRegistrosModificados = ps.executeUpdate();

				if(numeroRegistrosModificados != 1) {
					throw new RuntimeException("Resultado no esperado en la UPDATE: " +
							numeroRegistrosModificados);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al modificar la persona", e);
		}
	}
		
}
	
