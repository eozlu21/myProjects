package exception;

/**
 * Thrown when a Student is already in the Course
 * 
 * @author egeozlu
 *
 */

public class StudentAlreadyInCourseException extends Exception {

	public StudentAlreadyInCourseException() {
		// TODO Auto-generated constructor stub
	}

	public StudentAlreadyInCourseException(String message) {
		super("You are already enrolled to this course");
		// TODO Auto-generated constructor stub
	}

	public StudentAlreadyInCourseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public StudentAlreadyInCourseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public StudentAlreadyInCourseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
