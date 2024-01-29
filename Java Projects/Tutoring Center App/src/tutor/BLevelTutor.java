package tutor;

/**
 * Tutors that cannot teach advanced Courses and have max cut percentage limit
 * of 15%.
 * 
 * @author egeozlu
 *
 */
public class BLevelTutor extends Tutor {

	public BLevelTutor(String username, String password, String name) {
		super(username, password, name);
		this.setCutPercentage(random.nextDouble(.14) + .01);
	}

}
