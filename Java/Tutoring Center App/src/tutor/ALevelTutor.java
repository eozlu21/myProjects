package tutor;

/**
 * Tutors that can teach any Course and have max cut percentage limit of 10%
 * 
 * @author egeozlu
 *
 */
public class ALevelTutor extends Tutor {

	public ALevelTutor(String username, String password, String name) {
		super(username, password, name);

		this.setCutPercentage(random.nextDouble(.09) + .01);
	}

}
