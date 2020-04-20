package com.ipartek.formacion.repositorio;

public interface Dao<T> {
	Iterable<T> obtenerTodos();
	T obtenerPorId(int id);
	Integer agregar(T objeto);
	void eliminar(int id);
	void actualizar(T objeto);
}
