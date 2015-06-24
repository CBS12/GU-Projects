package P_AE3;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */

public class SportsCentreGUI extends JFrame implements ActionListener {

	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";
	
	private FitnessProgram fp;
	

	//--------------------------------------------------------------------------
	/**
	 * Constructor for AssEx3GUI class
	 * @param fp2 
	 */
	public SportsCentreGUI(FitnessProgram f) {
		fp = f;		
		fp.initData(classesInFile, attendancesFile);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(700, 300);
		display = new JTextArea();
		display.setEditable(false);
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		display.setText(updateDisplay());
		add(display, BorderLayout.CENTER);
		
		layoutTop();
		layoutBottom();
	}
	
	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public String updateDisplay() {
		String text = fp.getInitReport();
		return fp.getInitReport();
	}
	
	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);	
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	
	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	private void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		idIn.setEditable(true);
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}
		//--------------------------------------------------------------------------
	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
		//input from textfields for add/delete processes
		String name = classIn.getText().trim();
		String tutor = tutorIn.getText().trim();
		String id = idIn.getText().trim();
				
		if(ae.getSource() == addButton){
			//calls method to process adding a class
			processAdding(id,name,tutor);
		}
		else if (ae.getSource() == deleteButton){
			//calls method to process delete
			processDeletion(id);
		}
		else if (ae.getSource() == attendanceButton){
			//calls method to display the Attendance report
			displayReport();
		}
		else if (ae.getSource() == closeButton){
			//calls method to save class details to file and close the program 
			processSaveAndClose();
		}

	}
	//--------------------------------------------------------------------------
		/**
		 * Processes adding a class, finds a null class and index using
		 * FitnessProgram methods then replaces this in the first empty
		 * timeslot with the new data.
		 */
		private void processAdding(String d, String n, String t) {
			//calls data from textFields.
			String name = n;
			String tutor = t;
			String id = d;
			fp.addClass(id,name,tutor);//calls a method from FitnessProgram to process data
			
		
			display.setText(fp.getInitReport());//updates the display in the textArea
			this.resetTextFields();//fields in panel reset to "" after add/delete button pressed.
		}
		//--------------------------------------------------------------------------
		/**
		 * Processes deleting a class
		 * * Finds a class using classID and replaces it with an empty class
		 * modification of findClassAtID should help here
		 */
		private void processDeletion(String d) {
			String id = d;//input from textfield
			fp.delete(id);//method in Fitness program to find and remove class
			String newclasses = fp.getInitReport();
			display.setText(newclasses);//update the display
			this.resetTextFields();//fields in panel reset to "" after add/delete button pressed.
		}	
		//--------------------------------------------------------------------------
		/**
		 * Frame for attendance report 	
		 */
		private void displayReport() {
			System.out.println("called display report");
			
			ReportFrame report = new ReportFrame(fp);
		}
		//--------------------------------------------------------------------------
		/**
		 * Writes lines to file representing class name, 
		 * tutor and start time and then exits from the program
		 */
		private void processSaveAndClose() {
			fp.writeData(classesOutFile);//calls a method from FitnessProgram to prepare data for saving
			System.exit(0);
			
		}
	//--------------------------------------------------------------------------
/**
 * methods to reset textfields after the add/delete buttons have been pressed
 */
	
	public void resetTextFields(){
		idIn.setText("");
		classIn.setText(""); 
		tutorIn.setText("");
	}	
	//--------------------------------------------------------------------------
}

