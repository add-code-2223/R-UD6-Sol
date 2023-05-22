package modelo.servicio.book;

import java.io.File;
import java.util.List;

import modelo.Book;
import modelo.dao.book.BookEXistDao;
import modelo.dao.book.IBookDao;
import modelo.exceptions.InstanceNotFoundException;

public class ServicioBook implements IServicioBook {

	private IBookDao bookDao;

	public ServicioBook() {
		bookDao = new BookEXistDao();
	}

	@Override
	public List<Book> findAll() {
		return bookDao.findAll();
	}

	@Override
	public boolean store(String col, String ruta) throws InstanceNotFoundException {
	
		
		
		File f = new File(ruta);

		if (!f.canRead()) {
			throw new InstanceNotFoundException(ruta, File.class.getName());
		}
		
		return bookDao.store(col, f);
	}




}
