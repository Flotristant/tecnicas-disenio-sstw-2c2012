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

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.SystemColor;

public class UI extends JFrame {
	
	public interface UIActionListener {
		public void onRun();
		public void onStop();
		public boolean onAddSubjectClicked(int subject);
		public void onDeleteSubjectClicked(int subject);
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
			@Override
			public void run() {
				try {
					final HashSet<Integer> inscriptions = new HashSet<Integer>();
					UI.UIActionListener listener = new UIActionListener(){

						@Override
						public void onRun() {
							// ejecutar procesamiento de mails
							UI.ui_instance.setStatus(Status.Up);
							System.out.println("Procesamiento de mails");
						}

						@Override
						public boolean onAddSubjectClicked(int subject) {
							System.out.println(String.format("Add inscription to %d", subject));
							inscriptions.add(subject);
							UI.ui_instance.setInscriptions(inscriptions);
							return true;
						}

						@Override
						public void onDeleteSubjectClicked(int subject) {
							System.out.println(String.format("Delete inscription to %d", subject));
							inscriptions.remove(subject);
							UI.ui_instance.setInscriptions(inscriptions);
						}

						@Override
						public void onStop() {
							UI.ui_instance.setStatus(Status.Down);
							
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
	
	public static enum Status {Up, Error, Down};
	
	public Status status;
	
	public void setStatus(Status s) {
		this.status = s;
		switch (s) {
		case Up:
			this.lblStatusRunningOk.setText("Status: Running OK");
			lblStatusRunningOk.setForeground(SystemColor.textHighlight);
			this.btnRunning.setText("Stop daemon");
			break;
		case Error:
			this.lblStatusRunningOk.setText("Status: Error");
			lblStatusRunningOk.setForeground(Color.RED);
			this.btnRunning.setText("Stop daemon");
			break;
		case Down:
			this.lblStatusRunningOk.setText("Status: Down");
			lblStatusRunningOk.setForeground(Color.BLACK);
			this.btnRunning.setText("Start daemon");
			break;
		}
	};
	
	public void setInscriptions(Set<Integer> students_by_subject) {
		for (int i=0; i<tablemodel.getRowCount(); i++) {
			tablemodel.removeRow(i);
		}
		for (Integer subject : students_by_subject) {
			tablemodel.addRow(new Integer[]{subject});
		}
	}
	
	public JButton btnRunning;
	
	public JLabel lblStatusRunningOk;

	/**
	 * Create the frame.
	 */
	public UI(UIActionListener listener) {
		this.listener = listener;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 125);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 93, 414, 125);
		contentPane.add(scrollPane);
		
		table = new JTable();
		tablemodel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Materia"
			}
		);
		table.setModel(tablemodel);

		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Agregar");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddSignUpDialog(new AddSignUpDialog.UIEventListener(){
					@Override
					public boolean add(int subject) {
						return UI.this.listener.onAddSubjectClicked(subject);
					}}).setVisible(true);
			}
		});
		btnNewButton.setBounds(335, 230, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int row : table.getSelectedRows()) {
					UI.this.listener.onDeleteSubjectClicked((int)tablemodel.getValueAt(row, 0));
				}
			}
		});
		btnBorrar.setBounds(236, 230, 89, 23);
		contentPane.add(btnBorrar);
		
		btnRunning = new JButton("Start daemon");
		btnRunning.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switch (UI.this.status) {
				case Error:
				case Up:
					UI.this.btnRunning.setText("Shutting down daemon...");
					UI.this.listener.onStop();
					break;
				case Down:
					UI.this.listener.onRun();
					break;
				}
			}
		});
		btnRunning.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRunning.setBounds(10, 11, 414, 43);
		contentPane.add(btnRunning);
		
		lblStatusRunningOk = new JLabel("Status: Running OK");
		lblStatusRunningOk.setBackground(Color.BLACK);
		lblStatusRunningOk.setForeground(SystemColor.textHighlight);
		lblStatusRunningOk.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStatusRunningOk.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatusRunningOk.setBounds(10, 65, 414, 17);
		contentPane.add(lblStatusRunningOk);
		
		this.defaults();
	}
	
	private void defaults() {
		this.setStatus(Status.Down);
	}
}
