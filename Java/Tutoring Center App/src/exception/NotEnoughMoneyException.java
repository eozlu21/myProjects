package exception;

/**
 * Thrown when a Student tries to make a transaction bigger than their balance
 * 
 * @author egeozlu
 *
 */
public class NotEnoughMoneyException extends Exception {

	public NotEnoughMoneyException() {
		// TODO Auto-generated constructor stub
	}

	public NotEnoughMoneyException(String message) {
		super("Transaction failed because you don't have sufficient funds.");
		// TODO Auto-generated constructor stub
	}

	public NotEnoughMoneyException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NotEnoughMoneyException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotEnoughMoneyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
