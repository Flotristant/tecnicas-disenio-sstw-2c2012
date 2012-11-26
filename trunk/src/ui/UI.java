package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.JSeparator;

public class UI extends JFrame {
	
	public interface UIActionListener {
		public void onProcessMailsClicked();
		public boolean onAddSubjectClicked(int subject, int student);
		public void onDeleteSubjectClicked(int subject, int student);
	}

	private static final long serialVersionUID = -9145648551464767892L;

	private UIActionListener listener;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tablemodel;

	public static UI ui_instance; // for application startup from here
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final HashMap<Integer, Set<Integer>> inscriptions = new HashMap<Integer, Set<Integer>>();
					UI.UIActionListener listener = new UIActionListener(){

						@Override
						public void onProcessMailsClicked() {
							// ejecutar procesamiento de mails
							System.out.println("Procesamiento de mails");
						}

						@Override
						public boolean onAddSubjectClicked(int subject, int student) {
							System.out.println(String.format("Add inscription to %d for student %d", subject, student));
							if (!inscriptions.containsKey(subject)) {
								inscriptions.put(subject, new HashSet<Integer>());
								inscriptions.get(subject).add(student);
							}
							UI.ui_instance.setInscriptions(inscriptions);
							return true;
						}

						@Override
						public void onDeleteSubjectClicked(int subject,
								int student) {
							System.out.println(String.format("Delete inscription to %d for student %d", subject, student));
							inscriptions.get(subject).remove(student);
							UI.ui_instance.setInscriptions(inscriptions);
						}
					};
					UI.ui_instance = new UI(listener);
					UI.ui_instance.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setInscriptions(Map<Integer, Set<Integer>> students_by_subject) {
		for (int i=0; i<tablemodel.getRowCount(); i++) {
			tablemodel.removeRow(i);
		}
		for (Integer subject : students_by_subject.keySet()) {
			for (Integer student : students_by_subject.get(subject)) {
				tablemodel.addRow(new Integer[]{subject, student});
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public UI(UIActionListener listener) {
		this.listener = listener;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 284);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 27, 414, 125);
		contentPane.add(scrollPane);
		
		table = new JTable();
		tablemodel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Materia", "Alumno"
			}
		);
		table.setModel(tablemodel);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(0).setMaxWidth(300);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Agregar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AddSignUpDialog(new AddSignUpDialog.UIEventListener(){
					@Override
					public boolean add(int subject, int student) {
						return UI.this.listener.onAddSubjectClicked(subject, student);
					}}).setVisible(true);
			}
		});
		btnNewButton.setBounds(335, 164, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int row : table.getSelectedRows()) {
					UI.this.listener.onDeleteSubjectClicked((int)tablemodel.getValueAt(row, 0), (int)tablemodel.getValueAt(row, 1));
				}
			}
		});
		btnBorrar.setBounds(236, 164, 89, 23);
		contentPane.add(btnBorrar);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 201, 414, 2);
		contentPane.add(separator);
		
		JButton btnProcesarMailsAhora = new JButton("Procesar mails ahora");
		btnProcesarMailsAhora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UI.this.listener.onProcessMailsClicked();
			}
		});
		btnProcesarMailsAhora.setBounds(148, 214, 139, 23);
		contentPane.add(btnProcesarMailsAhora);
	}
}
