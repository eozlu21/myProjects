package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import money.Money;
import student.Student;
import tutoringCenterSystem.TutoringCenterSystem;

/**
 * This panel allows user to create a new Student object
 * 
 * @author egeozlu
 *
 */
public class StudentRegisterPanel extends JPanel {

	private BackButton backButton;
	private JLabel usernameJLabel;
	private JTextField usernameField;
	private JLabel passwordJLabel;
	private JPasswordField passwordField;
	private JTextField nameField;
	private JLabel nameJLabel;
	private JLabel tcknLabel;
	private JTextField tcknField;
	private JLabel genderLabel;
	private JTextField genderField;
	private JLabel ageLabel;
	private JTextField ageField;
	private JLabel balanceJLabel;
	private JTextField balanceField;
	private JButton registerButton;

	/**
	 * Creates a new StudentRegisterPanel
	 * 
	 * @see StudentRegisterPanel
	 */
	public StudentRegisterPanel() {
		setLayout(new GridLayout(8, 2, 10, 10));

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
		tcknLabel = new JLabel("TCKN: ");
		tcknField = new JTextField();
		textFields.add(tcknField);
		genderLabel = new JLabel("Gender:");
		genderField = new JTextField();
		textFields.add(genderField);
		ageLabel = new JLabel("Age: ");
		ageField = new JTextField();
		textFields.add(ageField);
		balanceJLabel = new JLabel("Set up Initial Balance In TL: ");
		balanceField = new JTextField();
		textFields.add(balanceField);

		registerButton = new JButton("Register as Student");
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				for (Student student : TutoringCenterSystem.getInstance().getStudents()) {
					if (student.getName().equals(username)) {
						JOptionPane.showMessageDialog(null, "A student with an identical username exists", "Error",
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
					if (ageField.equals(textField) || balanceField.equals(textField) || tcknField.equals(textField)) {
						try {
							long number = Long.parseLong(textField.getText());
							if (number < 0) {
								JOptionPane.showMessageDialog(null,
										"Please enter an positive integer as your age value/balance/TCKN", "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null,
									"Please enter an positive integer as your age value/balance/TCKN", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (tcknField.equals(textField) && tcknField.getText().length() != 11) {
							JOptionPane.showMessageDialog(null, "TCKN must be 11 digits long", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}

				Student student = new Student(usernameField.getText(), new String(passwordField.getPassword()),
						nameField.getText(), tcknField.getText(), genderField.getText(),
						Integer.parseInt(ageField.getText()));
				student.setBalance(new Money(Integer.parseInt(balanceField.getText()) * 100));
				JOptionPane.showMessageDialog(null, "Successfully created student!", "Information",
						JOptionPane.INFORMATION_MESSAGE);
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
		add(tcknLabel);
		add(tcknField);

		add(genderLabel);
		add(genderField);

		add(ageLabel);
		add(ageField);

		add(balanceJLabel);
		add(balanceField);
		add(registerButton);
		add(backButton);

	}

}
