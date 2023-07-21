package tutor;

import java.awt.Image;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;

import course.Course;
import money.Money;
import schedule.Schedule;
import schedule.Session;
import tutoringCenterSystem.TutoringCenterSystem;
import user.HasPersonalInfo;
import user.User;

/**
 * Tutors can create sessions, and update Students' Course histories.
 * 
 * @author egeozlu
 *
 */
public abstract class Tutor extends User implements HasPersonalInfo {

	protected Random random = new Random(100);
	private String name;
	/**
	 * ID's of tutors are created one by one. Every time a tutor is created, this
	 * counter is incremented by one.
	 */
	private static int idCounter = 0;
	private String id;
	private Money balance;
	private Schedule schedule;
	private double cutPercentage;
	private LinkedList<Course> teachingCourses = new LinkedList<>();
	/**
	 * Money the tutor will receive after an admin finalizes the payments
	 */
	private Money queuedMoney = new Money(0);
	/**
	 * Portrait of the Tutor stored as an Image object. Initially it is a portrait
	 * placeholder.
	 */
	private Image portrait = new ImageIcon(getClass().getResource("Portrait_Placeholder.png")).getImage();

	/**
	 * Cannot be used by itself. When used in a concrete subclass' constructor,
	 * initializes the data. ID and cut percentage is assigned automatically. Also
	 * adds the tutor to the system.
	 * 
	 * @param username
	 * @param password
	 * @param name
	 */
	public Tutor(String username, String password, String name) {
		super(username, password);
		this.name = name;
		this.id = String.format("%04d", ++idCounter);
		this.balance = new Money(0);
		this.schedule = new Schedule();
		for (Session session : schedule.getHourSessionMap().values()) {
			session.setTutor(this);
		}

		getSystem().getTutors().add(this);
	}

	public double getCutPercentage() {
		return cutPercentage;
	}

	public void setCutPercentage(double cutPercentage) {
		this.cutPercentage = cutPercentage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Money getBalance() {
		return balance;
	}

	public void setBalance(Money balance) {
		this.balance = balance;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public LinkedList<Course> getCourses() {
		return teachingCourses;
	}

	public void setCourses(LinkedList<Course> courses) {
		this.teachingCourses = courses;
	}

	public void addCourse(Course course) {
		getCourses().add(course);
	}

	public void removeCourse(Course course) {
		getCourses().remove(course);
	}

	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Retrieves Tutor's info as a String
	 * 
	 * @return Tutor's info as a String
	 */
	public String getInfo() {
		String level = "";
		if (this instanceof ALevelTutor) {
			level = "A";
		} else if (this instanceof BLevelTutor) {
			level = "B";
		}
		String courses = "";
		for (Course course : getCourses()) {
			courses += "\n";
			courses += course.toString();
		}
		return String.format("Name: %s%nLevel: %s%nCourses: %s%n", getName(), level, courses);
	}

	@Override
	public LinkedHashMap<String, String> getPersonalInfo() {
		LinkedHashMap<String, String> info = new LinkedHashMap<>();
		info.put("Username: ", getUsername());
		info.put("Name: ", getName());
		info.put("ID: ", getId());
		if (this instanceof ALevelTutor) {
			info.put("Tutor Level: ", "A");
		} else if (this instanceof BLevelTutor) {
			info.put("Tutor Level: ", "B");
		} else {
			info.put("Tutor Level: ", "N/A");
		}
		return info;
	}

	/**
	 * Returns a List of Course names with the suffix "Teaching" if Tutor is
	 * teaching that course.
	 * 
	 * @return List of Course names with the suffix "Teaching" if Tutor is teaching
	 *         that course
	 */
	public LinkedList<String> getCourseInfo() {
		TutoringCenterSystem system = TutoringCenterSystem.getInstance();
		LinkedList<Course> courses = getCourses();
		LinkedList<String> output = new LinkedList<>();
		for (Course course : system.getCourses()) {
			if (courses.contains(course)) {
				output.add(course.toString() + " - Teaching");
			} else {
				output.add(course.toString());
			}
		}
		return output;
	}

	/**
	 * Returns the schedule info of the Tutor as a Map
	 * 
	 * @return The schedule info of the Tutor as a Map
	 */
	public LinkedHashMap<Integer, LinkedList<String>> getScheduleInfo() {
		LinkedHashMap<Integer, LinkedList<String>> output = new LinkedHashMap<>();
		for (int hour : Schedule.getHours()) {
			LinkedList<String> hourInfo = new LinkedList<>();
			Session session = getSchedule().getHourSessionMap().get(hour);
			String student = fixNullity(session.getStudent());
			String course = fixNullity(session.getCourse());
			String cost = fixNullity(session.getCost());
			hourInfo.add(session.getInterval());
			hourInfo.add(student);
			hourInfo.add(course);
			hourInfo.add(cost);
			output.put(session.getHour(), hourInfo);
		}
		return output;
	}

	/**
	 * This is used to replace a null property of a session with "N/A"
	 * 
	 * @param object
	 * @return Objects string representation if not null, "N/A" otherwise
	 */
	private String fixNullity(Object object) {
		if (object == null) {
			return "N/A";
		}
		return object.toString();
	}

	public Image getPortrait() {
		return portrait;
	}

	public Session getSessionAtHour(int hour) {
		return getSchedule().getHourSessionMap().get(hour);
	}

	public void setPortrait(Image portait) {
		this.portrait = portait;
	}

	public Money getQueuedMoney() {
		return queuedMoney;
	}

	public void setQueuedMoney(Money queuedMoney) {
		this.queuedMoney = queuedMoney;
	}

	public void setPortrait(String string) {
		this.portrait = new ImageIcon(getClass().getResource(string)).getImage().getScaledInstance(110, 110,
				Image.SCALE_SMOOTH);
	}
}
