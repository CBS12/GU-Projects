package P_AE3;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
/**
 * Class to define a window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {


	private FitnessProgram fp;
	private JTextArea display;

/**
 * Creates a report frame to display attendance data
 * @param f
 */
	public ReportFrame(FitnessProgram f) {

		fp= f;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 300);
		setTitle("Attendance Report");

		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));

		display.setText(getAttReport());
		add(display, BorderLayout.CENTER);
		setVisible(true);  
	}  
	
	//---------------------------------------------------------------------------------  
	/**
	 * Prepares a report showing class and attendance data, sorted in ascending order by
	 * average weekly attendance
	 * @return
	 */
	public String getAttReport() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID       Class           Tutor                     Attendances                Average Attendance   ");
		sb.append("\n");
		sb.append("================================================================================================"); 	
		sb.append("\n");
		//sorts the array of FitnessClasses by increasing average weekly attendance
		FitnessClass [] aveSortedFC = fp.sortByAverage();
		for (FitnessClass sortFC : aveSortedFC) {
			sb.append(sortFC.toString());
			sb.append("\n");
		}
		sb.append("\n");
		sb.append(String.format("Overall Average:   %7.2f", fp.getTotAverage()));//Prints the overall average attendance.
		
		return sb.toString();

	}
}

