package course;

import java.util.LinkedList;

import equipment.Equipment;

/**
 * A course subclass that can have any course as its prerequisite.
 * 
 * @author Ege Erdem Özlü
 * @see Course
 */

public class AdvancedCourse extends Course {

	/**
	 * Creates a course that can have any course as its prerequisite.
	 * 
	 * @param name               Name of the course
	 * @param preRequisites      Prerequisites of the course. Set null if there is
	 *                           none.
	 * @param requiredEquipments Required equipments of the course. Set null if
	 *                           there is none.
	 * @see Course
	 */

	public AdvancedCourse(String name, LinkedList<Course> preRequisites, LinkedList<Equipment> requiredEquipments) {
		super(name, preRequisites, requiredEquipments);
	}

}
