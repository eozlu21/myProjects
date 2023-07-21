package exception;

/**
 * Thrown when a Tutor tries to create a new Session in an hour where there is a
 * Student
 * 
 * @author egeozlu
 *
 */
public class StudentInSessionException extends Exception {

	public StudentInSessionException() {
		// TODO Auto-generated constructor stub
	}

	public StudentInSessionException(String message) {
		super("You cannot edit this session because ");
		// TODO Auto-generated constructor stub
	}

	public StudentInSessionException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public StudentInSessionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public StudentInSessionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
