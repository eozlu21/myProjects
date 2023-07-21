package tutoringCenterSystem;

import java.util.LinkedList;

import admin.Admin;
import course.Course;
import equipment.Equipment;
import money.Money;
import student.Student;
import tutor.Tutor;

/**
 * Acts like a database for the system. Stores all the Tutors, Students,
 * Courses, and Admins
 * 
 * @author egeozlu
 * @see tutor.Tutor
 * @see student.Student
 * @see course.course
 * @see admin.Admin
 */
public class TutoringCenterSystem {

	private LinkedList<Course> availableCourses = new LinkedList<>();
	private LinkedList<Student> students = new LinkedList<>();
	private LinkedList<Tutor> tutors = new LinkedList<>();
	private LinkedList<Admin> admins = new LinkedList<>();
	private Money revenue = new Money(0);
	private static TutoringCenterSystem single_instance = null;
	private Money equipmentRevenueOnQueue = new Money(0);
	private Money tutorCutRevenueOnQueue = new Money(0);
	private LinkedList<Equipment> equipments = new LinkedList<>();

	/**
	 * @see #getInstance()
	 */
	private TutoringCenterSystem() {
	}

	/**
	 * This method ensures that the database is a singleton (cannot be instantiated
	 * more than once). Always call this method if this class needed.
	 * 
	 * @return The one and only instance of TutoringCenterSystem
	 */
	public static TutoringCenterSystem getInstance() {
		if (single_instance == null)
			single_instance = new TutoringCenterSystem();

		return single_instance;
	}

	public LinkedList<Course> getAvailableCourses() {
		return availableCourses;
	}

	public void setAvailableCourses(LinkedList<Course> availableCourses) {
		this.availableCourses = availableCourses;
	}

	public LinkedList<Student> getStudents() {
		return students;
	}

	public void setStudents(LinkedList<Student> students) {
		this.students = students;
	}

	public LinkedList<Tutor> getTutors() {
		return tutors;
	}

	public void setTutors(LinkedList<Tutor> tutor) {
		this.tutors = tutor;
	}

	public LinkedList<Admin> getAdmins() {
		return admins;
	}

	public void setAdmins(LinkedList<Admin> admins) {
		this.admins = admins;
	}

	public Money getRevenue() {
		return revenue;
	}

	public void setRevenue(Money revenue) {
		this.revenue = revenue;
	}

	public void addRevenue(Money newRevenue) {
		Money.add(getRevenue(), newRevenue);
	}

	public LinkedList<Course> getCourses() {
		return availableCourses;
	}

	public Money getTutorCutRevenueOnQueue() {
		return tutorCutRevenueOnQueue;
	}

	public void setTutorCutRevenueOnQueue(Money tutorCutRevenueOnQueue) {
		this.tutorCutRevenueOnQueue = tutorCutRevenueOnQueue;
	}

	public Money getEquipmentRevenueOnQueue() {
		return equipmentRevenueOnQueue;
	}

	public void setEquipmentRevenueOnQueue(Money equipmentRevenueOnQueue) {
		this.equipmentRevenueOnQueue = equipmentRevenueOnQueue;
	}

	public LinkedList<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(LinkedList<Equipment> equipments) {
		this.equipments = equipments;
	}

}
