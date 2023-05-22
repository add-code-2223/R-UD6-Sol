package modelo.main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import modelo.Book;
import modelo.servicio.book.IServicioBook;
import modelo.servicio.book.ServicioBook;

public class SearchBookWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextArea mensajes_text_Area;
	private JList<Book> JListBooks;

	private IServicioBook bookServicio;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchBookWindow frame = new SearchBookWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SearchBookWindow() {

		bookServicio = new ServicioBook();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 847, 772);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(8, 8, 821, 500);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(19, 264, 669, 228);
		panel.add(scrollPane);

		mensajes_text_Area = new JTextArea();
		scrollPane.setViewportView(mensajes_text_Area);
		mensajes_text_Area.setEditable(false);
		mensajes_text_Area.setText("Panel de mensajes");
		mensajes_text_Area.setForeground(new Color(255, 0, 0));
		mensajes_text_Area.setFont(new Font("Monospaced", Font.PLAIN, 13));

		JListBooks = new JList<Book>();

		JListBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JListBooks.setBounds(403, 37, 377, 200);

		JScrollPane scrollPanel_in_JlistAllDepts = new JScrollPane(JListBooks);
		scrollPanel_in_JlistAllDepts.setLocation(300, 0);
		scrollPanel_in_JlistAllDepts.setSize(500, 250);

		panel.add(scrollPanel_in_JlistAllDepts);

		btnNewButton = new JButton("Mostrar todos los libros");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addMensaje(true, "Buscando...");
				List<Book> books = bookServicio.findAll();
				DefaultListModel<Book> defModel = new DefaultListModel<>();

				defModel.addAll(books);
				JListBooks.setModel(defModel);

			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(0, 18, 284, 39);
		panel.add(btnNewButton);

	}

	private void clearJListModel() {
		ListModel<Book> model = JListBooks.getModel();
		if (model != null) {
			if (model instanceof DefaultListModel) {
				((DefaultListModel<Book>) model).removeAllElements();
			}
		}
	}

	private void addMensaje(boolean keepText, String msg) {
		String oldText = "";
		if (keepText) {
			oldText = mensajes_text_Area.getText();

		}
		oldText = oldText + "\n" + msg;
		mensajes_text_Area.setText(oldText);

	}

	

}
