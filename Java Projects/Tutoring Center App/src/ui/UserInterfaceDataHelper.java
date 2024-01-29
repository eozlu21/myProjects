package ui;

import admin.Admin;
import course.Course;
import student.Student;
import tutor.Tutor;
import user.User;

/**
 * This class is the communicator between the Panels and The mainUI class
 * (TutoringCenterUI) Uses callXPanel() methods to make the mainUI class show
 * the corresponding Panel
 * 
 * @author egeozlu
 */
public class UserInterfaceDataHelper {

	private static TutoringCenterUI tutoringCenterUI;

	public static void callUserPanel(User user) {
		if (user instanceof Student) {
			tutoringCenterUI.showStudentPanel((Student) user);
		} else if (user instanceof Tutor) {
			tutoringCenterUI.showTutorPanel((Tutor) user);
		} else if (user instanceof Admin) {
			tutoringCenterUI.showAdminPanel((Admin) user);
		}
		return;
	}

	/**
	 * Creates the TutoringCenterUI
	 * 
	 * @param initialTutoringCenterUI
	 * @see TutoringCenterUI
	 */
	public static void initTutoringCenterUi(TutoringCenterUI initialTutoringCenterUI) {
		tutoringCenterUI = initialTutoringCenterUI;
	}

	public static void callReserveSessionPanel(Student student, Course course) {
		tutoringCenterUI.showReserveSessionPanel(student, course);
	}

	public static void callRegisterPanel(int result) {
		switch (result) {
		case 0:
			tutoringCenterUI.showStudentRegisterPanel();
			return;
		case 1:
			tutoringCenterUI.showTutorRegisterPanel();
			return;
		case 2:
			tutoringCenterUI.showAdminRegisterPanel();
			return;
		}

	}

	public static void callLoginPanel() {
		tutoringCenterUI.showLoginPanel();

	}

	public static void callWelcomePanel() {
		tutoringCenterUI.showWelcomePanel();
	}

	public static void callStudentInfoTablePanel(User user, Course course) {
		tutoringCenterUI.showStudentInfoTablePanel(user, course);
	}

	public static void callPersonalInfoTable(User user) {
		tutoringCenterUI.showPersonalInfoTablePanel(user);

	}

	public static void callOrganizeSchedulePanel(Tutor tutor) {
		// TODO Auto-generated method stub
		tutoringCenterUI.showOrganizeSchedulePanel(tutor);
	}

	/**
	 * Communicator between BackButton and TutoringCenterUI
	 * 
	 * @see BackButton
	 * @see TutoringCenterUI
	 */
	public static void goBack() {
		tutoringCenterUI.goPreviousPanel();
	}

	public static void callCourseCreationPanel(Admin admin) {
		tutoringCenterUI.showCourseCreationPanel(admin);

	}

}
