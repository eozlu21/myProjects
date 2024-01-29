package main;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import admin.Admin;
import course.AdvancedCourse;
import course.BeginnerCourse;
import course.Course;
import course.CourseHelper;
import equipment.Equipment;
import equipment.EquipmentHelper;
import money.Money;
import schedule.ScheduleHelper;
import student.Student;
import tutor.ALevelTutor;
import tutor.BLevelTutor;
import tutor.Tutor;
import tutoringCenterSystem.TutoringCenterSystem;

/**
 * This class generates all the Courses, Equipments and Courses.
 * 
 * @author Ege Erdem Özlü
 *
 */
public class NewInitializer {

	private Random random = new Random(100);
	private LinkedList<Course> courses;
	TutoringCenterSystem system = TutoringCenterSystem.getInstance();
	private Equipment physicsBook;
	private Equipment calculusBook;
	private Equipment richBook;
	private Equipment notebook;
	private Equipment labGlass;
	private Equipment labCoat;
	private Equipment laptop;
	private BeginnerCourse math106;
	private BeginnerCourse math107;
	private BeginnerCourse chem103;
	private BeginnerCourse comp100;
	private AdvancedCourse chem354;
	private BeginnerCourse mrkt220;
	private AdvancedCourse mrkt420;
	private AdvancedCourse math203;
	private AdvancedCourse math204;
	private BeginnerCourse univ199;
	private BeginnerCourse phys101;
	private BeginnerCourse phys102;
	private Tutor einstein;
	private Tutor euler;
	private Tutor wayne;
	private Tutor leibnitz;
	private Tutor tate;
	private Tutor bateman;
	private Tutor walter;
	private Tutor chan;
	private Tutor mark;

	/**
	 * Creates the database of the system
	 * 
	 * @see tutoringCenterSystem.TutoringCenterSystem
	 */
	public NewInitializer() {

		initializeEquipments();
		initalizeCourses();
		initializeCoursePrereqs();
		initializeCourseEquipments();
		itializeAdmin();
		initializeStudents();
		initializeTutors();
		initializeTutorsTeachingCourses();
		initializeStudentEquipments();
		initializeStudentCourseRelations();
		initializeSchedules();

	}

	private void initializeSchedules() {

		Random random = new Random(50);
		for (Tutor tutor : system.getTutors()) {			
			for (Course course : tutor.getCourses()) {
				int hour = random.nextInt(13) + 8;
				if (hour == 12) {
					hour++;
				}
				Money cost = new Money(random.nextInt(20000) + 80000);
				if (!ScheduleHelper.canCreateSession(tutor, hour, cost , course)) {
					continue;
				}
				ScheduleHelper.createSession(tutor, hour, cost, course);
			}
		}

	}

	/**
	 * Creates the Equipments
	 */
	private void initializeEquipments() {
		physicsBook = new Equipment(new Money(32000), "General Physics Textbook", .20);
		calculusBook = new Equipment(new Money(20000), "Calculus Textbook 10th Ed.", .15);
		richBook = new Equipment(new Money(49000), "Get Rich Book", .02);
		notebook = new Equipment(new Money(2000), "Notebook", .3);
		labGlass = new Equipment(new Money(5000), "Erlenmeyer Flask", .1);
		labCoat = new Equipment(new Money(35000), "Lab Coat", .15);
		laptop = new Equipment(new Money(100000), "Laptop", .18);
	}

	/**
	 * Creates the Courses
	 */
	private void initalizeCourses() {
		math106 = new BeginnerCourse("MATH 106", null, null);
		math107 = new BeginnerCourse("MATH 107", null, null);
		chem103 = new BeginnerCourse("CHEM 103", null, null);
		comp100 = new BeginnerCourse("COMP 100", null, null);
		chem354 = new AdvancedCourse("CHEM 354", null, null);
		mrkt220 = new BeginnerCourse("MRKT 220", null, null);
		mrkt420 = new AdvancedCourse("MRKT 420", null, null);
		math203 = new AdvancedCourse("MATH 203", null, null);
		math204 = new AdvancedCourse("MATH 204", null, null);
		univ199 = new BeginnerCourse("UNIV 199", null, null);
		phys101 = new BeginnerCourse("PHYS 101", null, null);
		phys102 = new BeginnerCourse("PHYS 102", null, null);
	}

	/**
	 * Creates the Course prerequisites
	 */
	private void initializeCoursePrereqs() {
		CourseHelper.addPreRequisite(chem354, chem103);
		CourseHelper.addPreRequisite(math203, math106);
		CourseHelper.addPreRequisite(math203, math107);
		CourseHelper.addPreRequisite(math204, math203);
		CourseHelper.addPreRequisite(mrkt420, mrkt220);
		CourseHelper.addPreRequisite(phys102, phys101);
	}

	/**
	 * Adds Equipment requirements to Courses
	 */
	private void initializeCourseEquipments() {
		CourseHelper.addEquipmentReqToCourse(math106, calculusBook);
		CourseHelper.addEquipmentReqToCourse(math203, calculusBook);
		CourseHelper.addEquipmentReqToCourse(math204, calculusBook);
		CourseHelper.addEquipmentReqToCourse(chem103, labCoat);
		CourseHelper.addEquipmentReqToCourse(univ199, laptop);
		CourseHelper.addEquipmentReqToCourse(comp100, laptop);
		CourseHelper.addEquipmentReqToCourse(chem354, labCoat);
		CourseHelper.addEquipmentReqToCourse(chem103, labGlass);
		CourseHelper.addEquipmentReqToCourse(phys101, physicsBook);
		CourseHelper.addEquipmentReqToCourse(phys102, physicsBook);
		CourseHelper.addEquipmentReqToCourse(mrkt220, notebook);
		CourseHelper.addEquipmentReqToCourse(mrkt420, richBook);
	}

	/**
	 * Initializes the admin(s)
	 */
	private void itializeAdmin() {
		new Admin("iamadmin", "please123");

	}

	/**
	 * Creates the Students
	 */

	private void initializeStudents() {
		for (int i = 0; i < 120; i++) {
			new Student("username" + i, "password" + i, "name" + i, "1234567" + Integer.toString(8901 + i),
					i % 2 == 0 ? "Male" : "Female", random.nextInt(15) + 17);
		}
		for (Student student : system.getStudents()) {
			student.setBalance(new Money(random.nextInt(1000000) + 200000));
		}
	}

	/**
	 * Creates the Tutors
	 */
	private void initializeTutors() {

		einstein = new ALevelTutor("smart_one", "emc2", "Albert Einstein");
		leibnitz = new ALevelTutor("math_god", "math", "Leibnitz");
		euler = new ALevelTutor("math_pro", "e", "Euler");
		bateman = new ALevelTutor("psycho", "ax", "Patrick Bateman");
		walter = new ALevelTutor("heisenberg", "family", "Walter White");
		chan = new ALevelTutor("master", "password", "Jackie Chan");
		wayne = new BLevelTutor("not_batman", "iamrich", "Bruce Wayne");
		tate = new BLevelTutor("33cars", "romania", "Andrew Tate");
		mark = new BLevelTutor("facebook", "lizard12", "Mark Zuckerberg");

		einstein.setPortrait("einstein.jpeg");
		leibnitz.setPortrait("leibnitz.jpeg");
		euler.setPortrait("euler.jpeg");
		bateman.setPortrait("patrick bateman.jpeg");
		walter.setPortrait("walterWhite.png");
		chan.setPortrait("jackiechan.jpeg");
		wayne.setPortrait("Bruce_Wayne.jpg");
		tate.setPortrait("andrewtate Small.png");
		mark.setPortrait("markzuckerberg.jpeg");

	}

	/**
	 * Adds Tutors to Courses
	 */
	private void initializeTutorsTeachingCourses() {

		CourseHelper.addTutorToCourse(chem354, walter);
		CourseHelper.addTutorToCourse(chem354, einstein);
		CourseHelper.addTutorToCourse(chem354, euler);

		CourseHelper.addTutorToCourse(chem103, walter);
		CourseHelper.addTutorToCourse(chem103, wayne);
		CourseHelper.addTutorToCourse(chem103, bateman);

		CourseHelper.addTutorToCourse(mrkt220, walter);
		CourseHelper.addTutorToCourse(mrkt220, tate);
		CourseHelper.addTutorToCourse(mrkt220, wayne);
		CourseHelper.addTutorToCourse(mrkt220, bateman);
		CourseHelper.addTutorToCourse(mrkt220, mark);

		CourseHelper.addTutorToCourse(mrkt420, bateman);
		CourseHelper.addTutorToCourse(mrkt420, walter);
		CourseHelper.addTutorToCourse(mrkt420, chan);

		CourseHelper.addTutorToCourse(phys101, einstein);
		CourseHelper.addTutorToCourse(phys101, leibnitz);
		CourseHelper.addTutorToCourse(phys101, wayne);

		CourseHelper.addTutorToCourse(phys102, einstein);
		CourseHelper.addTutorToCourse(phys102, mark);
		CourseHelper.addTutorToCourse(phys102, chan);

		CourseHelper.addTutorToCourse(math203, einstein);
		CourseHelper.addTutorToCourse(math203, leibnitz);
		CourseHelper.addTutorToCourse(math203, euler);

		CourseHelper.addTutorToCourse(math106, leibnitz);
		CourseHelper.addTutorToCourse(math106, euler);
		CourseHelper.addTutorToCourse(math106, einstein);

		CourseHelper.addTutorToCourse(math107, leibnitz);
		CourseHelper.addTutorToCourse(math107, euler);
		CourseHelper.addTutorToCourse(math107, einstein);

		CourseHelper.addTutorToCourse(math204, euler);
		CourseHelper.addTutorToCourse(math204, leibnitz);
		CourseHelper.addTutorToCourse(math204, einstein);

		CourseHelper.addTutorToCourse(univ199, mark);
		CourseHelper.addTutorToCourse(univ199, tate);
		CourseHelper.addTutorToCourse(univ199, walter);

		CourseHelper.addTutorToCourse(comp100, mark);
		CourseHelper.addTutorToCourse(comp100, tate);
		CourseHelper.addTutorToCourse(comp100, wayne);

	}

	/**
	 * Psuedorandomly adds Equipments to Students. Since random generator is seeded,
	 * this will generate the same results
	 */
	private void initializeStudentEquipments() {
		for (Student student : system.getStudents()) {
			for (Equipment equipment : system.getEquipments()) {
				EquipmentHelper.addEquipmentToStudent(student, equipment);
			}
		}
		LinkedList<Equipment> equipments = system.getEquipments();
		for (Student student : system.getStudents()) {
			Collections.shuffle(equipments, random);
			for (Equipment equipment : equipments.subList(0, 3)) {
				student.getEquipments().remove(equipment);
			}
		}
	}

	/**
	 * Randomly adds courses, then randomly attempts to distribute the Students to
	 * Courses in a non-homogenous way. Result is same every time due to seeded
	 * random
	 */
	private void initializeStudentCourseRelations() {

		for (Student student : system.getStudents()) {
			for (Course course : system.getCourses()) {
				addRegisteredCourseButCheckConditions(student, course);
			}
		}
		courses = new LinkedList<Course>(TutoringCenterSystem.getInstance().getCourses());
		addPassedCoursesToStudentsRandomly();
		registerStudentsToCoursesRandomly();
		addPassedCoursesToStudentsRandomly();
		registerStudentsToCoursesRandomly();
		addPassedCoursesToStudentsRandomly();
		registerStudentsToCoursesRandomly();
	}
/**
 * Randomly registers students to courses if they meet the conditions
 * @see #addRegisteredCourseButCheckConditions
 */
	private void registerStudentsToCoursesRandomly() {
		for (Student student : system.getStudents()) {
			Collections.shuffle(courses, random);
			for (Course course : courses.subList(0, 5)) {
				addRegisteredCourseButCheckConditions(student, course);
			}
		}
	}
/**
 * Randomly adds courses to students' passed courses if they meet the conditions
 * @see #addPassedCourseButCheckConditions
 */
	private void addPassedCoursesToStudentsRandomly() {
		for (Student student : system.getStudents()) {
			Collections.shuffle(courses, random);
			for (Course course : courses.subList(0, 5)) {
				addPassedCourseButCheckConditions(student, course);
			}
		}
	}

	/**
	 * Checks if the Student can attend Course. If can, registers the Student
	 * 
	 * @param student
	 * @param course
	 */
	private void addRegisteredCourseButCheckConditions(Student student, Course course) {
		if (student.getPassedCourses().contains(course)) {
			return;
		}
		if (!student.getPassedCourses().containsAll(course.getPreRequisites())) {
			return;
		}
		if (!student.getEquipments().containsAll(course.getRequiredEquipments())) {
			return;
		}
		if (student.getRegisteredCourses().contains(course)) {
			return;
		}
		student.getRegisteredCourses().add(course);
	}

	/**
	 * Checks if the Student can attend Course. If can, adds Course to Student's
	 * passed Courses.
	 * 
	 * @param student
	 * @param course
	 */
	private void addPassedCourseButCheckConditions(Student student, Course course) {
		if (!student.getPassedCourses().containsAll(course.getPreRequisites())) {
			return;
		}
		if (!student.getEquipments().containsAll(course.getRequiredEquipments())) {
			return;
		}
		if (student.getPassedCourses().contains(course)) {
			return;
		}
		if (student.getRegisteredCourses().contains(course)) {
			student.getRegisteredCourses().remove(course);
		}
		student.getPassedCourses().add(course);
	}
}
