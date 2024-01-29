package course;

import java.util.ArrayList;
import java.util.LinkedList;

import equipment.Equipment;
import money.Money;
import student.Student;
import tutor.Tutor;
import tutoringCenterSystem.TutoringCenterSystem;

/**
 * Course class that stores all the relevant information about itself in its
 * fields
 * 
 * @author Ege Erdem Özlü
 *
 */
public abstract class Course {

	private String name;
	private LinkedList<Tutor> tutors;
	private LinkedList<Course> preRequisites;
	private LinkedList<Student> enrolledStudents;
	private LinkedList<Equipment> requiredEquipments;
	/**
	 * Total profit gained from this course from students buying required
	 * equipments. Only updates when payments are finalized.
	 * 
	 * @see admin.Admin#makePayments()
	 */
	private Money equipmentProfit;
	/**
	 * Stores how much money is going to be paid to the system for the equipments
	 * bought when admin finalizes the payments. Updates when a student enrolls a
	 * course.
	 * 
	 * @see admin.Admin#makePayments()
	 */
	private Money queuedEquipmentProfit;
	/**
	 * Stores how much money is generated from the session which will be distributed
	 * to the system and the tutor when admin finalizes the payments. Updates when a
	 * course status is updated by the tutor.
	 * 
	 * @see admin.Admin#makePayments()
	 */
	private Money queuedTutorProfit;
	/**
	 * Total profit gained by the tutors from this course. This value only takes
	 * into account the amount that goes into tutors' balance. Only updates when
	 * payments are finalized.
	 * 
	 * @see admin.Admin#makePayments()
	 */
	private Money tutorProfit;
	private TutoringCenterSystem system = TutoringCenterSystem.getInstance();

	/**
	 * This constructor is only meant to be used by its subclasses Advanced and
	 * Beginner Course
	 * 
	 * @param name               Name of the course
	 * @param preRequisites      Prerequisites for the course
	 * @param requiredEquipments Required equipments for the course
	 * @see AdvancedCourse
	 * @see BeginnerCourse
	 */

	protected Course(String name, LinkedList<Course> preRequisites, LinkedList<Equipment> requiredEquipments) {
		super();
		this.name = name;
		this.preRequisites = preRequisites;
		this.requiredEquipments = requiredEquipments;
		this.enrolledStudents = new LinkedList<>();
		this.equipmentProfit = new Money(0);
		this.tutorProfit = new Money(0);
		this.queuedEquipmentProfit = new Money(0);
		this.queuedTutorProfit = new Money(0);
		system.getAvailableCourses().add(this);
	}

	public LinkedList<Tutor> getTutors() {
		if (tutors == null) {
			this.tutors = new LinkedList<>();
		}
		return tutors;
	}

	public void setTutors(LinkedList<Tutor> tutors) {
		this.tutors = tutors;
	}

	public LinkedList<Course> getPreRequisites() {
		if (preRequisites == null) {
			this.preRequisites = new LinkedList<>();
		}
		return preRequisites;
	}

	public void setPreRequisites(LinkedList<Course> preRequisites) {
		this.preRequisites = preRequisites;
	}

	public LinkedList<Student> getEnrolledStudents() {
		return enrolledStudents;
	}

	public void setEnrolledStudents(LinkedList<Student> enrolledStudents) {
		this.enrolledStudents = enrolledStudents;
	}

	public LinkedList<Equipment> getRequiredEquipments() {
		if (this.requiredEquipments == null) {
			this.requiredEquipments = new LinkedList<>();
		}
		return requiredEquipments;
	}

	public void setRequiredEquipments(LinkedList<Equipment> requiredEquipments) {
		this.requiredEquipments = requiredEquipments;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TutoringCenterSystem getSystem() {
		return system;
	}

	public void setSystem(TutoringCenterSystem system) {
		this.system = system;
	}

	@Override
	public String toString() {
		return getName();
	}

	public Money getEquipmentProfit() {
		return equipmentProfit;
	}

	public void setEquipmentProfit(Money profit) {
		this.equipmentProfit = profit;
	}

	public Money getTutorProfit() {
		return tutorProfit;
	}

	public void setTutorProfit(Money tutorProfit) {
		this.tutorProfit = tutorProfit;
	}

	/**
	 * Returns the Money equivalent of the total cost for the course's all of
	 * required equipments. Returns a Money object with 0 TL if no equipment is
	 * required.
	 * 
	 * @return The Money equivalent of the total cost for the course's all of
	 *         required equipments
	 */
	public Money getRequiredEquipmentCost() {
		Money output = new Money(0);
		for (Equipment equipment : getRequiredEquipments()) {
			Money.add(output, Money.product(equipment.getPrice(), equipment.getPercentageToSystem()));
		}
		return output;
	}

	/**
	 * 
	 * @return The queued equipment profit
	 * @see #queuedEquipmentProfit
	 */
	public Money getQueuedEquipmentProfit() {
		return queuedEquipmentProfit;
	}

	/**
	 * Sets the queued equipment profit
	 * 
	 * @param queuedEquipmentProfit The amount of money to set
	 * @see #queuedEquipmentProfit
	 */
	public void setQueuedEquipmentProfit(Money queuedEquipmentProfit) {
		this.queuedEquipmentProfit = queuedEquipmentProfit;
	}

	/**
	 * 
	 * @return The queued tutor profit
	 * @see #queuedTutorProfit
	 */
	public Money getQueuedTutorProfit() {
		return queuedTutorProfit;
	}

	/**
	 * Sets the queued tutor profit
	 * 
	 * @param queuedTutorProfit The amount of money to set
	 * @see #queuedTutorProfit
	 */
	public void setQueuedTutorProfit(Money queuedTutorProfit) {
		this.queuedTutorProfit = queuedTutorProfit;
	}

	/**
	 * 
	 * @return The string representation of the required equipment names divided by
	 *         newlines
	 */
	public String getRequiredEquipmentInfo() {
		LinkedList<String> outputList = new LinkedList<>();
		if (getRequiredEquipments() == null || getRequiredEquipments().size() == 0) {
			return "None";
		}
		getRequiredEquipments().forEach(c -> outputList.add(c.toString()));
		return String.join("\n", outputList);
	}

	/**
	 * 
	 * @return The String representation of the names of the tutors divided by
	 *         newlines.
	 */
	public String getTutorInfo() {
		LinkedList<String> outputList = new LinkedList<>();
		if (getTutors() == null || getTutors().size() == 0) {
			return "None";
		}
		getTutors().forEach(c -> outputList.add(c.toString()));
		return String.join("\n", outputList);
	}

	/**
	 * 
	 * @return The String summary of the course for the admin
	 */
	public String getInfoForAdmin() {
		ArrayList<String> preRequisites = new ArrayList<>();
		getPreRequisites().forEach(c -> preRequisites.add(c.toString()));
		return String.format(
				"Name: %s%nType: %s%nPrerequisites: %n%s%nRequired Equipments: %n%s%nTutors: %n%s%nTutor Revenue: %s%nEquipment Revenue: %s%n",
				getName(), getCourseTypeInfo(), getPrerequisiteInfo(preRequisites), getRequiredEquipmentInfo(),
				getTutorInfo(), getTutorProfit(), getEquipmentProfit());
	}

	/**
	 * 
	 * @param preRequisites An Arraylist of prerequisite Course names
	 * @return String representation of the prerequisites, "None" if the argument
	 *         list is empty
	 */
	private String getPrerequisiteInfo(ArrayList<String> preRequisites) {
		return preRequisites.size() != 0 ? String.join("\n", preRequisites) : "None";
	}

	/**
	 * 
	 * @return String representation of the course type
	 */
	private String getCourseTypeInfo() {
		return this instanceof AdvancedCourse ? "Advanced" : "Beginner";
	}

	/**
	 * 
	 * @return The String summary of the course for the tutor
	 */
	public String getInfoForTutor() {
		ArrayList<String> preRequisites = new ArrayList<>();
		getPreRequisites().forEach(c -> preRequisites.add(c.toString()));
		return String.format("Name: %s%nType: %s%nPrerequisites: %n%s%nRequired Equipments: %n%s%n", getName(),
				getCourseTypeInfo(), getPrerequisiteInfo(preRequisites), getRequiredEquipmentInfo());
	}

}
