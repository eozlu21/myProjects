package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * This button sends the user back to WelcomePanel and logs the user out
 * 
 * @author egeozlu
 * @see WelcomePanel
 */
public class LogOutButton extends JButton {

	/**
	 * Creates a LogOutButton
	 * 
	 * @see LogOutButton
	 */
	public LogOutButton() {
		setText("Log Out");
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserInterfaceDataHelper.callWelcomePanel();
			}
		});
	}

}
