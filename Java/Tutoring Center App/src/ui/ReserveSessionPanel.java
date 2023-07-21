package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import course.Course;
import schedule.ScheduleHelper;
import schedule.Session;
import student.Student;
import tutor.Tutor;

/**
 * This panel allows Students to reserve a Session for the given Course
 * 
 * @author egeozlu
 *
 */
public class ReserveSessionPanel extends JPanel {

	Student student;
	private BackButton backButton;

	/**
	 * Creates a ReserveSessionPanel with given Student and Course
	 * 
	 * @param student
	 * @param course
	 * @see ReserveSessionPanel
	 */
	public ReserveSessionPanel(Student student, Course course) {
		setLayout(new GridLayout(5, 1));
		backButton = new BackButton();
		JLabel titleJLabel = new JLabel();
		titleJLabel.setText("Choose a session for " + course.getName() + ":");
		add(titleJLabel);

		String[] headers = { "Time Interval", "Tutor", "Cost" };
		DefaultTableModel model = new DefaultTableModel(headers, 0);

		// Create a new JTable with the table model
		JTable table = new JTable(model);
		table.setShowGrid(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// Add the table to a scroll pane
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setEnabled(false);

		// Add the scroll pane to the panel
		add(scrollPane);
		LinkedList<Session> possibleSessions = ScheduleHelper.getAvailableSessionsForCourse(course);
		JButton reservationButton = new JButton("Make Reservation");
		if (student == null) {
			itializeTableInfo(possibleSessions, model);
			reservationButton.setEnabled(false);
		} else {
			this.student = student;
			LinkedList<Session> sessions = ScheduleHelper.getAvailableSessionsForCourse(student, course);
			itializeTableInfo(sessions, model);
		}

		reservationButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table.getSelectedRow();
				if (rowIndex == -1) {
					JOptionPane.showMessageDialog(null, "You have not selected a session to reserve", "Hey There!",
							JOptionPane.INFORMATION_MESSAGE);
					return;

				}
				String interval = (String) table.getValueAt(rowIndex, 0);
				Tutor tutor = (Tutor) table.getValueAt(rowIndex, 1);
				Session session = ScheduleHelper.getSessionWithGivenInterval(tutor, interval);
				ScheduleHelper.reserveSession(session.getHour(), session.getTutor(), student);
				// TODO the cost will be deducted from the system after tutor has marked the
				// session complete
				JOptionPane.showMessageDialog(null, "Successfully reserved the session: \n" + session.toString(),
						"Information", JOptionPane.INFORMATION_MESSAGE);
				UserInterfaceDataHelper.callUserPanel(student);
			}
		});

		JButton tutorInfoButton = new JButton("Get Tutor Info");
		tutorInfoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table.getSelectedRow();
				Tutor tutor = (Tutor) table.getValueAt(rowIndex, 1);
				JOptionPane.showMessageDialog(null, tutor.getInfo(), "Tutor Information",
						JOptionPane.INFORMATION_MESSAGE);
			}

		});
		add(reservationButton);
		add(tutorInfoButton);
		add(backButton);
	}

	private void itializeTableInfo(LinkedList<Session> sessions, DefaultTableModel model) {
		for (Session session : sessions) {
			Object[] info = new Object[3];
			info[0] = session.getInterval();
			info[1] = session.getTutor();
			info[2] = session.getCost();
			model.addRow(info);
		}
	}

}
