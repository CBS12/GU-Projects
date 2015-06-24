package P_AE1;

/** Main method using a JOptionPane to obtain user name and initial balance.
 * This is passed to a Customer Account object and then to the GUI object
 @author Stewart home*/

import javax.swing.*;

public class AssEx1 {
	public static void main(String[] args){

		// create JOptionPane with dialog to obtain customer name
		String custName = JOptionPane.showInputDialog(null, "Enter the Customer name");
		JOptionPane.showMessageDialog(null, "You entered " + custName, "Result summary", 
				JOptionPane.INFORMATION_MESSAGE);// if some data entered, report it back to user
		if (custName.isEmpty())
		{// otherwise give an error message if a name has not been entered
			JOptionPane.showMessageDialog(null, "No data provided","Result summary", 
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		else{
			//create JOptionPane with dialogue to obtain initial balance
			String startBalance =JOptionPane.showInputDialog(null,"Enter the Initial Balance");

			if (startBalance != null){
				// if some data entered, report it back to user
				JOptionPane.showMessageDialog(null, "You entered £" + startBalance + "",
						"Result summary", JOptionPane.INFORMATION_MESSAGE);
				String b = startBalance.trim();// remove any white space
				// check that valid data has  been entered
				try {
					double initBalance = Double.parseDouble(b);//convert from string to double
					CustomerAccount custModel = new CustomerAccount(custName, initBalance);
					LWMGUI controller = new LWMGUI(custModel);
					controller.setVisible(true);

				}//invalid data raises an error message
				catch (NumberFormatException nfx)	{
					JOptionPane.showMessageDialog(null,  "Enter a Double", b,
							JOptionPane.ERROR_MESSAGE );
					//check for type of variable entered
					JOptionPane.showInputDialog(null,"Enter the initial Balance");
				}
			}
			else{ // otherwise give an error message if data has not been entered
				JOptionPane.showMessageDialog(null, "No data provided", 
						"Result summary", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}

		}

	}
}       	


