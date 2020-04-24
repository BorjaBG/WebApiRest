package com.ipartek.formacion.repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.ipartek.formacion.model.Curso;

public class CursoMySQL implements Dao<Curso>{
	
	
	private static final Logger LOGGER = Logger.getLogger(CursoMySQL.class.getCanonicalName());
	private static final String sqlSelect = "SELECT id, nombre, imagen, precio FROM cursos ORDER BY id DESC LIMIT 100";
	private static final String sqlSelectById   = "SELECT id, nombre, precio, imagen FROM curso WHERE id = ?";
	private static final String sqlSelectByNombre = "SELECT id, nombre, imagen, precio FROM curso WHERE nombre LIKE ? ORDER BY id DESC LIMIT 100";
	String url;
	String usuario;
	String contraseña;

	//SINGLETON
	private CursoMySQL(String url, String usuario, String contraseña) {
		this.url = url;
		this.usuario = usuario;
		this.contraseña = contraseña;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("No se ha encontrado el driver de JDBC");
		}
	}
	
	private static CursoMySQL INSTANCIA = null;
	
	public static CursoMySQL getInstancia() {
		if(INSTANCIA == null) {
			INSTANCIA = new CursoMySQL("jdbc:mysql://localhost:3306/alumnos?serverTimezone=UTC",
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
	public Iterable<Curso> obtenerTodos() {
		try(Connection con = getConexion()){
			try (PreparedStatement ps = con.prepareStatement(sqlSelect)) {
				try (ResultSet rs = ps.executeQuery()) {
					ArrayList<Curso> cursos = new ArrayList<>();
					while (rs.next()) {
						cursos.add(new Curso(rs.getInt("id"), rs.getString("nombre"), rs.getString("imagen"), rs.getDouble("precio")));
					}
					return cursos;
				}
			}
			
		}catch(SQLException e) {
			throw new RuntimeException("Error al obtener todos los registros", e);
		}
	}
	
	public Iterable<Curso> getAllLikeNombre( String busqueda ) {
		LOGGER.info("getAll");		
		ArrayList<Curso> cursos = new ArrayList<Curso>();
		//ConnectionManager.getConnection()
		try (Connection con = getConexion();
				PreparedStatement ps = con.prepareStatement(sqlSelectByNombre);
				) {

			//CUIDADO los simobolos % % no se pueden porner en la SQL, siempre en el PST
			ps.setString(1, "%" + busqueda + "%");
			
			try( ResultSet rs = ps.executeQuery() ){
				
				LOGGER.info(ps.toString());			
				while( rs.next() ) {				
					cursos.add( mapper(rs) );				
				}	
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		return cursos;
	}
	
	private Curso mapper( ResultSet rs ) throws SQLException {
		Curso c = new Curso();
		c.setId( rs.getInt("id") );
		c.setNombre( rs.getString("nombre"));
		c.setImagen( rs.getString("imagen"));
		c.setPrecio( rs.getDouble("precio"));
		return c;
	}

	@Override
	public Curso obtenerPorId(int id) {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(sqlSelectById)) {
				ps.setLong(1, id);

				try(ResultSet rs = ps.executeQuery()){

					if(rs.next()) {
						return new Curso(rs.getInt("id"), rs.getString("nombre"), rs.getString("imagen"), rs.getDouble("precio"));
					} else {
						return null;
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al obtener el curso id: " + id, e);
		}
	}

	@Override
	public Integer agregar(Curso objeto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizar(Curso objeto) {
		// TODO Auto-generated method stub
		
	}
	
	

}
