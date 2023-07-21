package user;

import tutoringCenterSystem.TutoringCenterSystem;

/**
 * Users can login and register and use the system
 * 
 * @author egeozlu
 *
 */
public abstract class User {

	private String username;
	private String password;
	private TutoringCenterSystem system = TutoringCenterSystem.getInstance();

	/**
	 * Can only be used as a super call in a concrete subclass. Initializes the
	 * username and password data
	 * 
	 * @param username
	 * @param password
	 */
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TutoringCenterSystem getSystem() {
		return system;
	}

	public void setSystem(TutoringCenterSystem system) {
		this.system = system;
	}

}
