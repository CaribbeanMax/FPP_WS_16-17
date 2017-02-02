package main;

import javax.swing.*;
import java.awt.*;
import com.jgoodies.forms.layout.*;


public class RegisterPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;

	public RegisterPanel(Client client) {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.GLUE_COLSPEC,
				FormSpecs.PREF_COLSPEC,
				FormSpecs.GLUE_COLSPEC,},
			new RowSpec[] {
				FormSpecs.GLUE_ROWSPEC,
				FormSpecs.MIN_ROWSPEC,
				FormSpecs.GLUE_ROWSPEC,}));
		
		JPanel panel_info = new JPanel();
		add(panel_info, "2, 2, fill, fill");
		GridBagLayout gbl_panel_info = new GridBagLayout();
		gbl_panel_info.columnWidths = new int[]{41, 40, 0};
		gbl_panel_info.rowHeights = new int[]{25, 0, 0, 0, 0};
		gbl_panel_info.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_info.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_info.setLayout(gbl_panel_info);
		
		JLabel lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.fill = GridBagConstraints.HORIZONTAL;
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
		lblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 1;
		panel_info.add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 1;
		panel_info.add(passwordField, gbc_passwordField);
		
		JLabel lblConfirmPassword = new JLabel("Confirm password");
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 0;
		gbc_lblConfirmPassword.gridy = 2;
		panel_info.add(lblConfirmPassword, gbc_lblConfirmPassword);
		
		confirmPasswordField = new JPasswordField();
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 1;
		gbc_passwordField_1.gridy = 2;
		panel_info.add(confirmPasswordField, gbc_passwordField_1);
		
		JButton btnRegister = new JButton("Register");
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRegister.gridwidth = 2;
		gbc_btnRegister.anchor = GridBagConstraints.NORTH;
		gbc_btnRegister.gridx = 0;
		gbc_btnRegister.gridy = 3;
		panel_info.add(btnRegister, gbc_btnRegister);
		btnRegister.addActionListener(client);
		btnRegister.setActionCommand("register");
		
		setMinimumSize(panel_info.getPreferredSize());
	}
	
	public String getPlayerName(){
		return txtName.getText();
	}
	public String getPassword(){
		return new String(passwordField.getPassword());
	}
	public String getConfirmPassword(){
		return new String(confirmPasswordField.getPassword());
	}
}
