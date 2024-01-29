package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import admin.Admin;
import tutoringCenterSystem.TutoringCenterSystem;

/**
 * This panel is displayed when a user prompts to register as an Admin.
 * 
 * @author egeozlu
 *
 */
public class AdminRegisterPanel extends JPanel {

	private BackButton backButton;
	private JLabel usernameJLabel;
	private JTextField usernameField;
	private JLabel passwordJLabel;
	private JPasswordField passwordField;
	private JButton registerButton;

	/**
	 * Creates a new Admin Register Panel
	 * 
	 * @see AdminRegisterPanel
	 */
	public AdminRegisterPanel() {

		setLayout(new GridLayout(3, 2, 20, 20));

		usernameJLabel = new JLabel("Username:");
		usernameField = new JTextField();
		passwordJLabel = new JLabel("Password:");
		passwordField = new JPasswordField();

		backButton = new BackButton();
		backButton.setText("Back");
		registerButton = new JButton("Register");

		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (Admin admin : TutoringCenterSystem.getInstance().getAdmins()) {
					if (admin.getUsername().equals(usernameField.getText())) {
						JOptionPane.showMessageDialog(null, "An admin with an identical username exists", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				new Admin(usernameField.getText(), new String(passwordField.getPassword()));
				JOptionPane.showMessageDialog(null, "Successfully created admin!", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				UserInterfaceDataHelper.callWelcomePanel();
			}
		});

		add(usernameJLabel);
		add(usernameField);
		add(passwordJLabel);
		add(passwordField);
		add(registerButton);
		add(backButton);

	}
}
