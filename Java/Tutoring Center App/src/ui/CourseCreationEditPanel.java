package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import admin.Admin;
import course.AdvancedCourse;
import course.BeginnerCourse;
import course.Course;
import course.CourseHelper;
import equipment.Equipment;
import equipment.EquipmentHelper;
import tutoringCenterSystem.TutoringCenterSystem;

/**
 * This panel is only accessible by an Admin and allows the user to create a new
 * Course. The Course created here only appears when the admin logs out.
 * 
 * @author egeozlu
 *
 */
public class CourseCreationEditPanel extends JPanel {

	private BackButton backButton;
	private JLabel nameLabel;
	private JLabel infoLabel;
	private JTextField nameField;
	private JComboBox<String> courseTypeBox;
	private JButton createCourseButton;
	private LinkedList<JCheckBox> preReqCheckBoxs = new LinkedList<>();
	private JComboBox<String> equipmentComboBox;
	private JButton addEquipmentButton;
	private LinkedList<Equipment> equipmentsRequired = new LinkedList<>();
	TutoringCenterSystem system = TutoringCenterSystem.getInstance();

	/**
	 * This creates a CourseCreationEditPanel
	 * 
	 * @param admin
	 * @see CourseCreationEditPanel
	 */
	public CourseCreationEditPanel(Admin admin) {

		setLayout(new GridLayout(4 + (TutoringCenterSystem.getInstance().getCourses().size() + 1) / 2, 2));

		backButton = new BackButton();
		infoLabel = new JLabel("Select Course Type:");
		courseTypeBox = new JComboBox<>();
		ArrayList<String> list = new ArrayList<>();
		list.add("Advanced Course");
		list.add("Beginner Course");

		courseTypeBox.setModel(new DefaultComboBoxModel<>(list.toArray(new String[0])));

		nameLabel = new JLabel();
		nameLabel.setText("Enter Course Name: (If you want to edit a course, just type its name)");

		nameField = new JTextField();

		LinkedList<String> courses = new LinkedList<>();
		system.getCourses().forEach(
				c -> courses.add(c.toString() + (c instanceof BeginnerCourse ? " - Beginner" : " - Advanced")));
		for (String course : courses) {
			JCheckBox preReqOption = new JCheckBox(course);
			preReqCheckBoxs.add(preReqOption);
		}

		createCourseButton = new JButton("Create Course");
		createCourseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isNewCourse = CourseHelper.getCourseWithGivenName(nameField.getText()) == null;

				if (courseTypeBox.getSelectedItem().toString().startsWith("B")) {
					for (JCheckBox optionBox : preReqCheckBoxs) {
						if (!optionBox.isSelected()) {
							continue;
						}
						if (CourseHelper.getCourseWithGivenName(optionBox.getText()) instanceof AdvancedCourse) {
							JOptionPane.showMessageDialog(null,
									"You cannot add an advanced course as a prerequisite to a beginner course", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				}
				LinkedList<Course> selectedPreReqCourses = new LinkedList<>();
				preReqCheckBoxs.forEach(c -> {
					if (c.isSelected()) {
						selectedPreReqCourses.add(CourseHelper.getCourseWithGivenName(c.getText()));
					}
				});
				Course course;
				if (!isNewCourse) {
					course = CourseHelper.getCourseWithGivenName(nameField.getText());
					course.setPreRequisites(selectedPreReqCourses);
					course.setRequiredEquipments(equipmentsRequired);

				} else {
					if (courseTypeBox.getSelectedItem().toString().startsWith("A")) {
						course = new AdvancedCourse(nameField.getText(), selectedPreReqCourses, equipmentsRequired);
					} else {
						course = new BeginnerCourse(nameField.getText(), selectedPreReqCourses, equipmentsRequired);
					}
				}
				JOptionPane.showMessageDialog(null, String.format("Successfully %s course: %n%s",
						isNewCourse ? "created" : "edited", course.getInfoForTutor()), "Information",
						JOptionPane.INFORMATION_MESSAGE);

				equipmentsRequired = new LinkedList<>();
				UserInterfaceDataHelper.goBack();
			}
		});

		equipmentComboBox = new JComboBox<>();
		system.getEquipments().forEach(c -> equipmentComboBox.addItem(c.toString()));

		addEquipmentButton = new JButton("Add/Remove Equipment");
		addEquipmentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedEquipment = equipmentComboBox.getSelectedItem().toString();
				if (selectedEquipment.endsWith("- Added")) {
					equipmentsRequired.remove(EquipmentHelper.getEquipmentFromName(selectedEquipment));

					JOptionPane.showMessageDialog(null, "Successfully removed equipment from requirements",
							"Information", JOptionPane.INFORMATION_MESSAGE);
					updatePanelComponents();
					repaint();
					return;
				}
				equipmentsRequired.add(EquipmentHelper.getEquipmentFromName(selectedEquipment));
				JOptionPane.showMessageDialog(null, "Successfully added equipment to requirements", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				updatePanelComponents();
				repaint();
			}
		});

		add(nameLabel);
		add(nameField);
		add(infoLabel);
		add(courseTypeBox);

		preReqCheckBoxs.forEach(c -> add(c));
		add(equipmentComboBox);
		add(addEquipmentButton);
		add(createCourseButton);
		add(backButton);

	}

	private void updatePanelComponents() {
		updateEquipmentComboBox();
	}

	private void updateEquipmentComboBox() {
		((DefaultComboBoxModel<String>) equipmentComboBox.getModel()).removeAllElements();
		for (Equipment equipment : system.getEquipments()) {
			if (equipmentsRequired.contains(equipment)) {

				((DefaultComboBoxModel<String>) equipmentComboBox.getModel())
						.addElement(equipment.toString() + " - Added");
			} else {
				((DefaultComboBoxModel<String>) equipmentComboBox.getModel()).addElement(equipment.toString());
			}
		}
		equipmentComboBox.repaint();
	}

}
