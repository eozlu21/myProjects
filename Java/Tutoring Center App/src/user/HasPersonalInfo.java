package user;

import java.util.LinkedHashMap;

/**
 * Student and Tutor implements this interface. Used to retrieve personal info.
 * 
 * @author egeozlu
 *
 */
public interface HasPersonalInfo {
	/**
	 * Retrieves all personal info of a User implementing this method as a Map
	 * 
	 * @return Personal info of the user as a Map
	 */
	public LinkedHashMap<String, String> getPersonalInfo();
}
