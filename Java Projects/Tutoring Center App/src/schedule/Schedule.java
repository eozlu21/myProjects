package schedule;

import java.util.Map;
import java.util.TreeMap;

/**
 * Class which every Tutor has a single unique instance of. Keeps track of
 * Sessions by hours.
 * 
 * @author egeozlu
 *
 */
public class Schedule {
	/**
	 * The starting hour of every single possible Session represented as an integer
	 * array
	 */
	private final static int[] hours = { 8, 9, 10, 11, 13, 14, 15, 16, 17, 18, 19, 20 };

	/**
	 * Mapping of every hour to a single Session
	 * 
	 * @see Session
	 */
	private Map<Integer, Session> hourSessionMap;

	/**
	 * Creates a Schedule in which every hour has an empty Session object mapped
	 * onto it
	 * 
	 * @see Session
	 */

	public Schedule() {
		this.hourSessionMap = new TreeMap<>();
		for (int availableHour : hours) {
			hourSessionMap.put(availableHour, new Session());
			hourSessionMap.get(availableHour).setHour(availableHour);
		}
	}

	/**
	 * @see #hours
	 * @return Possible starting hours of sessions as an array
	 */
	public static int[] getHours() {
		return hours;
	}

	/**
	 * @see #hourSessionMap
	 * @return
	 */
	public Map<Integer, Session> getHourSessionMap() {
		return hourSessionMap;
	}

	/**
	 * @see #hourSessionMap
	 * @param mapOfTimeStudents Map to set the hourSessionMap to
	 */
	public void setHourSessionMap(Map<Integer, Session> mapOfTimeStudents) {
		this.hourSessionMap = mapOfTimeStudents;
	}

	/**
	 * Returns info for the Schedule
	 */
	@Override
	public String toString() {
		String output = "";
		for (int hour : getHourSessionMap().keySet()) {
			output += String.format("The interval: %s%n", hour + "-" + (hour + 1));
			output += getHourSessionMap().get(hour);
		}
		return output;
	}

}
