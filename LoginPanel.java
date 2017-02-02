package main;

import javax.swing.*;
import com.jgoodies.forms.layout.*;
import java.awt.*;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JPasswordField passwordField;

	public LoginPanel(Client client) {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.GLUE_COLSPEC,
				FormSpecs.PREF_COLSPEC,
				FormSpecs.GLUE_COLSPEC,},
			new RowSpec[] {
				FormSpecs.GLUE_ROWSPEC,
				FormSpecs.PREF_ROWSPEC,
				FormSpecs.GLUE_ROWSPEC,}));
		
		JPanel panel_info = new JPanel();
		add(panel_info, "2, 2, fill, fill");
		GridBagLayout gbl_panel_info = new GridBagLayout();
		gbl_panel_info.columnWidths = new int[]{0, 0, 0};
		gbl_panel_info.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_info.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_info.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_info.setLayout(gbl_panel_info);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		panel_info.add(lblName, gbc_lblName);
		
		txtName = new JTextField();
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 0;
		panel_info.add(txtName, gbc_txtName);
		txtName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 1;
		panel_info.add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_pwdPassword = new GridBagConstraints();
		gbc_pwdPassword.insets = new Insets(0, 0, 5, 0);
		gbc_pwdPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwdPassword.gridx = 1;
		gbc_pwdPassword.gridy = 1;
		panel_info.add(passwordField, gbc_pwdPassword);
		
		JButton btnLogin = new JButton("Login");
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLogin.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogin.gridx = 1;
		gbc_btnLogin.gridy = 2;
		panel_info.add(btnLogin, gbc_btnLogin);
		btnLogin.addActionListener(client);
		btnLogin.setActionCommand("login");
		
		JLabel lblAreYouNewHere = new JLabel("Are you new here?");
		GridBagConstraints gbc_lblAreYouNewHere = new GridBagConstraints();
		gbc_lblAreYouNewHere.insets = new Insets(0, 0, 0, 5);
		gbc_lblAreYouNewHere.gridx = 0;
		gbc_lblAreYouNewHere.gridy = 3;
		panel_info.add(lblAreYouNewHere, gbc_lblAreYouNewHere);
		
		JButton btnRegister = new JButton("Register");
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRegister.gridx = 1;
		gbc_btnRegister.gridy = 3;
		panel_info.add(btnRegister, gbc_btnRegister);
		btnRegister.addActionListener(client);
		btnRegister.setActionCommand("toRegister");

		setMinimumSize(panel_info.getPreferredSize());	
	}
	
	public String getName(){
		return txtName.getText();
	}
	
	public String getPassword() {
		return new String(passwordField.getPassword());
	}
}
