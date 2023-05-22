package modelo.main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modelo.exceptions.InstanceNotFoundException;
import modelo.servicio.book.IServicioBook;
import modelo.servicio.book.ServicioBook;

public class StoreCollectionWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextArea mensajes_text_Area;

	private IServicioBook bookServicio;
	private JTextField textFieldRutaXML;
	private JTextField textFieldColeccion;
	private JButton btnStoreDoc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StoreCollectionWindow frame = new StoreCollectionWindow();
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
	public StoreCollectionWindow() {

		bookServicio = new ServicioBook();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 847, 772);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(8, 8, 821, 596);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(19, 349, 669, 228);
		panel.add(scrollPane);

		mensajes_text_Area = new JTextArea();
		mensajes_text_Area.setLocation(new Point(0, 20));
		scrollPane.setViewportView(mensajes_text_Area);
		mensajes_text_Area.setEditable(false);
		mensajes_text_Area.setText("Panel de mensajes");
		mensajes_text_Area.setForeground(new Color(255, 0, 0));
		mensajes_text_Area.setFont(new Font("Monospaced", Font.PLAIN, 13));
		
		textFieldRutaXML = new JTextField();
		textFieldRutaXML.setBounds(19, 189, 266, 39);
		panel.add(textFieldRutaXML);
		textFieldRutaXML.setColumns(10);
		
		JLabel lbDocXML = new JLabel("Introduce la ruta al documento XML (desde la raíz del proyecto):");
		lbDocXML.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbDocXML.setBounds(19, 125, 637, 70);
		panel.add(lbDocXML);
		
		textFieldColeccion = new JTextField();
		textFieldColeccion.setColumns(10);
		textFieldColeccion.setBounds(19, 78, 266, 39);
		panel.add(textFieldColeccion);
		
		JLabel lblCol = new JLabel("Introduce el nombre de la colección:");
		lblCol.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCol.setBounds(19, 36, 518, 34);
		panel.add(lblCol);
		
		btnStoreDoc = new JButton("Almacenar");
		btnStoreDoc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnStoreDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String col =textFieldColeccion.getText().trim();
				String ruta = textFieldRutaXML.getText().trim();
				if(col!="" && ruta !="") {
					try {
						if(bookServicio.store(col, ruta)) {
						addMensaje(true, "Se ha guardado el documento correctamente");
						}
						else {
							addMensaje(true, "Ha ocurrido algún problema y no se ha podido guardar el documento");
						}
					} catch (InstanceNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						addMensaje(true, "Ha ocurrido una exception: El fichero no existe en la ruta : " + ruta  + e1.getMessage());
					}
					catch(Exception ex) {
						ex.printStackTrace();
						addMensaje(true, "Ha ocurrido una exception: " + ex.getMessage());
					}
				}
			}
		});
		btnStoreDoc.setBounds(276, 269, 195, 39);
		panel.add(btnStoreDoc);

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
