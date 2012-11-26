package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

public class AddSignUpDialog extends JDialog {

	private static final long serialVersionUID = 9184841576275841439L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	
	private UIEventListener listener;
	
	public interface UIEventListener {
		public boolean add(int subject, int student);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddSignUpDialog dialog = new AddSignUpDialog(new UIEventListener(){

				@Override
				public boolean add(int subject, int student) {
					System.out.println(String.format("Adding signup to %d for student %d", subject, student));
					return true;
				}});
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddSignUpDialog(UIEventListener listener) {
		this.listener = listener;
		setBounds(100, 100, 175, 198);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblMateria = new JLabel("C\u00F3digo de materia");
			lblMateria.setBounds(10, 11, 139, 14);
			contentPanel.add(lblMateria);
		}
		
		textField = new JFormattedTextField(NumberFormat.getIntegerInstance());
		textField.setBounds(10, 36, 139, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblAlumno = new JLabel("Padr\u00F3n de alumno");
		lblAlumno.setBounds(10, 67, 130, 14);
		contentPanel.add(lblAlumno);
		
		textField_1 = new JFormattedTextField(NumberFormat.getIntegerInstance());
		textField_1.setBounds(10, 92, 139, 20);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean res = AddSignUpDialog.this.listener.add(Integer.parseInt(textField.getText()), Integer.parseInt(textField_1.getText()));
						if (res) {
							AddSignUpDialog.this.setVisible(false);
						} else {
							JOptionPane.showMessageDialog(AddSignUpDialog.this, "Error agregando materia");
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AddSignUpDialog.this.setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
