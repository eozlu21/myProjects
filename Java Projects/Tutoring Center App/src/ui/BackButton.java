package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * This Button class has a universal property of sending the user back to the
 * previous panel.
 * 
 * @author egeozlu
 * @see ui.UserInterfaceDataHelper#goBack
 */
public class BackButton extends JButton {

	/**
	 * Creates a BackButton
	 * 
	 * @see BackButton
	 */
	public BackButton() {
		setText("Back");
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserInterfaceDataHelper.goBack();
			}
		});
	}
}
