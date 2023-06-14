/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao.book;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.exist.xmldb.EXistResource;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XQueryService;

import es.teis.rud6.modelutil.dao.AbstractGenericDao;
import es.teis.rud6.modelutil.exceptions.InstanceNotFoundException;
import modelo.Book;
import util.ConnectionManager;
import util.MyDataSource;

/**
 *
 * @author mfernandez
 */
public class BookEXistDao extends AbstractGenericDao<Book> implements IBookDao {

	
	private static final String AUTHOR_TAG = "author";
	private static final String TITLE_TAG = "title";
	private static final String CATEGORY_ATT = "category";
	private static final String ID_ATT = "id";
	private MyDataSource dataSource;

	public BookEXistDao() {
		this.dataSource = ConnectionManager.getDataSource();
		Class cl;
		try {
			cl = Class.forName(dataSource.getDriver());

			Database database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");

			DatabaseManager.registerDatabase(database);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Book read(long id) throws InstanceNotFoundException {

		return null;
	}

	private Book stringNodeToBook(String nodeString) {
		Element node = null;
		Book book = null;
		try {
			node = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(nodeString.getBytes())).getDocumentElement();

			String title = getElementText(node, TITLE_TAG);
			String author = getElementText(node, AUTHOR_TAG);
			Integer id = Integer.parseInt(node.getAttribute(ID_ATT));
			String category = node.getAttribute(CATEGORY_ATT);

			book = new Book(author, category, title);
			book.setId(id);

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return book;
	}

	private String getElementText(Element parent, String tag) {
		String texto = "";
		NodeList lista = parent.getElementsByTagName(tag);

		if (lista.getLength() > 0) {
			texto = lista.item(0).getTextContent();
		}

		return texto;
	}

	@Override
	public boolean create(Book entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Book entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Book entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Book> findAll() {
		List<Book> books = new ArrayList<Book>();
		Book book = null;

		try (Collection col = DatabaseManager.getCollection(dataSource.getUrl() + dataSource.getColeccion(),
				dataSource.getUser(), dataSource.getPwd())) {

			XQueryService xqs = (XQueryService) col.getService("XQueryService", "1.0");
			xqs.setProperty("indent", "yes");

			CompiledExpression compiled = xqs
					.compile("for $b in doc(\"bookstore.xml\")//book order by $b/title return $b");
			ResourceSet result = xqs.execute(compiled);

			ResourceIterator i = result.getIterator();
			Resource res = null;
			while (i.hasMoreResources()) {
				try {
					res = i.nextResource();

					System.out.println(res.getContent().toString());

					book = stringNodeToBook(res.getContent().toString());
					if (book != null) {
						books.add(book);
					}

				} finally {
					// dont forget to cleanup resources
					try {
						((EXistResource) res).freeResources();
					} catch (XMLDBException xe) {

						xe.printStackTrace();
					}
				}
			}

		} catch (XMLDBException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}

	
}
