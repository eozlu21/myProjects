package exception;

/**
 * Thrown when a Student tries to arrange a session they are not enrolled to
 * 
 * @author egeozlu
 *
 */
public class StudentNotEnrolledInCourseException extends Exception {

	public StudentNotEnrolledInCourseException() {
		// TODO Auto-generated constructor stub
	}

	public StudentNotEnrolledInCourseException(String message) {
		super("You are not enrolled in this course so you cannot arrange a session.");
		// TODO Auto-generated constructor stub
	}

	public StudentNotEnrolledInCourseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public StudentNotEnrolledInCourseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public StudentNotEnrolledInCourseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
