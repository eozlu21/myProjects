package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import tutor.ALevelTutor;
import tutor.BLevelTutor;
import tutor.Tutor;
import tutoringCenterSystem.TutoringCenterSystem;

/**
 * This class allows user to create a Tutor object
 * 
 * @author egeozlu
 *
 */
public class TutorRegisterPanel extends JPanel {

	private static final String POSITIVE_INTEGER_WARNING = "Please enter an positive integer as your ID";
	private BackButton backButton;
	private JLabel usernameJLabel;
	private JTextField usernameField;
	private JLabel passwordJLabel;
	private JPasswordField passwordField;
	private JTextField nameField;
	private JLabel nameJLabel;

	private JButton registerButton;
	private JCheckBox advancedTutorCheckBox;
	private FileChooserButton fileChooserButton;
	private JLabel portraitJLabel;
	private ImageIcon portraitImageIcon;

	/**
	 * Creates a TutorRegisterPanel
	 * 
	 * @see TutorRegisterPanel
	 */
	public TutorRegisterPanel() {

		setLayout(new GridLayout(6, 2, 10, 10));

		LinkedList<JTextField> textFields = new LinkedList<>();

		usernameJLabel = new JLabel("Username:");
		usernameField = new JTextField();
		textFields.add(usernameField);
		passwordJLabel = new JLabel("Password:");
		passwordField = new JPasswordField();
		textFields.add(passwordField);
		nameJLabel = new JLabel("Name: ");
		nameField = new JTextField();
		textFields.add(usernameField);

		advancedTutorCheckBox = new JCheckBox();
		advancedTutorCheckBox.setText("A Level Tutor");
		setPortraitImageIcon((new ImageIcon(getClass().getResource("Portrait_Placeholder.png"))));
		portraitJLabel = new JLabel(getPortraitImageIcon());
		fileChooserButton = new FileChooserButton(this, null);
		fileChooserButton.setText("Choose File");

		new JLabel(new ImageIcon(getClass().getResource("Portrait_Placeholder.png")));

		registerButton = new JButton("Register as Tutor");
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();

				for (Tutor tutor : TutoringCenterSystem.getInstance().getTutors()) {
					if (tutor.getName().equals(username)) {
						JOptionPane.showMessageDialog(null, "A tutor with an identical username exists", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

				}
				for (JTextField textField : textFields) {
					if (textField.getText() == null || textField.getText() == "") {
						JOptionPane.showMessageDialog(null, "You have left at least one of the forms empty", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				Tutor tutor;
				if (advancedTutorCheckBox.isSelected()) {
					tutor = new ALevelTutor(usernameField.getText(), new String(passwordField.getPassword()),
							nameField.getText());

				} else {
					tutor = new BLevelTutor(usernameField.getText(), new String(passwordField.getPassword()),
							nameField.getText());

				}

				tutor.setPortrait(fileChooserButton.getImage());
				JOptionPane
						.showMessageDialog(null,
								String.format("Successfully created tutor!%nYour cut percentage: %.2f",
										tutor.getCutPercentage() * 100),
								"Information", JOptionPane.INFORMATION_MESSAGE);
				UserInterfaceDataHelper.callWelcomePanel();
			}
		});

		backButton = new BackButton();

		add(usernameJLabel);
		add(usernameField);
		add(passwordJLabel);
		add(passwordField);
		add(nameJLabel);
		add(nameField);

		add(new JLabel("Select Tutor Level: "));
		add(advancedTutorCheckBox);
		add(fileChooserButton);
		add(portraitJLabel);
		add(registerButton);
		add(backButton);

	}

	public ImageIcon getPortraitImageIcon() {
		return portraitImageIcon;
	}

	public void setPortraitImageIcon(ImageIcon portraitImageIcon) {
		this.portraitImageIcon = portraitImageIcon;
	}

	public JLabel getPortraitJLabel() {
		return portraitJLabel;
	}

	public void setPortraitJLabel(JLabel imageJLabel) {
		this.portraitJLabel = imageJLabel;
	}
}
