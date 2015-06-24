package JavaBall;



import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/* 
 * main GUI class for Java Ball Match Allocations 
 * 
 */

public class JavaBallGUI extends JFrame implements ActionListener
{

	// main text area to show all referees
	private static JTextArea display;

	//text fields for search/add/delete button
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;

	//button on the main screen
	private JButton searchButton;
	private JButton addButton;
	private JButton deleteButton;
	private JButton matchAllocButton;
	private JButton chartButton;
	private JButton exitButton;

	/** Names of text files */
	private final String refInFile = "RefereesIn.txt";
	private final String refOutFile = "RefereesOut.txt";
	private final String allocOutFile = "MatchAllocs.txt";
	private RefereeBank refBank; //Used throughout the class
	private RefereeGUI refGUI; 
	private MatchAllocationGUI matchGUI;

	private static Referee [] sortedReferees;
	private static ArrayList<Integer> weeksAllocated;
	private static ArrayList<String> refsAllocated;
	/*
	 * constructor
	 */
	public JavaBallGUI()
	{	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Java Ball Referee Allocation Application");
		setLocation(200,100);
		setSize(850, 460);
		display = new JTextArea(13,5);
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		display.setEditable(false);//making text area non editable
		weeksAllocated=new ArrayList<Integer>();
		refsAllocated = new ArrayList<String>();
		//set layout for top part of the GUI
		layoutCentre();	

		//set layout for bottom screen
		layoutBottom();

		//Sets up the RefereeBank and can be used to display here etc
		initRefDetails(refInFile);

		//Displays the headers in the text area 
		displayHeadings();

		//Update the display as and when required
		updateDisplay(refBank);		
	}

	/*
	 * This method reads a file, line by line  and 
	 * passes each line to the RefereeBank class to add it to the program array.
	 */	

	public void initRefDetails(String inFile)
	{					
		{	
			refBank = new RefereeBank(); //Initialized RefereeBank
			FileReader readF = null; //Initialize fileReader
			try
			{	
				try
				{	// open the file

					readF = new FileReader(inFile);

					// construct a Scanner object from readF
					Scanner rDetails = new Scanner(readF);

					// while there are more referee details to be read
					while (rDetails.hasNextLine()) 
					{
						// read next referee details and puts into refereeD
						String refD = rDetails.nextLine().trim(); 
						//Add ref based on refD
						refBank.addRef(refD); 
					}
					//Close the Scanner object when finished (requested by Eclipse)
					rDetails.close();	
				}		
				finally
				{
					//close file, assuming it was successfully opened
					if (readF!=null) readF.close();
				}
			}
			catch (IOException e) 
			{	
				JOptionPane.showMessageDialog(null, "Unable to read the file, "
						+ "please check that your file exists and try again.",
						"Confirmation", JOptionPane.ERROR_MESSAGE);
				System.exit(1);        // exit abnormally
			}

			//If more than 12 individual referees are entered catch here.
			catch (ArrayIndexOutOfBoundsException abe)
			{
				JOptionPane.showMessageDialog(null, "Too many refrees entered, "
						+ "please edit the referee list and try again.",
						"Confirmation", JOptionPane.ERROR_MESSAGE);
				System.exit(1);        // exit abnormally
			}
		}

	}

	/* 
	 * This method simply displays the headings required for the GUI display. 
	 */
	private static void displayHeadings()
	{
		// appends the data for displaying in text area
		StringBuilder displayText = new StringBuilder();

		// adding headers in text area
		displayText.append(String.format("%-12s", "Referee ID"));
		displayText.append(String.format("%-22s", "Full Name"));
		displayText.append(String.format("%-17s", "Qualification"));
		displayText.append(String.format("%-19s", "Match Allocation"));
		displayText.append(String.format("%-13s", "Home Area"));
		displayText.append(String.format("%-13s", "Travel Areas"));
		displayText.append("\n"); //New line
		String allDetails = displayText.toString(); 
		display.setText(allDetails);	
	}


	/* 
	 * This method takes in the current bank of referees and 
	 * updates the display - 
	 */
	public static void updateDisplay(RefereeBank refBank)
	{

		display.setEditable(false);
		display.setText(""); //Clear the display text

		displayHeadings(); //Redisplay the headings
		display.append("\n"); //New line

		int numRefs = refBank.getNumRefs(); //Get no of Refs in RefBank

		//Sort the refereeBank on IDs as required, using the sort arrays method.

		sortedReferees = refBank.sortReferees();
		//Second loop to add the class names to the display
		for (int i = 0; i < numRefs; i++)	
		{
			//Referee currentRef = refBank.getRefAtPosition(i);	
			String refDetails = sortedReferees[i].getRefDetails();

			//Add these all together and append to the display.
			display.append(refDetails); //Add to same line on display
			display.append("\n"); //New line
		}	
	}

	/* A method to return a String of sorted Referees
	 * The sorting is carried out in the refereeBank
	 * 
	 */

	private String writeReferees() 
	{	
		String referees=""; //Initialise
		Referee [] sortedReferees = refBank.sortReferees(); //An array of sorted Referees from the refBank
		int numRefs = refBank.getNumRefs(); //no. of referees in the array

		for (int i= 0; i < numRefs; i++) //for the whole array get the ref details
		{
			String details = sortedReferees[i].getRefDetails();
			referees +=details+"\n";
		}	
		return referees; //Return the formatted String of Referees
	}

	//A method to return a string containing the match allocation details
	private String writeAllocations()
	{  
		//Write headings
		String allocations=(String.format("%4s %8s %8s     %-20s    %-18s", "Week", "Level",
				"Area", "Referee 1", "Referee2")+"\n"+"\n");


		if (matchGUI==null) //If the allocation GUI has never been opened
		{
			allocations +="No Referees have been allocated to matches this year!";
		}
		else
		{
			

			int numAlloc = refsAllocated.size();

			for (int i= 0; i < numAlloc; i++) //for the whole array get the ref details
			{
				String alloDetails = refsAllocated.get(i);
				allocations +=alloDetails+"\n";
			}	
		}
		return allocations; //Return the formatted String of Referees
	}

	/*
	 * adding gui components to lower section of the frame - first name field, last name field, Search button, add button, 
	 * delete button, chart button, match allocation button & Exit button on to the grid layout. 
	 * 
	 */
	//Q. why do we have a mixture of instance and non-instance variables?

	private void layoutBottom() 
	{
		GridLayout grid = new GridLayout(4, 5);
		grid.setHgap(5);
		grid.setVgap(5);

		JPanel bottomFrame = new JPanel(grid);
		bottomFrame.setBorder(new TitledBorder(new EtchedBorder(),""));// setting border of bottom frame

		// setting first line of the grid
		JLabel firstNameLabel = new JLabel("First Name");
		firstNameLabel.setHorizontalAlignment(JTextField.RIGHT);
		bottomFrame.add(firstNameLabel);
		firstNameTextField = new JTextField();
		bottomFrame.add(firstNameTextField);		
		JLabel lastNameLabel = new JLabel("Last Name");
		lastNameLabel.setHorizontalAlignment(JTextField.RIGHT);
		bottomFrame.add(lastNameLabel);
		lastNameTextField = new JTextField();
		bottomFrame.add(lastNameTextField);
		searchButton = new JButton("Search");
		searchButton.addActionListener(this);
		bottomFrame.add(searchButton);

		// setting second line of the grid		
		bottomFrame.add(new JLabel(""));
		bottomFrame.add(new JLabel(""));
		bottomFrame.add(new JLabel(""));
		bottomFrame.add(new JLabel(""));
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		bottomFrame.add(addButton);

		// setting third line of the grid
		bottomFrame.add(new JLabel(""));
		matchAllocButton = new JButton("Match Allocation");
		matchAllocButton.addActionListener(this);
		bottomFrame.add(matchAllocButton);
		chartButton = new JButton("Display Chart");
		chartButton.addActionListener(this);		
		bottomFrame.add(chartButton);
		bottomFrame.add(new JLabel(""));
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		bottomFrame.add(deleteButton);

		//setting fourth line of the grid
		bottomFrame.add(new JLabel(""));
		bottomFrame.add(new JLabel(""));
		bottomFrame.add(new JLabel(""));
		bottomFrame.add(new JLabel(""));

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);		
		bottomFrame.add(exitButton);

		add(bottomFrame,BorderLayout.SOUTH);
	}


	private void layoutCentre ()
	{
		//panel for adding text area
		JPanel centerPanel = new JPanel();
		centerPanel.setBorder(new TitledBorder(new EtchedBorder(),"Referee Details"));
		centerPanel.add(display);
		add(centerPanel, BorderLayout.CENTER);

	}	

	//Adds a referee to the RefereeBank

	public void processAdding(String fName, String sName)
	{
		refBank.addNewRef(fName, sName);
		return;
	}

	/*
	 *This method writes lines to a file representing the referee details, 
	 * then exits from the program. This uses the writeFile helper method.
	 */
	public void processSaveAndClose() 
	{		
		PrintWriter outputF = null; //Initialize PrintWriter
		PrintWriter outputA = null; //Output for allocations
		try 
		{
			try 
			{
				outputF = new PrintWriter(refOutFile); //open output file
				outputF.print(writeReferees()); //print the written file to the report
				outputA = new PrintWriter(allocOutFile); //open output file
				outputA.print(writeAllocations()); //print the written file to the report

			}
			finally 
			{
				// close the output file assuming it was opened successfully
				if (outputF != null) 
					outputF.close();//close read from file
				if (outputA != null) 
					outputA.close();//close read from file
			}
		}
		//EXCEPTION
		// If there is problem writing to the file tell the user.
		catch (IOException e) 
		{	
			JOptionPane.showMessageDialog(null, "Output error! File not written, please try again",
					"Confirmation", JOptionPane.ERROR_MESSAGE);
		}
		//If successful, tell the user the file has been written
		JOptionPane.showMessageDialog(null, "The current referees and match allocation details have been "
				+ "written to "+refOutFile+" and "+allocOutFile+", respectively",
				"Confirmation", JOptionPane.INFORMATION_MESSAGE);
		System.exit(1); //Finally exit the program
	}

	/*
	 * To check if the textFields are not blank 
	 */
	private boolean validateNameFields(String fName, String sName)
	{ 
		if(fName.equals("") || sName.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Please enter the First Name and "
					+ "Last Name of the Referee","Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		else if(!fName.matches("[A-Za-z]+") || !sName.matches("[A-Za-z]+"))
		{
			JOptionPane.showMessageDialog(null, "Please enter only alphabetic "
					+ "characters for First Name and Last Name","Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		else
		{
			return true;
		}
	}

	/*
	 * A method to check if a name already exists, it returns true if the name if in the refbank, otherwise it returns false.
	 */
	private boolean isCurrentName(String fName, String sName)
	{
		if (refBank.currentName(fName, sName))
		{
			return true;
		}
		else
		{
			return false;
		}
	}





	public void actionPerformed(ActionEvent ae) 

	{	//These are used for several of the buttons so have given them more scope accordingly.
		String firstName = firstNameTextField.getText().trim(); //trimmed to remove all blank spaces.
		String lastName = lastNameTextField.getText().trim();

		// if exit button is pressed
		if(ae.getSource() == exitButton)
		{
			processSaveAndClose();			
		}

		//if the chart button is pressed
		else if(ae.getSource()==chartButton)
		{
			processChart();
		}

		//If the Search or Delete button is pressed
		else if(ae.getSource() == searchButton || ae.getSource() == deleteButton)
		{	
			if (!validateNameFields(firstName, lastName))
			{
				return;
			}
			else if (!isCurrentName(firstName, lastName))
			{
				JOptionPane.showMessageDialog(null, "This is not the name of a current Referee, "
						+ "please check and reenter the name","Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			else  //the name is valid and in use.
			{
				Referee currentRef = refBank.getRefWithName(firstName, lastName); //get ref with name

				//reset the name fields
				resetNameFields();

				//Open the RefereeGui using the details of the currentRef
				refGUI = new RefereeGUI(currentRef, refBank);
				refGUI.setVisible(true);
			}
		}

		//If the add button is pressed
		else if(ae.getSource()== addButton)
		{
			//If not a current name and a valid name
			if (!isCurrentName(firstName, lastName) && validateNameFields(firstName, lastName) && !refBank.isRefBankFull())
			{
				//Add this ref to the array if there is space
				//this.processAdding(firstName, lastName);
				String refID = refBank.generateRefID(firstName, lastName);

				//reset the name fields
				resetNameFields();
				//Open the RefereeGui filling in the first and last name only
				refGUI = new RefereeGUI(firstName, lastName, refID , refBank);
				refGUI.setVisible(true);
			}

			//If the name already exists
			else if (isCurrentName(firstName, lastName))
			{
				JOptionPane.showMessageDialog(null, "This Referee already exists, please enter a new name","Error", JOptionPane.ERROR_MESSAGE);

			}else if (refBank.isRefBankFull()){

				JOptionPane.showMessageDialog(null, "Our refree fulfillment is currently full and "
						+ "no new refrees can currently be entered.", 
						"Confirmation", JOptionPane.ERROR_MESSAGE);
			}

		}

		else if(ae.getSource()== matchAllocButton)
		{
			matchGUI = new MatchAllocationGUI(refBank, weeksAllocated,refsAllocated);
			matchGUI.setVisible(true);
		}	
	}

	//A method to return the number of weeks allocated to matches



	private void resetNameFields()
	{
		firstNameTextField.setText("");
		lastNameTextField.setText("");
	}
	public ArrayList<Integer> getWeeksAllocated()
	{
		return weeksAllocated;
	}
	public ArrayList<String> getRefsAllocated()
	{
		return refsAllocated;
	}

	public static void setWeeksAllocated(ArrayList<Integer> weeksAll)
	{
		weeksAllocated=weeksAll;
	}
	public static void setRefsAllocated(ArrayList<String> RefsAll)
	{
		refsAllocated=RefsAll;
	}
	

	private void processChart ()
	{
		JFrame f = new JFrame();
		f.setSize(400, 300);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//Here we need the integer values to be the number of match allocations.
		int numRefs = refBank.getNumRefs();
		int [] values = new int [numRefs]; // value array to Number of Refs in Bank
		String [] refIDs = new String [numRefs]; //String array to hold IDs
		sortedReferees = refBank.sortReferees(); //sorted by ID version of RefBank

		//For each Referee get the number of matches and ID for the chart.
		for (int i= 0; i < numRefs; i++)
		{
			values [i] = sortedReferees[i].getNumMatches();
			refIDs [i] = sortedReferees[i].getRefID();

		}

		f.getContentPane().add(new Chart(values, refIDs, "Number of Matches", "Referee ID")); 
		f.setVisible(true);
	}

}
