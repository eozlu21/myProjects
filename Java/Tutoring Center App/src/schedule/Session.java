package schedule;

import course.Course;
import money.Money;
import student.Student;
import tutor.Tutor;

/**
 * Sessions are the objects being reserved and every Tutor has as many unique
 * Sessions as they have hours in the system. Sessions are stored in Tutor's
 * Schedule field.
 * 
 * @author egeozlu
 * @see Schedule
 */
public class Session {

	private Student student;
	private Tutor tutor;
	private Course course;
	private Money cost;
	private int hour;

	/**
	 * Creates a Session with the given Student, Tutor, Course, Money and hour
	 * 
	 * @param student
	 * @param tutor
	 * @param course
	 * @param cost    (Money object)
	 * @param hour
	 */
	public Session(Student student, Tutor tutor, Course course, Money cost, int hour) {
		super();
		this.student = student;
		this.tutor = tutor;
		this.course = course;
		this.cost = cost;
		this.hour = hour;
	}

	/**
	 * Creates a Session with no initial information. Setters can be used to
	 * initialize info later.
	 */
	public Session() {
		this.student = null;
		this.tutor = null;
		this.course = null;
		this.cost = null;
	}

	public Student getStudent() {

		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Money getCost() {
		return cost;
	}

	public void setCost(Money cost) {
		this.cost = cost;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	/**
	 * Gives info about the Session in a String format
	 */
	@Override
	public String toString() {
		String output = String.format("Course: %s%nTime: %s%nTutor: %s%nCost: %s%n", getCourse().getName(),
				getInterval(), getTutor().getName(), getCost());
		return output;
	}

	/**
	 * Gets the hour interval from the Session
	 * 
	 * @return
	 */
	public String getInterval() {
		return getHour() + ":00 - " + (getHour() + 1) + ":00";
	}

	/**
	 * Retrieves the info of this Session
	 * 
	 * @return
	 */
	public String getAvailableSessionInfo() {
		String output = String.format("The hour: %s%nTutor: %s%nThe cost: %s%nThe course: %s%n", getInterval(),
				getTutor().getName(), getCost(), getCourse());
		return output;
	}

}
