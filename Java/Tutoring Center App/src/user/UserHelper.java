package user;

import java.util.ArrayList;

import javax.swing.JTextField;

import admin.Admin;
import student.Student;
import tutor.Tutor;
import tutoringCenterSystem.TutoringCenterSystem;

/**
 * Contains User related methods
 * 
 * @author egeozlu
 *
 */
public class UserHelper {

	private static TutoringCenterSystem system = TutoringCenterSystem.getInstance();

	public enum UserTypes {
		ADMIN, TUTOR, STUDENT
	}

	/**
	 * Registers the student by calling this method
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @param tckn
	 * @param gender
	 * @param age
	 */
	public static void registerStudent(String username, String password, String name, String tckn, String gender,
			int age) {
		Student student = new Student(username, password, name, tckn, gender, age);

	}

	/**
	 * Logs the Student in with the given username and password
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static Student loginStudent(String username, String password) {
		for (Student student : system.getStudents()) {
			if (!student.getUsername().equals(username) || !student.getPassword().equals(password)) {
				continue;
			}
			return student;
		}
		return null;
	}

	/**
	 * Logs the Tutor in with the given username and password
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static Tutor loginTutor(String username, String password) {
		for (Tutor tutor : system.getTutors()) {
			if (!tutor.getUsername().equals(username) || !tutor.getPassword().equals(password)) {
				continue;
			}
			return tutor;
		}
		return null;
	}

	/**
	 * Logs the Admin in with the given username and password
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static Admin loginAdmin(String username, String password) {
		for (Admin admin : system.getAdmins()) {
			if (!admin.getUsername().equals(username) || !admin.getUsername().equals(username)) {
				continue;
			}
			return admin;
		}
		return null;
	}

	/**
	 * Checks what type of User is logging in, and calls the appropriate method
	 * 
	 * @param username
	 * @param password
	 * @param userType
	 * @return
	 * @see #loginStudent
	 * @see #loginTutor
	 * @see #loginAdmin
	 */
	public static User login(String username, String password, String userType) {
		switch (userType) {
		case ("Student"):
			return UserHelper.loginStudent(username, password);
		case ("Tutor"):
			return UserHelper.loginTutor(username, password);
		case ("Admin"):
			return UserHelper.loginAdmin(username, password);
		default:
			return null;
		}
	}

	/**
	 * Sets the new student info from the info taken from personal info panel
	 * 
	 * @param student
	 * @param info
	 */
	public static void setNewStudentInfo(Student student, JTextField... info) {
		ArrayList<String> infoArray = new ArrayList<>();
		for (JTextField infoArea : info) {
			String infoText = infoArea.getText();
			infoArray.add(infoText);
		}
		student.setUsername(infoArray.get(0));
		student.setName(infoArray.get(1));
		student.setAge(Integer.parseInt(infoArray.get(2)));
	}

	/**
	 * Sets the new Tutor info from the info taken from personal info panel
	 * 
	 * @param tutor
	 * @param info
	 */
	public static void setNewTutorInfo(Tutor tutor, JTextField... info) {
		ArrayList<String> infoArray = new ArrayList<>();
		for (JTextField infoArea : info) {
			String infoText = infoArea.getText();
			infoArray.add(infoText);
		}
		tutor.setUsername(infoArray.get(0));
		tutor.setName(infoArray.get(1));
		tutor.setId(infoArray.get(2));
	}
}
