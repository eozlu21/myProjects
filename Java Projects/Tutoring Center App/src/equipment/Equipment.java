package equipment;

import money.Money;
import tutoringCenterSystem.TutoringCenterSystem;

/**
 * Equipment that has a name, a price and a certain percentage that is earned by
 * the system after every sale of that Equipment.
 * 
 * @author Ege Erdem Özlü
 *
 */
public class Equipment {

	private Money price;
	private String name;
	private double percentageToSystem;

	/**
	 * Creates an Equipment object with the given arguments
	 * 
	 * @param price
	 * @param name
	 * @param percentageToSystem
	 * @see Equipment
	 */

	public Equipment(Money price, String name, double percentageToSystem) {
		this.price = price;
		this.name = name;
		this.percentageToSystem = percentageToSystem;
		TutoringCenterSystem.getInstance().getEquipments().add(this);
	}

	public Money getPrice() {
		return price;
	}

	public void setPrice(Money price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPercentageToSystem() {
		return percentageToSystem;
	}

	public void setPercentageToSystem(double percentageOfTutor) {
		this.percentageToSystem = percentageOfTutor;
	}

	@Override
	public String toString() {
		return getName();
	}

}
