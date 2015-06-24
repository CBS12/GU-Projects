package P_AE1;

/** User interface for Lilybank Wine Merchants. input for customer transactions, 
 * action events on inputs and read out of current balance and transaction details, initial information is called 
via the CustomerAccount class and Wine class. The customer balance is adjusted in Customer Account and called to be 
displayed through the GUI 
@author Stewart home*/

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class LWMGUI extends JFrame implements ActionListener {

	//instance variables 
	private JTextField wineField;
	private JTextField numbotField; 
	private JTextField pricebotField;
	private JTextField transactionField;
	private JTextField currbalanceField;
	private JButton saleButton;
	private JButton returnButton;
	private JPanel top, middle, middleTop, middleBottom, bottom;
	private JLabel wineLabel;
	private JLabel numbottlesLabel;
	private JLabel costbotLabel;
	private JLabel transAmountLabel;
	private JLabel currBalanceLabel;
	private JLabel winePurchased;
	public String wname;
	public int number;
	public double price;
	public CustomerAccount customerObject;



	public LWMGUI(CustomerAccount custModel){
		customerObject = custModel;
		//when the user clicks on the close button, the program exits.
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//set title and size
		setTitle("Lilybank Wine Merchants: " + customerObject.getCustName());
		setSize(550,200);
		//set location
		setLocation(100,100);
		//add components
		layoutComponents();
	}

	//add each panel and components in turn	
	private void layoutComponents(){

		layoutTop();
		layoutMiddle();
		layoutBottom();
	}

	/*create top panel which is white and contains a 3 JLabels 
	 * and 3JTtextFields for wine type, number of bottles and 
	 * price per bottle*/
	private void layoutTop(){
		top = new JPanel();
		top.setBackground(Color.white);
		//create label and field for wine name
		wineLabel = new JLabel("Name:");
		top.add(wineLabel);
		wineField = new JTextField(14);
		wineField.addActionListener(this);
		top.add(wineField);	
		//create label and field for number of bottles
		numbottlesLabel = new JLabel("Quantity:");
		top.add(numbottlesLabel);
		numbotField = new JTextField(8);
		numbotField.addActionListener(this);
		top.add(numbotField);
		//create label and field for price per bottle
		costbotLabel = new JLabel("Price: £");
		top.add(costbotLabel);
		pricebotField = new JTextField(8);
		pricebotField.addActionListener(this);
		top.add(pricebotField);
		//add the top panel to the content pane
		add(top,BorderLayout.NORTH);
	}

	/*create layout of middle panel. The middle panel is
	 *  white, "sale" and "return" JButtons 
	 * and a JLabel returning the wine type transaction.*/
	private void layoutMiddle(){
		middle = new JPanel();
		middle.setLayout(new BorderLayout());
		//create a panel for the JLabel returning the type of wine 
		//to the user pane
		middleBottom = new JPanel();
		middleBottom.setBackground(Color.white);
		//relays info to user re wine type entered
		winePurchased = new JLabel("Wine Purchased: ");
		middleBottom.add(winePurchased);
		middle.add(middleBottom,BorderLayout.SOUTH);
		//create a panel to contain sale and return process JButtons
		//and action listeners
		middleTop = new JPanel();
		middleTop.setBackground(Color.white);
		//sale button
		saleButton = new JButton("Process Sale");
		saleButton.addActionListener(this);
		middleTop.add(saleButton);
		//returns button
		returnButton = new JButton("Process Return");
		returnButton.addActionListener(this);
		middleTop.add(returnButton);
		middle.add(middleTop,BorderLayout.CENTER);						
		//add the middle panel
		add(middle,BorderLayout.CENTER );
	}
	//layout of bottom panel
	private void layoutBottom(){	
		bottom = new JPanel();
		bottom.setBackground(Color.white);
		/*create JLabel and JTextField  for Transaction Amount
		 *  and Customer Balance input from CustomerAccount */
		transAmountLabel = new JLabel("Amount of Transaction:");
		bottom.add(transAmountLabel);
		transactionField = new JTextField(10);//from CustomerAccount
		transactionField.setEditable(false);
		bottom.add(transactionField);	
		currBalanceLabel = new JLabel("Current Balance:£ ");
		bottom.add(currBalanceLabel);
		currbalanceField = new JTextField
				(""+ customerObject.getInitBalance(),10);//from CustomerAccount
		currbalanceField.setEditable(false);
		bottom.add(currbalanceField);
		//add the bottom panel to the content pane
		add(bottom,BorderLayout.SOUTH);
	}

	/*Methods to handle events; collecting information from textfields; 
	 * passing this to a Wine Object which passes the values to Customer Account; 
	 * passing processed information from CustomerAccount to 
	 * textfields for transaction and current balance.  */	
	public void actionPerformed(ActionEvent e){

		//Collect data on winetype from user interface textfield
		String wname = wineField.getText().trim();
		if (wname.isEmpty()){
			// otherwise give an error message if no name entered
			JOptionPane.showMessageDialog(null,"Enter wine name", "Result summary",
					JOptionPane.ERROR_MESSAGE);
			winePurchased.setText("");
		}
		else{
			winePurchased.setText("Wine Purchased: " + wname);//relay info on winetype
		}
		//collect data on number of bottles from user interface textfield
		String n = numbotField.getText().trim();//remove white space
		try {
			number = Integer.parseInt(n);//convert string to integer
		}
		//check for valid data, error message for invalid type
		catch (NumberFormatException nfx)	{
			JOptionPane.showMessageDialog(numbotField,  "Enter an integer", 
					n, JOptionPane.ERROR_MESSAGE );
			numbotField.setText("");
		}	

		//collect data on cost per bottle from textfield 
		String c = pricebotField.getText().trim();//remove white space
		try {
			price = Double.parseDouble(c);//convert from string to double
		}
		//check for valid data, error message for invalid type
		catch (NumberFormatException nfx)	{
			JOptionPane.showMessageDialog(pricebotField,  "Enter a Double", 
					c, JOptionPane.ERROR_MESSAGE );
			pricebotField.setText("");//check for type of variable entered
		}

		//create helper object to input data for wine
		Wine wineModel = new Wine (number, price, wname);

		//action events on JButtons

		//apply correct transaction amount and current balance details for
		//a sale transaction
		if (e.getSource()== saleButton){
			transactionField.setText(String.format(" %.2f", 
					customerObject.saleTransaction(wineModel)));//input to GUI
			currbalanceField.setText
			(String.format(customerObject.saleBalance(wineModel)));//input to GUI
		}

		//apply correct transaction amount and current balance details for a return transaction
		else if (e.getSource()== returnButton){
			transactionField.setText
			(String.format(" %.2f", customerObject.returnTransaction(wineModel)));//input to GUI
			currbalanceField.setText
			(String.format(customerObject.returnBalance(wineModel)));//input to GUI
		}
		//fields in top panel reset to "" after sale/return button pressed.
		this.resetTextFields();
	}
	//methods for resetting top panel textfields after sale/return button pressed
	public void resetTextFields(){
		wineField.setText("");
		numbotField.setText(""); 
		pricebotField.setText("");
	}			

}		














