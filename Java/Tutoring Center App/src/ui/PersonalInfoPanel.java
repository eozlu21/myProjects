package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import student.Student;
import tutor.Tutor;
import tutoringCenterSystem.TutoringCenterSystem;
import user.User;
import user.UserHelper;

/**
 * This panel is accessible to Users with HasPersonalInfo. Displays and allows
 * them to edit their personal information.
 * 
 * @author egeozlu
 * @see user.HasPersonalInfo
 */
public class PersonalInfoPanel extends JPanel {

	private BackButton backButton;
	private JLabel portraitJLabel;

	/**
	 * Creates a PersonalInfoPanel for the User. If user is an instance of Student,
	 * calls personalStudentInfoPanel, and calls personalTutorInfoPanel if user is
	 * an instance of a Tutor.
	 * 
	 * @param user
	 * @see #personalStudentInfoPanel
	 * @see #personalTutorInfoPanel
	 */
	public PersonalInfoPanel(User user) {
		backButton = new BackButton();
		if (user instanceof Student) {
			setLayout(new GridLayout(7, 2, 20, 20));
			personalStudentInfoPanel((Student) user);
		} else if (user instanceof Tutor) {
			setLayout(new GridLayout(6, 2, 20, 20));
			personalTutorInfoPanel((Tutor) user);
		}

		add(backButton);
	}

	/**
	 * Creates a PersonalInfoPanel for the given Student
	 * 
	 * @param student
	 */
	public void personalStudentInfoPanel(Student student) {

		JButton infoEditModeButton;
		JTextField ageArea;
		JTextField usernameArea;
		JTextField nameArea;
		JLabel ageLabel;
		JLabel usernameLabel;
		JLabel nameLabel;
		JLabel tCKNLabel;
		JLabel genderLabel;
		JLabel equipmentLabel;
		JButton equipmentInfoLabel;

		usernameLabel = new JLabel();
		usernameLabel.setText("Username: ");

		usernameArea = new JTextField();
		usernameArea.setText(student.getUsername());
		usernameArea.setEditable(false);

		nameLabel = new JLabel();
		nameLabel.setText("Name: ");

		nameArea = new JTextField();
		nameArea.setText(student.getName());
		nameArea.setEditable(false);

		ageLabel = new JLabel();
		ageLabel.setText("Age: ");

		ageArea = new JTextField();
		ageArea.setText(Integer.toString(student.getAge()));

		genderLabel = new JLabel();
		genderLabel.setText(student.getGender().equals("female") ? "Gender: Female" : "Gender: Male");

		tCKNLabel = new JLabel("TCKN: " + student.getTckn());
		infoEditModeButton = new JButton("Edit Info");

		equipmentLabel = new JLabel("Equipments: ");
		equipmentInfoLabel = new JButton("Click to get eq. info");
		equipmentInfoLabel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, String.join("\n", student.getEquipmentInfo()), "Information",
						JOptionPane.INFORMATION_MESSAGE);

			}
		});

		infoEditModeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameArea.isEditable()) {
					for (Student student1 : TutoringCenterSystem.getInstance().getStudents()) {
						if (usernameArea.getText().equals(student1.getUsername()) && !student1.equals(student)) {
							JOptionPane.showMessageDialog(null, "Another student with the same username exists",
									"Error", JOptionPane.ERROR_MESSAGE);
							return;

						}
					}
					UserHelper.setNewStudentInfo(student, usernameArea, nameArea, ageArea);
				}
				changeInfoEditable(usernameArea, nameArea, ageArea);
			}
		});
		add(new FileChooserButton(this, student));
		portraitJLabel = new JLabel(new ImageIcon(student.getPortrait()));

		add(portraitJLabel);
		add(usernameLabel);
		add(usernameArea);
		add(nameLabel);
		add(nameArea);
		add(ageLabel);
		add(ageArea);
		add(tCKNLabel);
		add(genderLabel);
		add(equipmentLabel);
		add(equipmentInfoLabel);
		add(infoEditModeButton);
	}

	/**
	 * Creates a PersonalInfoPanel for given Tutor
	 * 
	 * @param tutor
	 */
	public void personalTutorInfoPanel(Tutor tutor) {

		JButton infoEditModeButton = new JButton("Edit Info");
		JLabel nameLabel = new JLabel();
		JLabel usernameLabel = new JLabel();
		JLabel leveLabel = new JLabel();
		JLabel idLabel = new JLabel();
		JTextField nameTextArea = new JTextField();
		JTextField usernameTextArea = new JTextField();
		JTextField idTextArea = new JTextField();
		JLabel cutPercentageJLabel = new JLabel(
				String.format("Your percentage cut: %.2f", tutor.getCutPercentage() * 100));

		LinkedHashMap<String, String> personalInfo = tutor.getPersonalInfo();
		nameLabel.setText("Name :");
		usernameLabel.setText("Username: ");
		leveLabel.setText("Level: " + personalInfo.get("Tutor Level: "));
		idLabel.setText("ID: ");
		nameTextArea.setText(personalInfo.get("Name: "));
		nameTextArea.setEditable(false);
		usernameTextArea.setText(personalInfo.get("Username: "));
		usernameTextArea.setEditable(false);
		idTextArea.setText(personalInfo.get("ID: "));
		idTextArea.setEditable(false);

		infoEditModeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameTextArea.isEditable()) {
					for (Tutor tutor1 : TutoringCenterSystem.getInstance().getTutors()) {
						if (usernameTextArea.getText().equals(tutor1.getUsername()) && !tutor1.equals(tutor)) {
							JOptionPane.showMessageDialog(null, "Another tutor with the same username exists", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;

						}
					}
					UserHelper.setNewTutorInfo(tutor, usernameTextArea, nameTextArea, idTextArea);
				}
				changeInfoEditable(nameTextArea, usernameTextArea, idTextArea);
			}
		});

		add(new FileChooserButton(this, tutor));
		portraitJLabel = new JLabel(new ImageIcon(tutor.getPortrait()));
		add(portraitJLabel);
		add(usernameLabel);
		add(usernameTextArea);
		add(nameLabel);
		add(nameTextArea);
		add(idLabel);
		add(idTextArea);
		add(leveLabel);
		add(cutPercentageJLabel);
		add(infoEditModeButton);
	}

	public static void changeInfoEditable(JTextField... infoAreas) {
		boolean editability = false;
		if (!infoAreas[0].isEditable()) {
			editability = true;
		}
		for (JTextField infoArea : infoAreas) {
			infoArea.setEditable(editability);
		}
	}

	public JLabel getPortraitJLabel() {
		return portraitJLabel;
	}

	public void setPortraitJLabel(JLabel portraitJLabel) {
		this.portraitJLabel = portraitJLabel;
	}

}
