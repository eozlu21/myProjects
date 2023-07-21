package exception;

/**
 * Thrown when a Tutor is assigned a cut percentage that is out of bounds for
 * that Tutor's guidelines
 * 
 * @author Ege Erdem Özlü
 *
 */
public class CutPercentageOutOfBoundsException extends Exception {

	public CutPercentageOutOfBoundsException() {
		// TODO Auto-generated constructor stub
	}

	public CutPercentageOutOfBoundsException(String message) {
		super("You cannot assign this as your cut percentage because ");
		// TODO Auto-generated constructor stub
	}

	public CutPercentageOutOfBoundsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CutPercentageOutOfBoundsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CutPercentageOutOfBoundsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
