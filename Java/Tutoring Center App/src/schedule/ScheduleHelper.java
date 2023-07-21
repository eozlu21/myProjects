package schedule;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import course.Course;
import exception.CostOutOfBoundsException;
import exception.SessionAlreadyCreatedException;
import exception.StudentIsUnavailableException;
import exception.StudentNotEnrolledInCourseException;
import exception.UnavailableSessionException;
import money.Money;
import student.Student;
import tutor.Tutor;
import tutoringCenterSystem.TutoringCenterSystem;
import validator.Validator;

/**
 * Contains methods that are mainly related to Schedule and Session actions
 * 
 * @author egeozlu
 * @see Schedule
 * @see Session
 */
public class ScheduleHelper {

	/**
	 * Reserves a session for the student for the given given hour of that Tutor's
	 * Schedule. First checks if the session can be reserved
	 * 
	 * @param hour    Starting time for the session
	 * @param tutor   Tutor to host the session
	 * @param student Student to take the session
	 */
	public static void reserveSession(int hour, Tutor tutor, Student student) {
		Session session = tutor.getSchedule().getHourSessionMap().get(hour);
		if (canReserveSession(hour, tutor, student)) {
			session.setStudent(student);
			student.getUnavailableHours().add(hour);
		}
	}

	/**
	 * Uses validator methods to see if the student can reserve the session
	 * 
	 * @param hour    Starting time for the session
	 * @param tutor   Tutor to host the session
	 * @param student Student to take the session
	 * @return true if session can be reserved, false otherwise
	 */
	public static boolean canReserveSession(int hour, Tutor tutor, Student student) {
		Session session = tutor.getSchedule().getHourSessionMap().get(hour);
		try {
			Validator.validateStudentAvailability(student, hour);
			Validator.validateTutorAvailability(tutor, hour);
			Validator.validateStudentEnrollmentToCourse(student, session);
		} catch (StudentIsUnavailableException e) {
			return false;
		}

		catch (UnavailableSessionException e) {
			return false;
		}

		catch (StudentNotEnrolledInCourseException e) {
			return false;
		}
		return true;
	}

	/**
	 * Completes the Session by updating the Student's Course history. Also adds the
	 * relevant payments to their queues for admin to compute them later.
	 * 
	 * @param hour   Starting time for the session
	 * @param tutor  Tutor to host the session
	 * @param passed true if Student has passed, false if failed
	 */
	public static void completeSession(Tutor tutor, int hour, boolean passed) {
		Session session = tutor.getSessionAtHour(hour);
		Money cost = session.getCost();
		Student student = session.getStudent();
		Course course = session.getCourse();
		if (passed) {
			student.getPassedCourses().add(course);
			student.getFailedCourses().remove(course);
		} else {
			student.getFailedCourses().add(course);
		}

		student.getRegisteredCourses().remove(course);
		// tutor.getStudents().add(student);
		student.getUnavailableHours().remove((Object) hour);
		Money.add(student.getMissingPayments(), cost);
		Money.add(course.getQueuedTutorProfit(), Money.product(cost, tutor.getCutPercentage()));
		Money.add(course.getQueuedEquipmentProfit(), student.getNewBoughtEquipment());
		// Money.add(tutor.getSystem().getEquipmentRevenueOnQueue(),
		// course.getRequiredEquipmentCost());
		// Money.add(tutor.getSystem().getTutorCutRevenueOnQueue(), Money.product(cost,
		// tutor.getCutPercentage()));
		Money.add(tutor.getQueuedMoney(), Money.product(cost, 1 - tutor.getCutPercentage()));
		session.setStudent(null);
		session.setCost(null);
		session.setCourse(null);
		student.setNewBoughtEquipment(new Money(0));
	}

	/**
	 * Creates a Session with the given arguments for students to reserve
	 * 
	 * @param tutor  Tutor creating the Session
	 * @param hour   Starting hour of the Session
	 * @param cost   Cost of the Session as a Money object
	 * @param course Course to teach in the Session
	 * @see Session
	 */

	public static void createSession(Tutor tutor, int hour, Money cost, Course course) {
		tutor.getSessionAtHour(hour).setCost(cost);
		tutor.getSessionAtHour(hour).setCourse(course);
		tutor.getSessionAtHour(hour).setTutor(tutor);
	}

	/**
	 * Uses validators to see if tutor can create a session for the given hour and
	 * given price
	 * 
	 * @param tutor  Tutor creating the Session
	 * @param hour   Starting hour of the Session
	 * @param cost   Cost of the Session as a Money object
	 * @param course Course to teach in the Session
	 * @return true if Tutor can create the Session, false otherwise
	 */
	public static boolean canCreateSession(Tutor tutor, int hour, Money cost, Course course) {
		try {
			Validator.validateNewSession(tutor, hour);
			Validator.ValidateSessionPrice(tutor, cost);
		} catch (CostOutOfBoundsException e) {
			return false;
		} catch (SessionAlreadyCreatedException e) {
			return false;
		}
		return course != null;
	}

	/**
	 * Retrieves all the possible sessions from all the tutors that the Student can
	 * reserve for the Course
	 * 
	 * @param student
	 * @param course
	 * @return LinkedList of all possible Sessions
	 */
	public static LinkedList<Session> getAvailableSessionsForCourse(Student student, Course course) {
		LinkedList<Tutor> tutors = new LinkedList<>(student.getSystem().getTutors());
		for (Tutor tutor : new LinkedList<>(tutors)) {
			if (tutor.getCourses().contains(course)) {
				continue;
			}
			tutors.remove(tutor);
		}
		if (tutors.size() == 0) {
			return null;
		}
		LinkedList<Session> possibleSessions = new LinkedList<>();
		for (Tutor tutor : tutors) {
			for (int hour : Schedule.getHours()) {
				if (!canReserveSession(hour, tutor, student)
						|| tutor.getSessionAtHour(hour).getCourse().getName() != course.getName()) {
					continue;
				}
				possibleSessions.add(tutor.getSessionAtHour(hour));
			}
		}
		return possibleSessions;
	}

	/**
	 * Retrieves the Session with the given interval for the given Tutor
	 * 
	 * @param tutor
	 * @param interval Interval in which the returned Session takes place
	 * @return Session of the tutor which has the given interval
	 */
	public static Session getSessionWithGivenInterval(Tutor tutor, String interval) {
		int hour = getHourFromInterval(interval);
		return tutor.getSessionAtHour(hour);
	}

	/**
	 * Retrieves the starting hour from the given String interval.
	 * 
	 * @param interval String in the form of xx : xx - xx : xx
	 * @return the hour as an integer
	 */
	private static int getHourFromInterval(String interval) {
		String patternString1 = "(\\d{2}):\\d{2} - \\d{2}:\\d{2}$";
		String patternString2 = "(\\d{1}):\\d{2} - \\d{2}:\\d{2}$";
		String patternString3 = "(\\d{1}):\\d{2} - \\d{1}:\\d{2}$";
		Pattern pattern1 = Pattern.compile(patternString1);
		Pattern pattern2 = Pattern.compile(patternString2);
		Pattern pattern3 = Pattern.compile(patternString3);
		String inputString = interval;
		Matcher matcher1 = pattern1.matcher(inputString);
		Matcher matcher2 = pattern2.matcher(inputString);
		Matcher matcher3 = pattern3.matcher(inputString);
		int hour;
		if (matcher1.matches()) {
			hour = Integer.parseInt(matcher1.group(1));
			return hour;
		} else if (matcher2.matches()) {
			hour = Integer.parseInt(matcher2.group(1));
			return hour;
		}
		else if (matcher3.matches()) {
			hour = Integer.parseInt(matcher3.group(1));
			return hour;
		}
		return -1;
	}

	/**
	 * Retrieves the available sessions for the given Course for an admin (admins
	 * have no unavailable hour unlike a student)
	 * 
	 * @param course Course to look for available sessions for
	 * @return LinkedList of available Sessions
	 */
	public static LinkedList<Session> getAvailableSessionsForCourse(Course course) {
		LinkedList<Session> possibleSessions = new LinkedList<>();
		LinkedList<Tutor> tutors = TutoringCenterSystem.getInstance().getTutors();
		for (Tutor tutor : tutors) {
			for (int hour : Schedule.getHours()) {
				if (tutor.getSessionAtHour(hour).getCourse() != null
						&& course.equals(tutor.getSessionAtHour(hour).getCourse())
						&& tutor.getSessionAtHour(hour).getStudent() == null) {
					possibleSessions.add(tutor.getSessionAtHour(hour));
				}
			}
		}
		return possibleSessions;
	}
}
