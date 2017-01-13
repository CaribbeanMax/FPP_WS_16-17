package main;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;

public class RegisterPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JTextField txtPasswor;
	private JTextField txtConfirmPassword;

	/**
	 * Create the panel.
	 */
	public RegisterPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblRegister = new JLabel("Register");
		add(lblRegister);
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblName = new JLabel("Name");
		panel.add(lblName);
		
		txtName = new JTextField();
		panel.add(txtName);
		txtName.setText("name");
		txtName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		panel.add(lblPassword);
		
		txtPasswor = new JTextField();
		panel.add(txtPasswor);
		txtPasswor.setText("password");
		txtPasswor.setColumns(10);
		
		JLabel lblConfirmPassword = new JLabel("Confirm password");
		panel.add(lblConfirmPassword);
		
		txtConfirmPassword = new JTextField();
		panel.add(txtConfirmPassword);
		txtConfirmPassword.setText("confirm password");
		txtConfirmPassword.setColumns(10);
		
		JButton btnRegister = new JButton("Register");
		add(btnRegister);

	}

}
