package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import course.Course;
import course.CourseHelper;
import equipment.Equipment;
import equipment.EquipmentHelper;
import money.Money;
import schedule.ScheduleHelper;
import schedule.Session;
import student.Student;
import validator.Validator;

/**
 * This panel is the Panel responsible for every action a Student might want to
 * do
 * 
 * @author egeozlu
 *
 */
public class StudentPanel extends JPanel {

	private JButton enrollCourseButton;
	private JComboBox<String> allCourses;
	private JLabel balanceLabel;
	private JButton reserveSessionButton;
	private LogOutButton backButton;
	private JButton makeDuePaymentsButton;
	private JButton getCourseInfoButton;

	/**
	 * Creates a StudentPanel for the given Student
	 * 
	 * @param student
	 * @see StudentPanel
	 */
	public StudentPanel(Student student) {

		setLayout(new GridLayout(8, 1, 10, 10));
		backButton = new LogOutButton();

		JButton testButton = new JButton("Get Personal Info");
		testButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserInterfaceDataHelper.callPersonalInfoTable(student);

			}
		});
		add(testButton);
		balanceLabel = new JLabel();
		setBalanceLabelText(student);
		add(balanceLabel);

		getCourseInfoButton = new JButton("Get Course Info");
		getCourseInfoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String courseName = (String) allCourses.getSelectedItem();
				Course course = CourseHelper.getCourseWithGivenName(courseName);
				String info = course.getInfoForTutor();
				JOptionPane.showMessageDialog(null, info, "Course Info", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		LinkedList<String> courses = student.getAllCoursesInfo();
		allCourses = new JComboBox<>(courses.toArray(new String[courses.size()]));

		enrollCourseButton = new JButton("Enroll Course");
		enrollCourseButton.setEnabled(false);
		enrollCourseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String courseName = (String) (allCourses.getSelectedItem());
				Course course = CourseHelper.getCourseWithGivenName(courseName);
				EquipmentHelper.findAndBuyMissingEquipments(student, course);
				CourseHelper.addStudentToCourse(course, student);
				enrollCourseButton.setText("Course Enrolled!");
				enrollCourseButton.setEnabled(false);
				updatePanelComponents(student);
				updateCourseBox(student);
				repaint();
			}
		});

		allCourses.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				updateCourseBox(student);

			}
		});

		reserveSessionButton = new JButton("Reserve Session");
		reserveSessionButton.setEnabled(false);
		reserveSessionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String courseName = (String) allCourses.getSelectedItem();
				Course course = CourseHelper.getCourseWithGivenName(courseName);
				LinkedList<Session> sessions = ScheduleHelper.getAvailableSessionsForCourse(student, course);
				if (!checkIfSessionAvailable(student, sessions)) {
					return;
				}
				UserInterfaceDataHelper.callReserveSessionPanel(student, course);
			}
		});

		makeDuePaymentsButton = new JButton("Make Due Payments");
		makeDuePaymentsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!Validator.validateTransaction(student.getBalance(), student.getMissingPayments())) {
					JOptionPane.showMessageDialog(null, "Transaction failed as you don't have enough money", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				student.makePayment();
				JOptionPane.showMessageDialog(null, "Payment successful", "Success!", JOptionPane.INFORMATION_MESSAGE);
				updatePanelComponents(student);
				repaint();
			}
		});

		updateCourseBox(student);
		add(allCourses);
		add(getCourseInfoButton);
		add(enrollCourseButton);
		add(reserveSessionButton);
		add(makeDuePaymentsButton);
		add(backButton);

		setVisible(true);

	}

	private void setBalanceLabelText(Student student) {
		balanceLabel.setText("Balance: " + student.getBalance().toString() + "    Waiting Payments: "
				+ student.getMissingPayments());
	}

	private Money checkMissingEquipment(Student student) {
		String selectedCourse = (String) allCourses.getSelectedItem();
		Course course = CourseHelper.getCourseWithGivenName(selectedCourse);
		LinkedList<Equipment> missingEquipments = CourseHelper.getMissingEquipments(course, student);
		Money missingMoney = new Money(0);
		for (Equipment equipment : missingEquipments) {
			Money.add(missingMoney, equipment.getPrice());
		}
		return missingMoney;
	}

	private void updatePanelComponents(Student student) {
		setBalanceLabelText(student);
		LinkedList<String> courses = new LinkedList<>();
		for (Course course : student.getSystem().getCourses()) {
			courses.add(student.studentAnyCourseToString(course));
		}
		if (balanceLabel.getText().endsWith(": 0,00 TL")) {
			makeDuePaymentsButton.setEnabled(false);
		} else {
			makeDuePaymentsButton.setEnabled(true);
		}
		allCourses.setModel(new DefaultComboBoxModel<>(courses.toArray(new String[courses.size()])));

	}

	private boolean checkIfSessionAvailable(Student student, LinkedList<Session> sessions) {
		if (sessions == null || sessions.size() == 0) {
			JOptionPane.showMessageDialog(null, "No tutors are available", "Warning", JOptionPane.WARNING_MESSAGE);
			UserInterfaceDataHelper.callUserPanel(student);
			return false;
		}
		return true;
	}

	private boolean checkIfCanBeEnrolled(String selectedCourse) {
		return (selectedCourse.matches(".*Available") || selectedCourse.matches(".*Missing Equipments")
				|| selectedCourse.matches(".*Failed"));

	}

	private boolean checkIfRegistered(String selectedCourse) {
		return selectedCourse.matches(".*Registered");
	}

	private void updateCourseBox(Student student) {
		String selectedCourse = (String) allCourses.getSelectedItem();
		reserveSessionButton.setEnabled(false);
		if (checkIfRegistered(selectedCourse)) {
			reserveSessionButton.setEnabled(true);
			enrollCourseButton.setText("Enroll Course");
			enrollCourseButton.setEnabled(false);
			return;
		}

		if (!checkIfCanBeEnrolled(selectedCourse)) {
			enrollCourseButton.setEnabled(false);
			return;
		}

		int missingEquipmentPrice = checkMissingEquipment(student).getTotalAmountInKurus();
		if (missingEquipmentPrice == 0) {
			enrollCourseButton.setText("Enroll Course");
			enrollCourseButton.setEnabled(true);
			return;
		}

		enrollCourseButton.setText("Enroll Course" + " (" + checkMissingEquipment(student).toString() + ")");
		if (student.getBalance().getTotalAmountInKurus() > missingEquipmentPrice) {
			enrollCourseButton.setEnabled(true);
			return;

		}

		enrollCourseButton.setText(enrollCourseButton.getText() + " (INSUFFICIENT FUNDS)");
		enrollCourseButton.setEnabled(false);
	}

}
