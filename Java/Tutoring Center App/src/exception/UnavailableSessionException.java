package exception;

/**
 * Thrown when a taken session is tried to be reserved
 * 
 * @author egeozlu
 *
 */
public class UnavailableSessionException extends Exception {

	public UnavailableSessionException() {
		// TODO Auto-generated constructor stub
	}

	public UnavailableSessionException(String message) {
		super("Cannot enroll to session because it is taken.");
		// TODO Auto-generated constructor stub
	}

	public UnavailableSessionException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public UnavailableSessionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UnavailableSessionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
