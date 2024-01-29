package exception;

/**
 * Thrown when the Student is unavailable for that Session
 * 
 * @author egeozlu
 *
 */
public class StudentIsUnavailableException extends Exception {

	public StudentIsUnavailableException() {
		// TODO Auto-generated constructor stub
	}

	public StudentIsUnavailableException(String message) {
		super("You cannot register to this session because " + message);
		// TODO Auto-generated constructor stub
	}

	public StudentIsUnavailableException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public StudentIsUnavailableException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public StudentIsUnavailableException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
