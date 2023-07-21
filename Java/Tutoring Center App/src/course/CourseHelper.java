package course;

import java.util.LinkedList;

import equipment.Equipment;
import exception.InvalidPreRequisiteException;
import exception.StudentAlreadyInCourseException;
import student.Student;
import tutor.Tutor;
import tutoringCenterSystem.TutoringCenterSystem;
import validator.Validator;

/**
 * Contains methods that are related to Course actions
 * 
 * @see Course
 * @author Ege Erdem Özlü
 *
 */
public class CourseHelper {

	/**
	 * Adds second Course as a prerequisite to the first Course
	 * 
	 * @param course      Course that will have a new prerequisite
	 * @param courseToAdd Course to be added as a prerequisite
	 */

	public static void addPreRequisite(Course course, Course courseToAdd) {
		try {
			Validator.validateCourseFeasibility(course, courseToAdd);
		}

		catch (InvalidPreRequisiteException e) {
			e.printStackTrace();
			return;
		}
		course.getPreRequisites().add(courseToAdd);
	}

	/**
	 * Registers Student to the Course and adds Student to the Course's registered
	 * students after making necessary checks.
	 * 
	 * @param course
	 * @param student
	 * @return Exception if error occurs, null otherwise
	 * @throws StudentAlreadyInCourseException
	 * @throws InvalidPreRequisiteException
	 */

	public static Exception addStudentToCourse(Course course, Student student) {
		try {
			Validator.validateCourseNotAlreadyTaken(course, student);
			Validator.validateCoursePreRequisites(course, student);
		}

		catch (StudentAlreadyInCourseException e) {
			return e;
		} catch (InvalidPreRequisiteException e) {
			return e;
		}
		student.getRegisteredCourses().add(course);
		course.getEnrolledStudents().add(student);

		return null;
	}

	/**
	 * Retrieves the Course with the given name
	 * 
	 * @param courseName Name of the course to retrieve
	 * @return Course with the name if it exists, null otherwise
	 */
	public static Course getCourseWithGivenName(String courseName) {
		TutoringCenterSystem system = TutoringCenterSystem.getInstance();
		for (Course course : system.getAvailableCourses()) {
			if (courseName.startsWith(course.getName())) {
				return course;
			}
		}
		return null;
	}

	/**
	 * Retrieves all the Equipments missing from the Student for that Course
	 * 
	 * @param course  Course
	 * @param student Student
	 * @return All the missing equipments as a LinkedList, the list will be empty if
	 *         no such equipment exists.
	 */
	public static LinkedList<Equipment> getMissingEquipments(Course course, Student student) {
		LinkedList<Equipment> requiredEquipments = new LinkedList<>(course.getRequiredEquipments());
		LinkedList<Equipment> studentEquipments = new LinkedList<>(student.getEquipments());
		requiredEquipments.removeAll(studentEquipments);
		return requiredEquipments;
	}

	/**
	 * Adds Tutor to the Course and adds Course to Tutor's Course list.
	 * 
	 * @param course
	 * @param tutor
	 */
	public static void addTutorToCourse(Course course, Tutor tutor) {
		course.getTutors().add(tutor);
		tutor.getCourses().add(course);
	}

	/**
	 * Removes Tutor from the Course and removes Course from Tutor's Course list.
	 * 
	 * @param course
	 * @param tutor
	 */
	public static void removeTutorFromCourse(Course course, Tutor tutor) {
		course.getTutors().remove(tutor);
		tutor.getCourses().remove(course);
	}

	/**
	 * Adds an Equipment requirement to the Course
	 * 
	 * @param course
	 * @param equipment
	 */
	public static void addEquipmentReqToCourse(Course course, Equipment equipment) {
		course.getRequiredEquipments().add(equipment);
	}

}
