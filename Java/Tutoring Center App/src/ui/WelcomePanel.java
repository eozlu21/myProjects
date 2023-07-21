package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The first panel user will encounter when running the application. Also
 * appears when a user logs out.
 * 
 * @author egeozlu
 *
 */
public class WelcomePanel extends JPanel {

	private JButton registerButton;
	private JButton loginButton;

	/**
	 * Creates a WelcomePanel
	 * 
	 * @see WelcomePanel
	 */
	public WelcomePanel() {

		registerButton = new JButton("Sign In");
		loginButton = new JButton("Login");

		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] options = { "Student", "Tutor", "Admin" };

				// Show the option pane
				int result = JOptionPane.showOptionDialog(null, "Please select a role", "Option pane",
						JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

				UserInterfaceDataHelper.callRegisterPanel(result);
			}
		});

		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserInterfaceDataHelper.callLoginPanel();

			}
		});

		add(loginButton);
		add(registerButton);
	}

}
