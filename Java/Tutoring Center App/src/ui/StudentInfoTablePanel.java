package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import admin.Admin;
import course.Course;
import student.Student;
import tutor.Tutor;
import tutoringCenterSystem.TutoringCenterSystem;
import user.User;

/**
 * This panel gives an overview of the Students in a table
 * 
 * @author egeozlu
 *
 */
public class StudentInfoTablePanel extends JPanel {

	private BackButton backButton;

	/**
	 * Creates a StudentInfoTablePanel for the given user and Course
	 * 
	 * @param user
	 * @param course
	 * @see StudentInfoTablePanel
	 */
	public StudentInfoTablePanel(User user, Course course) {
		setLayout(new GridLayout(2, 1));
		backButton = new BackButton();
		TutoringCenterSystem system = TutoringCenterSystem.getInstance();
		String[] columnNames = { "Name", "Age", "Gender", "Equipments", "Course History" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		JTable table = new JTable(model);
		// Create a JScrollPane to wrap the JTable

		JScrollPane scrollPane = new JScrollPane(table);
		LinkedList<Student> students = new LinkedList<>();
		if (user instanceof Admin) {
			students = new LinkedList<>(system.getStudents());
			for (Student student : TutoringCenterSystem.getInstance().getStudents()) {
				if (!student.getRegisteredCourses().contains(course)) {
					students.remove(student);
				}
			}
		} else if (user instanceof Tutor) {

			for (Student student : system.getStudents()) {
				if (hasCommonElement(student.getRegisteredCourses(), ((Tutor) user).getCourses())) {
					students.add(student);
				}
			}
		}
		Collections.sort(students);
		for (Student student : students) {
			LinkedList<String> equipments = student.getEquipmentInfo();
			String equipmentString;
			if (equipments.size() == 0) {
				equipmentString = "N/A";
			} else {
				equipmentString = String.join(", ", equipments);
			}
			String[] rowInfo = { student.getName(), Integer.toString(student.getAge()), student.getGender(),
					equipmentString, String.join(", ", student.getTakenCoursesInfo()) };
			model.addRow(rowInfo);
		}
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);

		JButton getStudentCourseButton = new JButton("Get Course History");
		getStudentCourseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// JOptionPane.showMessageDialog(null, String.join("\n", ), "Course History",
				// JOptionPane.INFORMATION_MESSAGE);
			}
		});

		add(scrollPane);
		add(backButton);

	}

	public static boolean hasCommonElement(LinkedList<Course> list1, LinkedList<Course> list2) {
		Set<Course> set = new HashSet<>();
		for (Course element : list1) {
			set.add(element);
		}
		for (Course element : list2) {
			if (set.contains(element)) {
				return true;
			}
		}
		return false;
	}
}
