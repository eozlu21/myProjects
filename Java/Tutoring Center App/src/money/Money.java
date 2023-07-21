package money;

/**
 * This class represents TL, and has addition and multiplication operations.
 * 
 * @author egeozlu
 *
 */
public class Money {
	/**
	 * All Money objects are differentiated by this field, which represents money as
	 * the amount in kuruş.
	 */
	private int totalAmountInKurus;

	/**
	 * Creates money object with given kuruş amount
	 * 
	 * @param totalAmountInKurus
	 */
	public Money(int totalAmountInKurus) {
		this.totalAmountInKurus = totalAmountInKurus;
	}

	/**
	 * 
	 * @return The equivalent of the Money in kuruş
	 */
	public int getTotalAmountInKurus() {
		return totalAmountInKurus;
	}

	/**
	 * 
	 * @param totalAmountInKurus The amount of kuruş to set the money value to be
	 *                           equivalent to
	 */
	public void setTotalAmountInKurus(int totalAmountInKurus) {
		this.totalAmountInKurus = totalAmountInKurus;
	}

	/**
	 * This representation will be in the form of x,xx TL
	 */
	@Override
	public String toString() {
		int turkLira = getTotalAmountInKurus() / 100;
		int kurus = getTotalAmountInKurus() % 100;

		return String.format("%s,%02d TL", turkLira, kurus);
	}

	/**
	 * Subtracts the first argument from the second argument. First argument's
	 * values are changed.
	 * 
	 * @param moneyToSubtractFrom
	 * @param moneyToSubtract
	 */
	public static void subtract(Money moneyToSubtractFrom, Money moneyToSubtract) {
		moneyToSubtractFrom.setTotalAmountInKurus(
				moneyToSubtractFrom.getTotalAmountInKurus() - moneyToSubtract.getTotalAmountInKurus());
	}

	/**
	 * Adds the second argument to the first argument. First argument's values are
	 * changed.
	 * 
	 * @param originalMoney
	 * @param moneyToAdd
	 */
	public static void add(Money originalMoney, Money moneyToAdd) {
		originalMoney.setTotalAmountInKurus(originalMoney.getTotalAmountInKurus() + moneyToAdd.getTotalAmountInKurus());
	}

	/**
	 * Multiplies the Money object with the fraction, returns the resulting amount
	 * as a Money object
	 * 
	 * @param moneyToMultiply
	 * @param fraction
	 * @return Resulting amount as a Money object
	 */
	public static Money product(Money moneyToMultiply, double fraction) {
		return new Money((int) (moneyToMultiply.getTotalAmountInKurus() * fraction));
	}

}
