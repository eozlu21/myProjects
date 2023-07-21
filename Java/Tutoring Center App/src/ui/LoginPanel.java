package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import user.User;
import user.UserHelper;

/**
 * This panel allows user to login as any user type they might want
 * 
 * @author egeozlu
 *
 */
public class LoginPanel extends JPanel {

	private JTextField usernameTextField;
	private JLabel usernameLabel;
	private JPasswordField passwordTextField;
	private JLabel passwordLabel;
	private JButton loginButton;
	private JComboBox<String> userTypeBox;
	private BackButton backButton;

	/**
	 * Creates a login panel
	 * 
	 * @see LoginPanel
	 */
	public LoginPanel() {

		setLayout(new GridLayout(3, 2));

		backButton = new BackButton();

		backButton.setBounds(50, 50, 50, 50);
		add(backButton);
		usernameTextField = new JTextField();
		usernameTextField.setBounds(300, 300, 120, 20);

		usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(230, 300, 100, 20);
		add(usernameLabel);
		add(usernameTextField);

		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(300, 330, 120, 20);

		passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(230, 330, 100, 20);
		add(passwordLabel);
		add(passwordTextField);

		userTypeBox = new JComboBox<>(new String[] { "Student", "Tutor", "Admin" });
		userTypeBox.setBounds(450, 300, 100, 50);
		add(userTypeBox);

		loginButton = new JButton("Login");
		loginButton.setBounds(310, 360, 90, 30);

		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String userType = (String) userTypeBox.getSelectedItem();
				String username = usernameTextField.getText();
				String password = new String(passwordTextField.getPassword());
				User user = UserHelper.login(username, password, userType);
				if (!userExists(user)) {
					JOptionPane.showMessageDialog(null, "User couldn't be found", "Cannot login",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				UserInterfaceDataHelper.callUserPanel(user);

				// TODO make the UI go to the corresponding UI panel

			}
		});

		add(loginButton);// adding button on frame

		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		// setTitle("Login Panel");
		setSize(500, 500);
		setLayout(null);
		setVisible(true);
	}

	public boolean userExists(User user) {
		return user != null;
	}
}
