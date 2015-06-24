package JavaBall;


import java.awt.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.event.*;
import java.util.ArrayList;

public class MatchAllocationGUI extends JFrame implements ActionListener 
{
	private JTextArea display;
	private JPanel top, bottom;
	private JPanel areaPanel, levelPanel;
	private JPanel buttonPanel, ref1Panel, ref2Panel;
	private JButton findRefButton;
	private JButton closeButton, saveButton;
	private JLabel weekNumLabel, areaLabel, levelLabel;
	private JLabel ref1Label, ref2Label;
	private JTextField weekNumField;
	private JTextField ref1Field, ref2Field;
	private JComboBox<String> areaCombo, levelCombo;


	//RefereeBank that is passed into this GUI
	private RefereeBank passedRefBank;

	private final int WEEKS = 52; //52 weeks to be assigned or could be changed as necessary

	private int weekNum;
	private String areaMatch;
	private String matchLevel;
	private String ref1Name;
	private String ref2Name;
	
	//Allocation list to keep track of the allocated ref 
	private ArrayList<String> refsAllocated;
	private ArrayList<Integer> weeksAllocated;
	private ArrayList<Referee> suitMatchRefs;
	
	public MatchAllocationGUI(RefereeBank refBank, ArrayList<Integer> weeksAlloc, ArrayList<String> refsAlloc) 
	{		
		setTitle("Match Allocations");
		setSize(500, 400);
		setLocation(300,100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//Use the refBank we are working with as an instance variable
		passedRefBank = refBank;

		//Initialise as the GUI starts up.
		weekNum =0;		//weeksAssigned=0;
		areaMatch ="";
		matchLevel="";
		
		ref1Name="";
		ref2Name="";

		refsAllocated = refsAlloc;
		weeksAllocated = weeksAlloc;
		layoutComponents();	
	}

	public void layoutComponents()
	{
		top = new JPanel(); 

		//add label for the week number to the top panel
		weekNumLabel = new JLabel("Week No ");
		top.add(weekNumLabel);

		//add field for the week number
		weekNumField= new JTextField(3);
		top.add(weekNumField);
		weekNumField.setEditable(true);

		//add label for the area to the top panel
		areaLabel = new JLabel("Area ");
		top.add(areaLabel);

		//add combo box for the area
		areaPanel = createAreaBox();
		top.add(areaPanel);

		//add label for the level to the top panel
		levelLabel = new JLabel("Level ");
		top.add(levelLabel);

		//add combo box for the level
		levelPanel = createlevelBox();
		top.add(levelPanel);

		this.add(top, BorderLayout.NORTH);

		//add components to the center
		buttonPanel = createButton();
		ref1Panel = createReferee1();
		ref2Panel = createReferee2();



		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(3, 1));

		middlePanel.add(buttonPanel);
		middlePanel.add(ref1Panel);
		middlePanel.add(ref2Panel);

		add(middlePanel, BorderLayout.CENTER);



		bottom = new JPanel(new BorderLayout());
		bottom.setBackground(Color.white);
		JPanel textAreaPanel = new JPanel(new BorderLayout());
		textAreaPanel.setBorder(new TitledBorder(new EtchedBorder(),"All Suitable Referees"));

		display = new JTextArea();

		display.setRows(4);
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		display.setEditable(false);//making text area non editable
		textAreaPanel.add(display,BorderLayout.CENTER);
		bottom.add(textAreaPanel,BorderLayout.CENTER);

		
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		saveButton.setEnabled(false);
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		closePanel.add(saveButton);
		closePanel.add(closeButton);
		bottom.add(closePanel,BorderLayout.SOUTH);

		//bottom.setBorder(new TitledBorder(new EtchedBorder(),"All Suitable Referees"));
		this.add(bottom, BorderLayout.SOUTH);


		//		this.add(right,BorderLayout.EAST);

	}

	/**
    Creates the combo box with the area choices.
    @return the panel containing the combo box
	 */
	public JPanel createAreaBox()
	{
		areaCombo = new JComboBox<String>();
		areaCombo.addItem("North");
		areaCombo.addItem("Central");
		areaCombo.addItem("South");

		areaCombo.addActionListener(this);

		JPanel panel = new JPanel();
		panel.add(areaCombo);
		return panel;
	}

	/**
    Creates the combo box with the area choices.
    @return the panel containing the combo box
	 */
	private JPanel createlevelBox()
	{
		levelCombo = new JComboBox<String>();
		levelCombo.addItem("Junior");
		levelCombo.addItem("Senior");

		levelCombo.addActionListener(this);

		JPanel panel = new JPanel();
		panel.add(levelCombo);
		return panel;
	}	  

	private JPanel createButton()
	{

		findRefButton = new JButton("Find Referee");
		findRefButton.addActionListener(this);

		JPanel panel = new JPanel();
		panel.add(findRefButton);
		return panel;
	}

	private JPanel createReferee1()
	{

		//add label an fields for the referee 1

		ref1Label = new JLabel("Allocated Referee 1 ");
		ref1Field= new JTextField(16);
		ref1Field.setEditable(false);		

		JPanel panel = new JPanel();
		panel.add(ref1Label);
		panel.add(ref1Field);
		return panel;

	}

	private JPanel createReferee2()
	{

		//add label an fields for the referee 1

		ref2Label = new JLabel("Allocated Referee 2 ");
		ref2Field= new JTextField(16);
		ref2Field.setEditable(false);		

		JPanel panel = new JPanel();
		panel.add(ref2Label);
		panel.add(ref2Field);
		return panel;

	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==findRefButton)
		{

			try
			{	
				weekNum=readWeekNum(); 
			}
			catch (NumberFormatException nfx)
			{
				JOptionPane.showMessageDialog(null, "Please enter a valid week number, "
						+ "from week 1 to 52", 
						"Error Message", JOptionPane.ERROR_MESSAGE);
				return;

			}

			//If not a sensible week number then throw an exception
			if ((weekNum <= 0) || (weekNum > WEEKS))
			{
				JOptionPane.showMessageDialog(null, "Please enter a valid week number, "
						+ "from week 1 to 52", 
						"Error Message", JOptionPane.ERROR_MESSAGE);
				return;
			}
			areaMatch = readArea();
			matchLevel = readLevel();
			processFindRefs(passedRefBank);
			

		} 
		else if (e.getSource()==saveButton) //add a boolean to say found ref has been called.
		
		{
			
			//Only update the ref bank if the Save button is pressed.
			updateRefMatches();
			addToAllocationList();
			//Update the JavaBallGUI display
			JavaBallGUI.updateDisplay(passedRefBank);
			saveButton.setEnabled(false);
		
		}
		else if(e.getSource()==closeButton)
		{
			
			//Pass weeks allocated to the main GUI
			JavaBallGUI.setWeeksAllocated(weeksAllocated);
			JavaBallGUI.setRefsAllocated(refsAllocated);
			//Close widow
			this.dispose();
		}

	}

	//A method that passes in the current RefereeBank to find suitable Referees.
	private void processFindRefs(RefereeBank refereeBank)

	{
		//New array list for the found Referees
		suitMatchRefs = new ArrayList<Referee>();
		//Get the sorted and suitable list from the refereeBank
		suitMatchRefs = refereeBank.processRefAllocation(matchLevel, areaMatch, weekNum);

		//If there are not two referees then we should inform the user.

		if (suitMatchRefs.size() < 2)
		{
			JOptionPane.showMessageDialog(null, "Unfortunately there are no suitable Referees available for "
					+ "week "+ weekNum+", please try again!", 
					"Information", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		for (int i = 0; i < weeksAllocated.size(); i++ )
		{
			if (weekNum==weeksAllocated.get(i))
			{
				JOptionPane.showMessageDialog(null, "Referees have already been assigned for week "+ weekNum, 
						"Information", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}

		//Allow a single save
		saveButton.setEnabled(true);
		
		//Use this array in the update display class
		updateDisplay();
		
	}
	//reads the week number from the textfield
	private int readWeekNum()
	{
		String s = weekNumField.getText().trim();
		weekNum = Integer.parseInt(s);
		return weekNum;
	}

	private String readArea()
	{
		//records the area in which the match is to be played 
		areaMatch = (String) areaCombo.getSelectedItem();
		return areaMatch;
	}

	private String readLevel()
	{
		//records the level of match to be played
		matchLevel = (String) levelCombo.getSelectedItem();
		return matchLevel;
	}

	/*
	 * This uses a suitably sorted list to update the display (eventually)
	 */

	private void updateDisplay() 
	{
		//Place the cursor at start position

		display.setText("");
		display.setCaretPosition(0);
		display.setEditable(false);
		//Change the tab size
		display.setTabSize(12);

		//Secondly add ALL of the suitable & sorted referee names to the display 
		for (int i = 0; i < suitMatchRefs.size(); i++)	
		{
			//Referee currentRef = refBank.getRefAtPosition(i);	
			String refName = suitMatchRefs.get(i).getFullName()+" "+suitMatchRefs.get(i).getNumMatches();

			//Add these all together and append to the display.
			display.append(refName); //Add to same line on display
			display.append("\n"); //New line	
		}

		/*
		 * Add to the match allocation for these CHOSEN Refs
		 */

		//get number of matches


		ref1Name = suitMatchRefs.get(0).getFullName(); 
		ref2Name = suitMatchRefs.get(1).getFullName();

		
		int ref1matches = suitMatchRefs.get(0).getNumMatches();
		int ref2matches =  suitMatchRefs.get(1).getNumMatches();
		
		ref1matches++;
		ref2matches++; 
		
		//Refs plus their allocation amounts
		
		String ref1plus = ref1Name+ ", " +ref1matches + " matches";
		String ref2plus = ref2Name+ ", " +ref2matches + " matches";

		//Write these names to the referee text boxes.	
		updateRefBoxes(ref1plus, ref2plus);	

	}
	
	private void updateRefMatches()
	{
		//get number of matches
		int ref1Matches = suitMatchRefs.get(0).getNumMatches();
		ref1Matches++;//increase by 1
		
		int ref2Matches = suitMatchRefs.get(1).getNumMatches();
		ref2Matches++;//increase by 1
		
		//Set number of matches with new value			
		suitMatchRefs.get(0).setNumMatches(ref1Matches);
		suitMatchRefs.get(1).setNumMatches(ref2Matches);
			
	}
	

	//The method to update the two chosen ref boxes
	private void updateRefBoxes(String ref1, String ref2)
	{
		ref1Field.setText(ref1);
		ref2Field.setText(ref2);
	}

	//Add a string containing all the ref allocation parameters to an arraylist
	public void addToAllocationList()
	{
		
		refsAllocated.add(String.format("%4d %8s %8s     %-20s    %-18s", weekNum, matchLevel, areaMatch, ref1Name, ref2Name));
		weeksAllocated.add(weekNum);

	}

	public String getAllocationList()
	{
		String allocatedRef = String.format("%4d %8s %8s     %-20s    %-18s", weekNum, matchLevel, areaMatch, ref1Name, ref2Name);
		return allocatedRef;
	}

	//Return the arrayList holding all of the weeks where allocations have been made.

	public ArrayList<Integer> getWeeksAllocated()
	{
		return weeksAllocated;
	}

}

