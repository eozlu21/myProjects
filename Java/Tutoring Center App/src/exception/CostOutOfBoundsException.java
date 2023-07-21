package exception;

/**
 * Thrown when a Tutor tries to assign a cost that is out of bounds for that
 * Tutor's guidelines
 * 
 * @author Ege Erdem Özlü
 *
 */
public class CostOutOfBoundsException extends Exception {

	public CostOutOfBoundsException() {
		// TODO Auto-generated constructor stub
	}

	public CostOutOfBoundsException(String message) {
		super("You cannot assign that amount as the price because ");
		// TODO Auto-generated constructor stub
	}

	public CostOutOfBoundsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CostOutOfBoundsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CostOutOfBoundsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
