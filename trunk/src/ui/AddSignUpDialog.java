package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
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
	
	private UIEventListener listener;
	
	public interface UIEventListener {
		public boolean add(int subject);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddSignUpDialog dialog = new AddSignUpDialog(new UIEventListener(){

				@Override
				public boolean add(int subject) {
					System.out.println(String.format("Adding signup to %d", subject));
					return true;
				}});
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
		setBounds(100, 100, 175, 144);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblMateria = new JLabel("C\u00F3digo de materia");
			lblMateria.setBounds(10, 11, 139, 14);
			contentPanel.add(lblMateria);
		}
		
		NumberFormat nf = NumberFormat.getIntegerInstance();
		nf.setGroupingUsed(false);
		textField = new JFormattedTextField(nf);
		textField.setBounds(10, 36, 139, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						boolean res = AddSignUpDialog.this.listener.add(Integer.parseInt(textField.getText()));
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
					@Override
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
