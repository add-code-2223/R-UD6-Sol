package modelo.servicio.book;


import java.util.List;

import modelo.Book;
import modelo.exceptions.InstanceNotFoundException;

public interface IServicioBook {


	public List<Book> findAll();
	
	public boolean store(String col, String ruta) throws InstanceNotFoundException;
	
}


