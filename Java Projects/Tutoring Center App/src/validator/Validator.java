package validator;

import course.AdvancedCourse;
import course.Course;
import exception.CostOutOfBoundsException;
import exception.InvalidPreRequisiteException;
import exception.SessionAlreadyCreatedException;
import exception.StudentAlreadyInCourseException;
import exception.StudentIsUnavailableException;
import exception.StudentNotEnrolledInCourseException;
import exception.UnavailableSessionException;
import money.Money;
import schedule.Session;
import student.Student;
import tutor.ALevelTutor;
import tutor.BLevelTutor;
import tutor.Tutor;

/**
 * Contains methods that validate various conditions
 * 
 * @author egeozlu
 *
 */
public class Validator {

	/**
	 * Checks if student has enough money to do transaction
	 * 
	 * @param buyerBalance Student's Money
	 * @param cost         Transaction amount
	 * @return true if transcation can be made, false otherwise
	 */
	public static boolean validateTransaction(Money buyerBalance, Money cost) {
		if (buyerBalance.getTotalAmountInKurus() < cost.getTotalAmountInKurus()) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if arg2 can be assigned as a prerequisite to arg1
	 * 
	 * @param course
	 * @param preReqCourse
	 * @throws InvalidPreRequisiteException
	 */
	public static void validateCourseFeasibility(Course course, Course preReqCourse)
			throws InvalidPreRequisiteException {
		if (course instanceof AdvancedCourse) {
			return;
		} else if (preReqCourse instanceof AdvancedCourse) {
			throw new InvalidPreRequisiteException("an advanced course cannot be a prerequisite to a beginner course.");
		}
	}

	/**
	 * Checks if the student is available for the given session
	 * 
	 * @param student
	 * @param hour
	 * @throws StudentIsUnavailableException
	 */
	public static void validateStudentAvailability(Student student, int hour) throws StudentIsUnavailableException {
		if (student.getUnavailableHours().contains(hour)) {
			throw new StudentIsUnavailableException("you already have a session");
		}
	}

	/**
	 * Checks if the tutor is available for the given hour
	 * 
	 * @param tutor
	 * @param hour
	 * @throws UnavailableSessionException
	 */
	public static void validateTutorAvailability(Tutor tutor, int hour) throws UnavailableSessionException {
		if (tutor.getSchedule().getHourSessionMap().get(hour).getStudent() != null) {
			throw new UnavailableSessionException("message PLACEHOLDER");
		}
	}

	/**
	 * Checks if the student can enroll to the course
	 * 
	 * @param student
	 * @param session
	 * @throws StudentNotEnrolledInCourseException
	 */
	public static void validateStudentEnrollmentToCourse(Student student, Session session)
			throws StudentNotEnrolledInCourseException {
		var registeredCourses = student.getRegisteredCourses();
		var sessionCourse = session.getCourse();
		if (!registeredCourses.contains(sessionCourse)) {
			throw new StudentNotEnrolledInCourseException("placeholder");
		}
	}

	/**
	 * Checks if the session is not already created
	 * 
	 * @param tutor
	 * @param hour
	 * @throws SessionAlreadyCreatedException
	 */
	public static void validateNewSession(Tutor tutor, int hour) throws SessionAlreadyCreatedException {
		Session session = tutor.getSchedule().getHourSessionMap().get(hour);
		if (session.getCost() != null) {
			throw new SessionAlreadyCreatedException("Placeholder");
		}
	}

	/**
	 * Checks if the session price is valid for the type of the Tutor.
	 * 
	 * @param tutor
	 * @param price
	 * @throws CostOutOfBoundsException
	 */
	public static void ValidateSessionPrice(Tutor tutor, Money price) throws CostOutOfBoundsException {
		final int MINIMUM_A_LEVEL_PRICE = 700;
		final int KURUS_IN_TL = 100;
		final int MAXIMUM_B_LEVEL_PRICE = 1000;
		if (tutor instanceof ALevelTutor && price.getTotalAmountInKurus() < MINIMUM_A_LEVEL_PRICE * KURUS_IN_TL) {
			throw new CostOutOfBoundsException("placeholder");
		}

		else if (tutor instanceof BLevelTutor && price.getTotalAmountInKurus() > MAXIMUM_B_LEVEL_PRICE * KURUS_IN_TL) {
			throw new CostOutOfBoundsException("placeholder");
		}
	}

	/**
	 * Checks if the student has passed the required prerequisites of the Course
	 * 
	 * @param courseToAdd
	 * @param student
	 * @throws InvalidPreRequisiteException
	 */
	public static void validateCoursePreRequisites(Course courseToAdd, Student student)
			throws InvalidPreRequisiteException {
		var passedCourses = student.getPassedCourses();
		var requiredCourses = courseToAdd.getPreRequisites();
		if (requiredCourses == null) {
			return;
		}
		if (!passedCourses.containsAll(requiredCourses)) {
			throw new InvalidPreRequisiteException();
		}
	}

	/**
	 * Checks if the student is already taking the Course
	 * 
	 * @param courseToAdd
	 * @param student
	 * @throws StudentAlreadyInCourseException
	 */
	public static void validateCourseNotAlreadyTaken(Course courseToAdd, Student student)
			throws StudentAlreadyInCourseException {
		var registeredCourses = student.getRegisteredCourses();
		if (registeredCourses.contains(courseToAdd)) {
			throw new StudentAlreadyInCourseException();
		}
	}

	/**
	 * Checks if the Course capacity will be exceeded if the Tutor will remove or
	 * add the course.
	 * 
	 * @param course
	 * @param willTeach Whether the Tutor will add (true) or remove (false) this
	 *                  Course
	 * @return true if tutor can perform action, false otherwise
	 */
	public static boolean checkCourseCapacity(Course course, boolean willTeach) {
		if (course.getTutors().size() == 1 && !willTeach) {
			return false;
		} else if (course.getTutors().size() == 5 && willTeach) {
			return false;
		}
		return true;
	}

}
