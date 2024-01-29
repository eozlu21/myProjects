package ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import course.Course;
import money.Money;
import schedule.Schedule;
import schedule.ScheduleHelper;
import tutor.Tutor;

/**
 * This panel is only accessible by a Tutor and allows them to create and
 * complete Sessions.
 * 
 * @author egeozlu
 *
 */
public class OrganizeSchedulePanel extends JPanel {

	private BackButton backButton;

	/**
	 * Creates a OrganizeSchedulePanel for the given tutor
	 * 
	 * @param tutor
	 * @see OrganizeSchedulePanel
	 */

	public OrganizeSchedulePanel(Tutor tutor) {
		setLayout(new GridLayout(6, 1));

		backButton = new BackButton();
		String[] columns = { "Time", "Student", "Course", "Cost" };
		DefaultTableModel model = new DefaultTableModel(columns, 0);

		// Create a JTable with the model
		JTable table = new JTable(model);

		// Add some rows to the table
		LinkedHashMap<Integer, LinkedList<String>> scheduleData = tutor.getScheduleInfo();
		for (int hour : Schedule.getHours()) {
			model.addRow(scheduleData.get(hour).toArray());
		}

		JScrollPane scrollPane = new JScrollPane(table);

		JComboBox<Course> teachableCoursesBox = new JComboBox<>(
				tutor.getCourses().toArray(new Course[tutor.getCourses().size()]));
		JTextField moneyInpuTextArea = new JTextField("Enter cost as TL");
		moneyInpuTextArea.setForeground(Color.LIGHT_GRAY);
		moneyInpuTextArea.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				return;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (moneyInpuTextArea.getText().equals("Enter cost as TL")) {
					moneyInpuTextArea.setText("");
					moneyInpuTextArea.setForeground(Color.BLACK);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				return;

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				return;

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				return;
			}
		});

		JButton createSessionButton = new JButton("Create session");
		createSessionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Course course = (Course) teachableCoursesBox.getSelectedItem();

				Money cost = new Money(Integer.parseInt(moneyInpuTextArea.getText()) * 100);

				int hour = Schedule.getHours()[table.getSelectedRow()];

				if (!ScheduleHelper.canCreateSession(tutor, hour, cost, course)) {
					JOptionPane.showMessageDialog(null, "Failed to create session", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				ScheduleHelper.createSession(tutor, hour, cost, course);
				JOptionPane.showMessageDialog(null, "Session created successfully", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				UserInterfaceDataHelper.callOrganizeSchedulePanel(tutor);
			}
		});

		JButton completeSessionButton = new JButton("Complete Session");
		completeSessionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!studentInSession(tutor, table)) {
					JOptionPane.showMessageDialog(null,
							"You cannot complete this session as it isn't attended by a student", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String[] options = { "Pass", "Fail" };

				// Show the option pane
				int result = JOptionPane.showOptionDialog(null, "Select student course status", "Option Pane",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				boolean passed = false;
				// Check the result
				if (result == 0) {
					passed = true;
				} else if (result == 1) {
					passed = false;
				} else {
					return;
				}
				int hour = Schedule.getHours()[table.getSelectedRow()];
				ScheduleHelper.completeSession(tutor, hour, passed);
				UserInterfaceDataHelper.callOrganizeSchedulePanel(tutor);
			}
		});

		add(scrollPane);
		add(teachableCoursesBox);
		add(moneyInpuTextArea);
		add(createSessionButton);
		add(completeSessionButton);
		add(backButton);

	}

	public boolean studentInSession(Tutor tutor, JTable table) {
		try {
			int hour = Schedule.getHours()[table.getSelectedRow()];
			return (tutor.getSessionAtHour(hour).getStudent() != null);
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	public void updatePanelComponents(JTable table, Tutor tutor) {
		String[] columns = { "Time", "Student", "Course", "Cost" };
		DefaultTableModel model = new DefaultTableModel(columns, 0);
		table = new JTable(model);

		// Add some rows to the table
		LinkedHashMap<Integer, LinkedList<String>> scheduleData = tutor.getScheduleInfo();
		for (int hour : Schedule.getHours()) {
			model.addRow(scheduleData.get(hour).toArray());
		}
	}

}
