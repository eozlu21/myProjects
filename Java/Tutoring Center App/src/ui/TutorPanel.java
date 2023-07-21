package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import course.AdvancedCourse;
import course.Course;
import course.CourseHelper;
import student.Student;
import tutor.BLevelTutor;
import tutor.Tutor;
import tutoringCenterSystem.TutoringCenterSystem;
import validator.Validator;

/**
 * This panel is the Panel responsible for every action a Tutor might want to do
 * 
 * @author egeozlu
 *
 */
public class TutorPanel extends JPanel {

	private JLabel balanceLabel;
	private JButton studentInfoButton;
	private JButton personalInfoButton;
	private JComboBox<String> courseBox;
	private JButton addCourseButton;
	private JButton getCourseInfoButton;
	private JButton createSessionButton;
	private LogOutButton backButton;

	/**
	 * Creates a TutorPanel for the given Tutor
	 * 
	 * @param tutor
	 * @see TutorPanel
	 */
	public TutorPanel(Tutor tutor) {
		TutoringCenterSystem system = TutoringCenterSystem.getInstance();
		setLayout(new GridLayout(2, 4, 15, 15));

		backButton = new LogOutButton();
		balanceLabel = new JLabel("Balance: " + tutor.getBalance());

		studentInfoButton = new JButton("Get Student Info");
		studentInfoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean studentExists = false;
				for (Student student : system.getStudents()) {
					if (StudentInfoTablePanel.hasCommonElement(student.getRegisteredCourses(), tutor.getCourses())) {
						studentExists = true;
						break;
					}
				}
				if (!studentExists) {
					JOptionPane.showMessageDialog(null, "No student is registered to a course you are teaching.",
							"Cannot Show Info", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				UserInterfaceDataHelper.callStudentInfoTablePanel(tutor, null);
			}
		});

		personalInfoButton = new JButton("Get Personal Info");
		personalInfoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserInterfaceDataHelper.callPersonalInfoTable(tutor);

			}
		});
		LinkedList<String> courses = tutor.getCourseInfo();
		courseBox = new JComboBox<>(courses.toArray(new String[courses.size()]));

		addCourseButton = new JButton("Change Course Status");
		addCourseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String courseName = (String) courseBox.getSelectedItem();
				Course course = CourseHelper.getCourseWithGivenName(courseName);
				boolean willTeach = !endsWithTeaching(courseName);
				if (course instanceof AdvancedCourse && tutor instanceof BLevelTutor) {
					JOptionPane.showMessageDialog(null,
							"You cannot teach this advanced course because you are a B level tutor", "Request Failed",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (!Validator.checkCourseCapacity(course, willTeach)) {
					JOptionPane.showMessageDialog(null,
							String.format("You cannot %s this course because its capacity is %s.",
									willTeach ? "teach" : "remove", willTeach ? "5" : "1"),
							"Request Failed", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (willTeach) {
					CourseHelper.addTutorToCourse(course, tutor);
				} else {
					CourseHelper.removeTutorFromCourse(course, tutor);
				}
				updatePanelComponents(tutor);
				JOptionPane.showMessageDialog(null,
						String.format("Successfuly %s.",
								willTeach ? "added course to teachable courses" : "removed course from taught courses"),
						"Success", JOptionPane.INFORMATION_MESSAGE);

			}
		});

		getCourseInfoButton = new JButton("Get Course Info");
		getCourseInfoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String courseName = (String) courseBox.getSelectedItem();
				Course course = CourseHelper.getCourseWithGivenName(courseName);
				String info = course.getInfoForTutor();
				JOptionPane.showMessageDialog(null, info, "Course Info", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		createSessionButton = new JButton("Organize Schedule");
		createSessionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserInterfaceDataHelper.callOrganizeSchedulePanel(tutor);
			}
		});

		add(balanceLabel);
		add(studentInfoButton);
		add(personalInfoButton);
		add(createSessionButton);
		add(courseBox);
		add(addCourseButton);
		add(getCourseInfoButton);
		add(backButton);

	}

	private static boolean endsWithTeaching(String str) {
		return str.endsWith("Teaching");
	}

	private void updatePanelComponents(Tutor tutor) {
		balanceLabel.setText("Balance: " + tutor.getBalance().toString());
		LinkedList<String> courses = tutor.getCourseInfo();
		courseBox.setModel(new DefaultComboBoxModel<>(courses.toArray(new String[courses.size()])));

	}

}
