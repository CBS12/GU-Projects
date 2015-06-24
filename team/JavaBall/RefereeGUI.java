package JavaBall;



import java.awt.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.event.*;

/*
 * This GUI is only deals a single Referee and takes takes in the open RefereeBank in its
 * constructor
 */
public class RefereeGUI extends JFrame implements ActionListener
{
	//Private instance variables are all the components 
	private JPanel top, bottom;
	private JPanel homeAreaPanel, numMatchesPanel, qualificationPanel, visitAreaPanel;

	private JLabel nameLabel, IDLabel, numMatchesLabel;	
	private JTextField fnameField, lnameField, refIDField, numMatchesField; 
	private JButton updateButton, deleteButton;
	private JComboBox<String> qualificationCombo;
	private JCheckBox northCheckBox, centralCheckBox, southCheckBox;
	private JRadioButton northButton, centralButton, southButton;

	//Instance variables to be used throughout this class.

	private String firstName;
	private String lastName;
	private String ID;
	private boolean isAddition;

	private RefereeBank passedRefBank;


	/*This constructor aims to read in a whole Referee 
	 * from the GUI so that it can be deleted or updated and the 
	 * RefereeBank updated accordingly.	This is used by the search and delete buttons 
	 * of the main GUI
	 */

	public RefereeGUI(Referee ref, RefereeBank refBank) 
	{
		setSize(350,400);
		setLocation(300,100);
		setTitle("Referee's Details");	

		firstName = ref.getRefFName();
		lastName = ref.getRefSName();
		ID = ref.getRefID();
		passedRefBank = refBank;
		isAddition = false; //This is false if not adding a new ref
		layoutComponents(); //Layout general components

		/* This method fills in the details for a known referee, checking the
		 * appropriate boxes on the GUI as required.
		 */
		layoutRefDetails(ref);
	}

	/*This constructor reads in the name of a new Referee 
	 * and the RefereeBank that it has to be added to.	
	 */
	public RefereeGUI(String fName, String sName, String refID , RefereeBank refBank) 
	{

		setSize(350,400);
		setLocation(300,100);
		setTitle("Referee's Details");	
		isAddition = true; //this is true if the ref needs to be added to the array.

		firstName = fName;
		lastName = sName;
		ID = refID;
		passedRefBank = refBank;


		/*Set layout which will take the first and last names and set the components at default or
		 * blank so that they can be filled in.
		 */
		layoutComponents();

		/*This method should fill in the name for a referee, and leave all the other boxes as
		 * blank/ or a default if possible
		 */
		setDefaultValues();
	}


	//This method this fills all boxes bar name and ID as defaults/blanks for all of the rest
	//for the rest of the GUI

	private void setDefaultValues( )
	{

		//Set numMatches blank? possibly set to zero?
		numMatchesField.setText("");

		//Set all the home areas to be blank
		//NB: Could this code be written better??

		northButton.setSelected(false);
		centralButton.setSelected(false);
		centralButton.setSelected(false);

		//Set the qualification area as blank possible? (Don't set??)
		//qualificationCombo.setSelectedItem();


		northCheckBox.setSelected(false);
		centralCheckBox.setSelected(false);
		southCheckBox.setSelected(false);

	}

	//This is a method to fill in all the details of a Referee into the 
	//Referee GUI (hopefully)
	//Names and ID are set through the instance variables, below.

	private void layoutRefDetails(Referee ref)
	{	
		/*
		 * Set the number of matches field from the Referee
		 */
		int numMatches = ref.getNumMatches();

		//Change int to String to display, not sure if this is best way
		String sNumMatches = Integer.toString(numMatches);
		numMatchesField.setText(sNumMatches);
		//The num of matches is non editable for referees already in the bank
		numMatchesField.setEditable(false); 

		/*
		 * Set correct Home area from Referee data
		 */
		if (ref.getHomeArea().equalsIgnoreCase("North"))
		{
			northButton.setSelected(true);

		}
		else if (ref.getHomeArea().equalsIgnoreCase("South"))
		{
			southButton.setSelected(true);

		}
		else if (ref.getHomeArea().equalsIgnoreCase("Central"))
		{
			centralButton.setSelected(true);

		}

		/*
		 * Set correct Qualification from Referee data
		 */
		qualificationCombo.setSelectedItem(ref.getQualification());

		/*
		 * Set correct Visit areas from Referee data
		 */
		northCheckBox.setSelected(ref.canGoNorth());
		centralCheckBox.setSelected(ref.canGoCentral());
		southCheckBox.setSelected(ref.canGoSouth());	

	}

	/**
    Creates the combo box with the qualification choices.
    @return the panel containing the combo box
	 */
	public JPanel createQualificationBox()
	{
		qualificationCombo = new JComboBox<String>();
		//Added a blank so that when we add a new Ref then this has to 
		//be chosen from the list.
		qualificationCombo.addItem("");
		qualificationCombo.addItem("NJB1");
		qualificationCombo.addItem("NJB2");
		qualificationCombo.addItem("NJB3");
		qualificationCombo.addItem("NJB4");
		qualificationCombo.addItem("IJB1");
		qualificationCombo.addItem("IJB2");
		qualificationCombo.addItem("IJB3");
		qualificationCombo.addItem("IJB4");

		qualificationCombo.addActionListener(this);

		JPanel panel = new JPanel();
		panel.add(qualificationCombo);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Qualification"));
		return panel;
	}	    

	/**
    Creates the check boxes for selecting bold and italic styles.
	@return the panel containing the check boxes
	 */
	public JPanel createVisitAreaButtons()
	{
		northCheckBox = new JCheckBox("North");
		northCheckBox.addActionListener(this);

		centralCheckBox = new JCheckBox("Central");
		centralCheckBox.addActionListener(this);

		southCheckBox = new JCheckBox("South");
		southCheckBox.addActionListener(this);

		JPanel panel = new JPanel();
		panel.add(northCheckBox);
		panel.add(centralCheckBox);
		panel.add(southCheckBox);
		panel.setBorder (new TitledBorder(new EtchedBorder(), "Visit Areas"));

		return panel;
	}

	//Action listener events

	public void actionPerformed(ActionEvent e)
	{

		if (e.getSource()==updateButton)
		{	
			//Check the information is sensible before trying to update.
			//Check the home area is chosen
			if (readHomeArea().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please choose a Home Area for your Referee",
						"Error Message", JOptionPane.ERROR_MESSAGE);
				return; //to GUI
			}				

			//Check the qualification level is not blank
			if (readQualLevel().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please choose select an appropriate Qualification Level"
						+ " for this Referee",
						"Error Message", JOptionPane.ERROR_MESSAGE);
				return; //to GUI
			}

			//Check for an integer for match allocation
			try
			{	
				readNumMatches(); 
			}
			catch (NumberFormatException nfx)
			{
				JOptionPane.showMessageDialog(null, "Please enter the number "
						+ "of matches allocated to this Referee", 
						"Error Message", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (readNumMatches() < 0 ){
				JOptionPane.showMessageDialog(null, "Please enter a non-negative number "
						+ "for the number of matches", 
						"Error Message", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (readVisitAreas().equals("NNN"))
			{
				JOptionPane.showMessageDialog(null, "Please choose the Travel Areas this Referee",
						"Error Message", JOptionPane.ERROR_MESSAGE);
				return; //to GUI
			}

			//If the home area and the will travel to buttons don't match
			if ((readHomeArea().equals("North") && !northCheckBox.isSelected()) ||		
					(readHomeArea().equals("South") && !southCheckBox.isSelected()) ||	
					(readHomeArea().equals("Central") && !centralCheckBox.isSelected()))
			{

				JOptionPane.showMessageDialog(null, "The Referee must be willing to travel to his home area,"
						+ " please make an appropriate change",
						"Error Message", JOptionPane.ERROR_MESSAGE);
				return; //to GUI
			}

			//If all okay then process the update

			if(isAddition)
			{				
				passedRefBank.addNewRef(firstName, lastName);	
				processUpdate();
			}
			else
			{
				processUpdate();	
			}

			JOptionPane.showMessageDialog(null,"You have successfully "
					+ "updated Referee's details", "Success", JOptionPane.INFORMATION_MESSAGE);

		}
		else if(e.getSource()==deleteButton)
		{
			int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this referee?", "Confirm",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.OK_OPTION) {
				processDeletion();
			} 		
		}

		//If the home area button is selected this automatically updates the corresponding will
		//visit area.
		if (e.getSource()==northButton)
		{
			northCheckBox.setSelected(true);
			centralCheckBox.setSelected(false);
			southCheckBox.setSelected(false); 
		}

		if (e.getSource()==centralButton)
		{
			centralCheckBox.setSelected(true);
			northCheckBox.setSelected(false);
			southCheckBox.setSelected(false); 
		}

		if (e.getSource()==southButton)
		{
			southCheckBox.setSelected(true);
			centralCheckBox.setSelected(false);
			northCheckBox.setSelected(false); 
		}		
	}

	public String readHomeArea()			
	{
		String homeArea = "";
		if (northButton.isSelected()) 
		{	
			homeArea="North";
		}
		else if (centralButton.isSelected()) 	
		{	

			homeArea="Central";
		}
		else if (southButton.isSelected())
		{
			homeArea="South";
		}

		return homeArea;
	}

	//A Method to return the number of matches by reading from the GUI

	private int readNumMatches()		

	{
		int numMatches =  Integer.parseInt(numMatchesField.getText().trim());	
		return numMatches;
	}

	//A method to return the qualification by reading from the GUI
	private String readQualLevel()	
	{		
		String qualification = (String) qualificationCombo.getSelectedItem();
		return qualification;
	}

	//A method to return the Areas visited by reading the info from the GUI

	private String readVisitAreas()
	{
		String visitArea = "NNN";
		String isNorth = "N";
		String isCentral = "N";
		String isSouth = "N";
		if (northCheckBox.isSelected()) 
		{
			isNorth = "Y";
		}
		if (centralCheckBox.isSelected()) 	
		{	
			isCentral = "Y";
		}
		if (southCheckBox.isSelected())
		{
			isSouth="Y";
		}
		visitArea = isNorth + isCentral + isSouth;
		return visitArea;
	}

	//Deletes a referee to the RefereeBank and then updates the display
	private void processDeletion()
	{
		passedRefBank.deleteRefWithName(firstName, lastName);
		//Update the JavaBallGUI display
		JavaBallGUI.updateDisplay(passedRefBank);
		//Exit the referee GUI 
		this.dispose();	
	}

	//Updates the details referee to the RefereeBank and then updates the display
	private void processUpdate()

	{
		//Check home area details have been entered.		
		String homeArea = readHomeArea();	
		int numMatches = readNumMatches();
		String q = readQualLevel();
		String visitAreas = readVisitAreas();

		String refDetails = ID +" "+firstName+" " +lastName+" "+q+" " +" "+ 
				numMatches+" "+homeArea+" "+" "+visitAreas;

		passedRefBank.updateRefDetails(refDetails, ID); //take out the "" to refID, it is a variable

		//Update the JavaBall GUI
		JavaBallGUI.updateDisplay(passedRefBank);
		this.dispose();
	}

	private void layoutComponents()
	{
		//Code that can layout the skeleton of the components without choosing specific values for 
		//the boxes.

		//top panel is white and contains information about the name and the home area
		top = new JPanel();
		top.setBackground(Color.white);


		//add label for the name to the top panel
		nameLabel = new JLabel("Name");
		top.add(nameLabel);

		//add field for the first name
		fnameField= new JTextField(8);
		top.add(fnameField);
		//set editable to false as we don't want to Edit the name?
		fnameField.setEditable(false);

		//Set the name from the instance variable, this is never editable.
		//This is the same for last name and ID
		fnameField.setText(firstName);

		//add field for the last name
		lnameField= new JTextField(8);
		top.add(lnameField);
		//set editable to false as we don't want to Edit the name?
		lnameField.setEditable(false);
		lnameField.setText(lastName); //set last name text

		//add label for the ID to the top panel
		IDLabel = new JLabel("ID ");
		top.add(IDLabel);

		//add field for the ID
		refIDField= new JTextField(3);
		top.add(refIDField);
		//set editable to false as we don't want to Edit the ID?
		refIDField.setEditable(false);
		refIDField.setText(ID); //Set ID text

		this.add(top, BorderLayout.NORTH);

		//add components to the bottom panel 
		homeAreaPanel = createHomeAreaButtons();
		numMatchesPanel = createNumMatchesField();
		qualificationPanel = createQualificationBox();
		visitAreaPanel = createVisitAreaButtons();


		// Line up component panels
		//KMCL: Should this JPanel be an instance variable?

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(4, 1));
		controlPanel.add(homeAreaPanel);
		controlPanel.add(numMatchesPanel);
		controlPanel.add(qualificationPanel);
		controlPanel.add(visitAreaPanel);


		// Add panels to content pane

		add(controlPanel, BorderLayout.CENTER);


		//middle panel is white and contains 2 button
		bottom = new JPanel();
		bottom.setBackground(Color.white);

		//add buttons to the bottom panel
		//For updates
		updateButton = new JButton("Update");
		updateButton.addActionListener(this);
		bottom.add(updateButton);


		//For delete
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		bottom.add(deleteButton);

		this.add(bottom, BorderLayout.SOUTH);

	}

	/**
    Creates the Field for number of Matches.
    @return the panel containing the field
	 */
	private JPanel createNumMatchesField()
	{
		//add label and a text field for number of matches
		numMatchesLabel = new JLabel("Number of Matches");
		numMatchesField= new JTextField(3);

		JPanel panel = new JPanel();
		panel.add(numMatchesLabel);
		panel.add(numMatchesField);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Number of Matches"));
		return panel;
	}

	/**
    Creates the radio buttons with the home area choices.
    @return the panel containing the radio buttons
	 */
	private JPanel createHomeAreaButtons()
	{
		northButton = new JRadioButton("North");
		northButton.addActionListener(this);

		centralButton = new JRadioButton("Central");
		centralButton.addActionListener(this);

		southButton = new JRadioButton("South");
		southButton.addActionListener(this);

		// Add radio buttons to button group

		ButtonGroup group = new ButtonGroup();
		group.add(northButton);
		group.add(centralButton);
		group.add(southButton);

		JPanel panel = new JPanel();
		panel.add(northButton);
		panel.add(centralButton);
		panel.add(southButton);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Home Area"));
		return panel;
	}

}


