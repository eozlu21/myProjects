package exception;

/**
 * Thrown when a session is already created
 * 
 * @author egeozlu
 *
 */
public class SessionAlreadyCreatedException extends Exception {

	public SessionAlreadyCreatedException() {
		// TODO Auto-generated constructor stub
	}

	public SessionAlreadyCreatedException(String message) {
		super("You have already created a session for this hour");
	}

}
