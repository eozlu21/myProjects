package exception;

/**
 * Thrown when a Advanced course is attempted to be given as a prerequisite a
 * beginner Course
 * 
 * @author Ege Erdem Özlü
 *
 */
public class InvalidPreRequisiteException extends Exception {

	public InvalidPreRequisiteException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidPreRequisiteException(String message) {
		super("Cannot add course as a pre requisite because " + message);
		// TODO Auto-generated constructor stub
	}

	public InvalidPreRequisiteException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidPreRequisiteException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidPreRequisiteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
