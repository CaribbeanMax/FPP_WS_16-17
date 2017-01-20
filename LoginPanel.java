package main;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JPasswordField;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JPasswordField pwdPassword;

	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblName = new JLabel("Name");
		lblName.setMaximumSize(lblName.getPreferredSize());
		panel.add(lblName);
		
		txtName = new JTextField();
		txtName.setMaximumSize(txtName.getPreferredSize());
		panel.add(txtName);
		txtName.setText("Name");
		txtName.setColumns(10);
		
		JLabel lblPasswor = new JLabel("Passwort");
		panel.add(lblPasswor);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setText("");
		panel.add(pwdPassword);
		
		JButton btnLogin = new JButton("Login");
		add(btnLogin);
		
		JButton btnRegister = new JButton("Register");
		add(btnRegister);
		
	}

}
