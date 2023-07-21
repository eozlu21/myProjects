package admin;

import course.Course;
import money.Money;
import tutor.ALevelTutor;
import tutor.Tutor;
import tutoringCenterSystem.TutoringCenterSystem;
import user.User;

/**
 * This class extends User, with the unique ability to make payments to the
 * tutor and calculate system revenue.
 * 
 * @author Ege Erdem Özlü
 *
 */
public class Admin extends User {

	private ALevelTutor adminTutor;

	/**
	 * Creates an instance of an admin user. It has the additional property of
	 * adding the admin to the system.
	 * 
	 * @param username Username of admin
	 * @param password Password of admin
	 */
	public Admin(String username, String password) {
		super(username, password);
		getSystem().getAdmins().add(this);
	}

	/**
	 * Retrieves the payment queue since the last time this method was called. Adds
	 * the payments to the system revenue and resets the payment queue.
	 * 
	 * @return String summary of the payments made by this method call
	 * @see tutor.Tutor#getQueuedMoney()
	 * @see course.Course#queuedEquipmentProfit
	 * @see course.Course#queuedTutorProfit
	 */

	public String makePayments() {
		TutoringCenterSystem system = TutoringCenterSystem.getInstance();
		Money tutorMoneyToSystem = new Money(0);
		Money tutorMoneyToSelf = new Money(0);
		Money equipmentMoney = new Money(0);
		for (Tutor tutor : system.getTutors()) {
			Money.add(tutor.getBalance(), tutor.getQueuedMoney());
			Money.add(tutorMoneyToSelf, tutor.getQueuedMoney());
			Money.add(tutorMoneyToSystem, Money.product(Money.product(tutor.getQueuedMoney(), tutor.getCutPercentage()), 1/(1 - tutor.getCutPercentage())));
			tutor.setQueuedMoney(new Money(0));
		}
		for (Course course : system.getAvailableCourses()) {
			Money.add(equipmentMoney, course.getQueuedEquipmentProfit());
			Money.add(course.getEquipmentProfit(), course.getQueuedEquipmentProfit());
			Money.add(course.getTutorProfit(), course.getQueuedTutorProfit());
			course.setQueuedEquipmentProfit(new Money(0));
			course.setQueuedTutorProfit(new Money(0));
		}
		system.addRevenue(tutorMoneyToSystem);
		system.addRevenue(equipmentMoney);
		return String.format("Revenue of tutors: %s%nRevenue earned by service cut: %s%nRevenue from equipment: %s",
				tutorMoneyToSelf.toString(), tutorMoneyToSystem.toString(), equipmentMoney.toString());
	}

	public ALevelTutor getAdminTutor() {
		return adminTutor;
	}

	public void setAdminTutor(ALevelTutor adminTutor) {
		this.adminTutor = adminTutor;
	}
}
