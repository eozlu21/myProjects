package course;

import java.util.LinkedList;

import equipment.Equipment;

/**
 * A course subclass that cannot have an advanced course as its prerequisite.
 * 
 * @author Ege Erdem Özlü
 * @see Course
 */
public class BeginnerCourse extends Course {

	/**
	 * Creates a course that cannot have an advanced course as its prerequisite.
	 * 
	 * @param name               Name of the course
	 * @param preRequisites      Prerequisites of the course. Set null if there is
	 *                           none.
	 * @param requiredEquipments Required equipments of the course. Set null if
	 *                           there is none.
	 * @see Course
	 */
	public BeginnerCourse(String name, LinkedList<Course> preRequisites, LinkedList<Equipment> requiredEquipments) {
		super(name, preRequisites, requiredEquipments);
	}

}
