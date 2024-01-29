package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import admin.Admin;
import course.Course;
import course.CourseHelper;
import tutor.Tutor;
import tutoringCenterSystem.TutoringCenterSystem;

/**
 * This panel is the Panel that is displayed when an Admin logs in. It stores
 * every action and label that the Admin may need.
 * 
 * @author egeozlu
 *
 */
public class AdminPanel extends JPanel {

	private JLabel revenueLabel;
	private JButton makePaymentButton;
	private JButton studentInfoButton;
	private JComboBox<Course> courseBox;
	private JButton addCourseButton;
	private JButton courseInfoButton;
	private LogOutButton backButton;
	private JComboBox<Tutor> tutorBox;
	private JButton tutorInfoButton;
	private JButton tutorScheduleButton;
	private JButton studentScheduleButton;

	/**
	 * Creates a new Admin panel
	 * 
	 * @param admin
	 * @see AdminPanel
	 */
	public AdminPanel(Admin admin) {

		backButton = new LogOutButton();

		setLayout(new GridLayout(6, 2));
		revenueLabel = new JLabel();
		revenueLabel.setText("Revenue : " + admin.getSystem().getRevenue());

		makePaymentButton = new JButton("Finalize Payments");
		makePaymentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String message = admin.makePayments();
				JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
				updatePanelComponents(admin);
				repaint();
			}
		});

		studentInfoButton = new JButton("Get Student Info");
		studentInfoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserInterfaceDataHelper.callStudentInfoTablePanel(admin,
						CourseHelper.getCourseWithGivenName(courseBox.getSelectedItem().toString()));
			}
		});

		LinkedList<Course> courses = TutoringCenterSystem.getInstance().getCourses();
		courseBox = new JComboBox<>((courses.toArray(new Course[courses.size()])));

		courseInfoButton = new JButton("Get Course Info");
		courseInfoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Course course = (Course) courseBox.getSelectedItem();
				String info = course.getInfoForAdmin();
				JOptionPane.showMessageDialog(null, info, "Information", JOptionPane.INFORMATION_MESSAGE);

			}
		});

		addCourseButton = new JButton("Edit/Add Course");
		addCourseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserInterfaceDataHelper.callCourseCreationPanel(admin);
			}
		});

		tutorBox = new JComboBox<>();
		TutoringCenterSystem.getInstance().getTutors().forEach(c -> tutorBox.addItem(c));
		tutorInfoButton = new JButton("Get Tutor Info");
		tutorInfoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, ((Tutor) tutorBox.getSelectedItem()).getInfo(), "Tutor Info",
						JOptionPane.INFORMATION_MESSAGE);

			}
		});

		tutorScheduleButton = new JButton("Get Tutor Schedule");
		tutorScheduleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserInterfaceDataHelper.callOrganizeSchedulePanel((Tutor) tutorBox.getSelectedItem());
			}
		});

		studentScheduleButton = new JButton("Get Schedule Info for Course");
		studentScheduleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserInterfaceDataHelper.callReserveSessionPanel(null, (Course) courseBox.getSelectedItem());

			}
		});

		add(revenueLabel);
		add(makePaymentButton);
		add(tutorBox);
		add(tutorInfoButton);
		add(courseBox);
		add(addCourseButton);
		add(courseInfoButton);
		add(studentScheduleButton);
		add(studentInfoButton);
		add(tutorScheduleButton);
		add(backButton);
	}

	private void updatePanelComponents(Admin admin) {
		revenueLabel.setText("Revenue : " + admin.getSystem().getRevenue());
	}

}
