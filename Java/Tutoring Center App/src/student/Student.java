package student;

import java.awt.Image;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import course.Course;
import equipment.Equipment;
import exception.InvalidPreRequisiteException;
import money.Money;
import user.HasPersonalInfo;
import user.User;

/**
 * Student class extends user class and stores all the relevant data for the
 * Student.
 * 
 * @author egeozlu
 *
 */
public class Student extends User implements HasPersonalInfo, Comparable<Student> {

	private String name;
	private String tckn;
	private int age;
	private String gender;
	private LinkedList<Course> passedCourses;
	private LinkedList<Course> registeredCourses;
	private LinkedList<Course> failedCourses;
	/**
	 * The total Money that the student has
	 */
	private Money balance;
	/**
	 * List of equipments that the Student currently owns
	 */
	private LinkedList<Equipment> equipments;
	/**
	 * Hours that are currently unavailable for the Student due to reserved Courses
	 */
	private LinkedList<Integer> unavailableHours;
	/**
	 * Any transaction this Student makes is first stored in this field. This Money
	 * is subtracted from balance after the Student makes payments
	 */
	private Money missingPayments;
	/**
	 * This field is used to compute how much the Student paid for newly bought
	 * Equipment which is renewed when Student makes payment
	 */
	private Money newBoughtEquipment;
	/**
	 * portrait of the Student stored as an Image object. Initially it is a portrait
	 * placeholder.
	 */
	private Image portrait = new ImageIcon(getClass().getResource("Portrait_Placeholder.png")).getImage();

	/**
	 * Creates a Student object with the given info and adds it to the system.
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @param tckn
	 * @param gender
	 * @param age
	 */
	public Student(String username, String password, String name, String tckn, String gender, int age) {
		super(username, password);
		this.name = name;
		this.tckn = tckn;
		this.age = age;
		this.gender = gender;
		this.balance = new Money(0);
		this.passedCourses = new LinkedList<>();
		this.registeredCourses = new LinkedList<>();
		this.equipments = new LinkedList<>();
		this.unavailableHours = new LinkedList<>();
		this.missingPayments = new Money(0);
		this.failedCourses = new LinkedList<>();
		getSystem().getStudents().add(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTckn() {
		return tckn;
	}

	public void setTckn(String tckn) {
		this.tckn = tckn;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LinkedList<Course> getPassedCourses() {
		return passedCourses;
	}

	public void setPassedCourses(LinkedList<Course> passedCourses) {
		this.passedCourses = passedCourses;
	}

	public LinkedList<Course> getRegisteredCourses() {
		return registeredCourses;
	}

	public void setRegisteredCourses(LinkedList<Course> registeredCourses) {
		this.registeredCourses = registeredCourses;
	}

	public Money getBalance() {
		return balance;
	}

	public void setBalance(Money balance) {
		this.balance = balance;
	}

	public LinkedList<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(LinkedList<Equipment> equipments) {
		this.equipments = equipments;
	}

	public LinkedList<Integer> getUnavailableHours() {
		return unavailableHours;
	}

	public void setUnavailableHours(LinkedList<Integer> unavailableHours) {
		this.unavailableHours = unavailableHours;
	}

	/**
	 * In case a student has a gender other than male or female, this getter method
	 * can also return the gender as a unedited String
	 * 
	 * @return
	 */
	public String getGender() {
		if (gender == "female") {
			return "Female";
		} else if (gender == "male") {
			return "Male";
		} else
			return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * This method uses Student's Course history to add a suffix to the given
	 * courses name and return that name
	 * 
	 * @param course
	 * @return Given Course's name with a relevant suffix
	 */
	public String studentAnyCourseToString(Course course) {
		LinkedList<Course> passedCourses = getPassedCourses();
		LinkedList<Course> registeredCourses = getRegisteredCourses();
		LinkedList<Course> failedCourses = getFailedCourses();
		if (passedCourses.contains(course)) {
			return (course.getName() + " - Passed");
		} else if (registeredCourses.contains(course)) {
			return (course.getName() + " - Registered");
		} else if (failedCourses.contains(course)) {
			return (course.getName() + " - Failed");
		}
		try {
			validator.Validator.validateCoursePreRequisites(course, this);
		}

		catch (InvalidPreRequisiteException e) {
			return (course.getName() + " - Do Not Meet Prerequisites");
		}
		if (!getEquipments().containsAll(course.getRequiredEquipments())) {
			return (course.getName() + " - Missing Equipments");
		}
		return (course.getName() + " - Available");
	}

	/**
	 * This method uses Student's Course history to add a suffix to the given
	 * courses name and return that name. Unlike studentAnyCourseToString, this
	 * method returns an empty string if Student has never done anything with that
	 * Course.
	 * 
	 * @param course
	 * @return Given Course's name with a relevant suffix if the Course is passed,
	 *         registered or failed, empty String otherwise
	 * @see #studentAnyCourseToString(Course course)
	 */
	public String studentPastOrPresentCourseToString(Course course) {
		LinkedList<Course> passedCourses = getPassedCourses();
		LinkedList<Course> registeredCourses = getRegisteredCourses();
		if (passedCourses.contains(course)) {
			return (course.getName() + " - Passed");
		} else if (registeredCourses.contains(course)) {
			return (course.getName() + " - Registered");
		} else if (failedCourses.contains(course)) {
			return (course.getName() + " - Failed");
		} else {
			return "";
		}
	}

	public Money getMissingPayments() {
		return missingPayments;
	}

	public void setMissingPayments(Money missingPayments) {
		this.missingPayments = missingPayments;
	}

	/**
	 * Returns a List of Strings for the Equipments the Student has
	 * 
	 * @return A List of Strings for the Equipments the Student has
	 */
	public LinkedList<String> getEquipmentInfo() {
		LinkedList<String> output = new LinkedList<>();
		for (Equipment equipment : getEquipments()) {
			output.add(equipment.toString());
		}
		return output;
	}

	@Override
	public LinkedHashMap<String, String> getPersonalInfo() {
		LinkedHashMap<String, String> info = new LinkedHashMap<>();
		info.put("Username: ", getUsername());
		info.put("Name: ", getName());
		info.put("Gender: ", getGender());
		info.put("Age", Integer.toString(getAge()));
		info.put("TCKN: ", getTckn());
		String equipmentsSummary = String.join(", ", getEquipmentInfo());
		info.put("Equipments: ", equipmentsSummary);
		return info;
	}

	/**
	 * Uses studentAnyCourseToString method to return a list of result of results
	 * for all Courses
	 * 
	 * @return A list of result of results of studentAnyCourseToString for all
	 *         Courses
	 * @see #studentAnyCourseToString
	 */
	public LinkedList<String> getAllCoursesInfo() {
		LinkedList<String> courses = new LinkedList<>();
		for (Course course : getSystem().getCourses()) {
			courses.add(studentAnyCourseToString(course));
		}
		return courses;
	}

	/**
	 * Uses studentPastOrPresentCourseToString method to return a list of result of
	 * results for all Courses
	 * 
	 * @return A list of result of results of studentPastOrPresentCourseToString for
	 *         all Courses
	 * @see #studentPastOrPresentCourseToString
	 */
	public LinkedList<String> getTakenCoursesInfo() {
		LinkedList<String> courses = new LinkedList<>();
		for (Course course : getSystem().getCourses()) {
			String courseName = studentPastOrPresentCourseToString(course);
			if (courseName == "") {
				continue;
			}
			courses.add(courseName);
		}
		return courses;
	}

	@Override
	public String toString() {
		return getName();
	}

	public Image getPortrait() {
		return portrait;
	}

	public void setPortrait(Image portrait) {
		this.portrait = portrait;
	}

	public LinkedList<Course> getFailedCourses() {
		return failedCourses;
	}

	public void setFailedCourses(LinkedList<Course> failedCourses) {
		this.failedCourses = failedCourses;
	}

	/**
	 * This method only subtracts the Money from Student's balance because admin
	 * will be the one finalizing the transactions.
	 */
	public void makePayment() {
		Money.subtract(getBalance(), getMissingPayments());
		setMissingPayments(new Money(0));
	}

	/**
	 * Sorts students by age ascending order. If ages are the same, Female comes
	 * first
	 */
	@Override
	public int compareTo(Student o) {
		if (this.getAge() - o.getAge() != 0) {
			return this.getAge() - o.getAge();
		}
		if (this.getGender().equals("Female") && !o.getGender().equals("Female")) {
			return -1;
		} else if (o.getGender().equals("Female") && !this.getGender().equals("Female")) {
			return 1;
		} else {
			return 0;
		}
	}

	public Money getNewBoughtEquipment() {
		if (newBoughtEquipment == null) {
			this.newBoughtEquipment = new Money(0);
		}
		return newBoughtEquipment;
	}

	public void setNewBoughtEquipment(Money newBoughtEquipment) {
		this.newBoughtEquipment = newBoughtEquipment;
	}

}
