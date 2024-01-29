package ui;

import java.awt.Component;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import admin.Admin;
import course.Course;
import student.Student;
import tutor.Tutor;
import user.User;

/**
 * This class is responsible for what the GUI displays at any point. It
 * communicates with the Panels using UserInterfaceDataHelper
 * 
 * @author egeozlu
 * @see UserInterfaceDataHelper
 */
public class TutoringCenterUI extends JFrame {

	private static TutoringCenterUI single_instance = null;
	/**
	 * This field remembers which panels have been shown to the user, and
	 * communicates with the back button using UserInterfaceDataHelper
	 * 
	 * @see BackButton
	 */
	private LinkedList<JPanel> panelHistory = new LinkedList<>();

	/**
	 * This overridden add method has the additional property of also adding the
	 * panel to panelHistory
	 */

	@Override
	public Component add(Component comp) {
		if (panelHistory.size() != 0 && panelHistory.getLast().getClass() == comp.getClass()) {
			panelHistory.removeLast();
		}
		panelHistory.add((JPanel) comp);
		return super.add(comp);
	}

	/**
	 * Creates the GUI. After showing the WelcomePanel, communicates with
	 * UserInterFaceDataHelper to know which panel to show by using showXPanel()
	 * methods.
	 * 
	 * @see UserInterfaceDataHelper
	 */
	public TutoringCenterUI() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(200, 200, 700, 800);
		showWelcomePanel();
	}

	public void showStudentPanel(Student student) {
		getContentPane().removeAll();
		StudentPanel studentPanel = new StudentPanel(student);
		add(studentPanel);

		setTitle("Student Menu");
		setVisible(true);
	}

	public void showLoginPanel() {
		getContentPane().removeAll();
		LoginPanel loginPanel = new LoginPanel();
		add(loginPanel);
		setTitle("Login Menu");

		setVisible(true);
	}

	public void showReserveSessionPanel(Student student, Course course) {
		getContentPane().removeAll();
		ReserveSessionPanel reserveSessionPanel = new ReserveSessionPanel(student, course);
		add(reserveSessionPanel);
		setTitle("Session Reservation Menu");

		setVisible(true);

	}

	public void showTutorPanel(Tutor user) {
		getContentPane().removeAll();
		TutorPanel tutorPanel = new TutorPanel(user);
		add(tutorPanel);
		setTitle("Tutor Menu");

		setVisible(true);
	}

	public void showAdminPanel(Admin user) {
		getContentPane().removeAll();
		AdminPanel adminPanel = new AdminPanel(user);
		add(adminPanel);
		setTitle("Admin Menu");
		setVisible(true);
	}

	public void showWelcomePanel() {
		getContentPane().removeAll();
		add(new WelcomePanel());
		setTitle("Tutoring Center");
		setVisible(true);
	}

	public static TutoringCenterUI getInstance() {
		if (single_instance == null) {
			return new TutoringCenterUI();
		}
		return single_instance;
	}

	public void showPersonalInfoTablePanel(User user) {
		getContentPane().removeAll();
		add(new PersonalInfoPanel(user));
		setTitle("Personal Information");
		setVisible(true);
	}

	public void showOrganizeSchedulePanel(Tutor tutor) {
		getContentPane().removeAll();
		add(new OrganizeSchedulePanel(tutor));
		setTitle("Schedule");
		setVisible(true);

	}

	public void showStudentInfoTablePanel(User user, Course course) {
		getContentPane().removeAll();
		add(new StudentInfoTablePanel(user, course));
		setTitle("Student Info");
		setVisible(true);

	}

	public void showCourseCreationPanel(Admin admin) {
		getContentPane().removeAll();
		add(new CourseCreationEditPanel(admin));
		setTitle("Create New Course");
		setVisible(true);
	}

	public void showStudentRegisterPanel() {
		getContentPane().removeAll();
		add(new StudentRegisterPanel());
		setTitle("Register as a student");
		setVisible(true);
	}

	public void showTutorRegisterPanel() {
		getContentPane().removeAll();
		add(new TutorRegisterPanel());
		setTitle("Register as a tutor");
		setVisible(true);

	}

	public void showAdminRegisterPanel() {
		getContentPane().removeAll();
		add(new AdminRegisterPanel());
		setTitle("Register as a admin");
		setVisible(true);

	}

	public void goPreviousPanel() {
		panelHistory.getLast().setVisible(false);
		panelHistory.removeLast();
		super.add(panelHistory.getLast());
		setVisible(true);
	}

}
