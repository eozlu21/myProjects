package main;

import ui.TutoringCenterUI;
import ui.UserInterfaceDataHelper;

public class Main {
	/**************
	 * Pledge of Honor ****************************************** I hereby certify
	 * that I have completed this programming project on my own without any help
	 * from anyone else. The effort in the project thus belongs completely to me. I
	 * did not search for a solution, or I did not consult any program written by
	 * others or did not copy any program from other sources. I read and followed
	 * the guidelines provided in the project description. READ AND SIGN BY WRITING
	 * YOUR NAME SURNAME AND STUDENT ID SIGNATURE: Ege Erdem Özlü, 0080481
	 *************************************************************************/
	public static void main(String[] args) {
		new NewInitializer();
		UserInterfaceDataHelper.initTutoringCenterUi(TutoringCenterUI.getInstance());
	}
}
