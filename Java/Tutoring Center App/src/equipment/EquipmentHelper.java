package equipment;

import java.util.LinkedList;

import course.Course;
import course.CourseHelper;
import money.Money;
import student.Student;
import tutoringCenterSystem.TutoringCenterSystem;

/**
 * Contains methods that are directly related to a status of an equipment
 * 
 * @author egeozlu
 *
 */
public class EquipmentHelper {

	/**
	 * Adds the Equipment to Student's equipment list and also adds the price of the
	 * Equipment to Student's missing payments
	 * 
	 * @param student
	 * @param equipment
	 * @see student.Student#missingPayments
	 */
	public static void buyEquipment(Student student, Equipment equipment) {
		addEquipmentToStudent(student, equipment);
		Money.add(student.getMissingPayments(), equipment.getPrice());
		// student.getSystem().addRevenue(Money.product(equipment.getPrice(),
		// equipment.getPercentageToSystem()));
	}

	/**
	 * Adds the Equipment to Student's equipment list
	 * 
	 * @param student
	 * @param equipment
	 */
	public static void addEquipmentToStudent(Student student, Equipment equipment) {
		student.getEquipments().add(equipment);
	}

	/**
	 * Retrieves the Equipment with the given name
	 * 
	 * @param name
	 * @return Equipment with the given name
	 */
	public static Equipment getEquipmentFromName(String name) {
		TutoringCenterSystem system = TutoringCenterSystem.getInstance();
		LinkedList<Equipment> equipments = system.getEquipments();
		for (Equipment equipment : equipments) {
			if (name.startsWith(equipment.getName())) {
				return equipment;
			}
		}
		return null;
	}

	/**
	 * Retrieves all the missing Equipments of a Student for a Course and buys them
	 * one by one
	 * 
	 * @param student
	 * @param course
	 */

	public static void findAndBuyMissingEquipments(Student student, Course course) {
		LinkedList<Equipment> equipments = CourseHelper.getMissingEquipments(course, student);
		for (Equipment equipment : equipments) {
			buyEquipment(student, equipment);
			Money.add(student.getNewBoughtEquipment(),
					Money.product(equipment.getPrice(), equipment.getPercentageToSystem()));
		}
	}

}
