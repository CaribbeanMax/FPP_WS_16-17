package main;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridLayout;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JTextField txtPassword;

	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
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
		
		JLabel lblPasswor = new JLabel("Passwor");
		panel.add(lblPasswor);
		
		txtPassword = new JTextField();
		txtPassword.setMaximumSize(txtPassword.getPreferredSize());
		panel.add(txtPassword);
		txtPassword.setText("Password");
		txtPassword.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		add(btnLogin);
		
		JButton btnRegistrieren = new JButton("Registrieren");
		add(btnRegistrieren);
		
	}

}
