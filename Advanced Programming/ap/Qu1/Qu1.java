package Qu1;

/**
 * A basic program to play higher/lower game. Basic gui
 * for user to input their guess and to feedback High or Low 
 * and counter to show time remaining.
 * @author 2160196S
 */

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Qu1 extends JFrame implements ActionListener {
	
	private final JButton submitButton, startButton;
	private final JTextField inputField;
	private final JLabel responseLabel, countdownLabel;
	private String message, text;
	private Counter count;
	private int cdown;
	private int userGuess;
	private int target = (int)(50* Math.random());// trying to get an exact integer
	private final Integer GAME_TIME = 30;//max allowed time for game to run is 30 secs
	private final Integer MAX_GUESS = 50;//user to guess to a max of 50
	
	/**
	 * set up GUI
	 */
	public Qu1() 
	{
		super("Game");//title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridBagLayout());
		
		//this will submit player's guess
		submitButton = new JButton("Submit");
		submitButton.setActionCommand("Submit");
		submitButton.addActionListener(this);
		getContentPane().add(submitButton); 
		submitButton.setEnabled(false);
		
		//start will begin the count down
		startButton = new JButton("Start");
		startButton.setActionCommand("Start");
		startButton.addActionListener(this);
		getContentPane().add(startButton);
		startButton.setEnabled(true);
		
		
		//user will input to this field
		inputField = new JTextField(5);
		getContentPane().add(inputField);
		inputField.setEnabled(true);
		
		//this will be where the user is given a response
		responseLabel = new JLabel("  ");
		getContentPane().add(responseLabel);
		
		//this will feature the countdown from 30secs
		countdownLabel = new JLabel(""+ cdown + "secs");
		getContentPane().add(countdownLabel);
		//generate display
		pack();
		setVisible(true);
	}
	//=================================================================================
	/**
	 * action performed - actions arising from buttons
	 */
	public void actionPerformed(ActionEvent e)
	{
		//this starts the countdown when start is clicked
		if(e.getActionCommand()=="Start")
		{
			(count = new Counter()).execute();
			submitButton.setEnabled(true);//allows user to submit the first guess.
		}
		//this starts the guess game interaction by submitting user input
		else if (e.getActionCommand()=="Submit")
		{
			guessGame();//starts processing user input
			startButton.setEnabled(false);//stops counter being reset
		}
	}
	//=================================================================================
	/**
	 * method to compare user input with the randomly generated target, compared
	 * comments on higher/lower sent to gui.
	 */
	private void guessGame()
	{
		System.out.println(""+ target);//useful for easy check
		text = inputField.getText(); //user text from textfield
		userGuess = Integer.parseInt(text);//cast to integer
		//check values are between 0 and MAX_GUESS. A check on validity of user input would be appropriate here.
		if (userGuess < 0 || userGuess > MAX_GUESS)			
		{	//error dialogue and exit to allow user to start again.
			JOptionPane.showMessageDialog(null, "Please enter a number between 0 and 50!",
					"Confirmation", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		if(userGuess != target)//too high or too low
		{
			message = (userGuess > target)? "Too high!  " : "Too low!  ";//create appropriate error message
			responseLabel.setText("" + message);//send text to response label
		}
		else
		{
			count.cancel(true);//correct guess stops the countdown
		}
		return;
	}
	//====================================================================================	

	/**
	 * runs a countdown in the background 
	 * methods return Void and Integer to be published
	 * Publish final comments, out of time or completed time. 
	 */
	public class Counter extends SwingWorker<Void,Integer> 
	{
		public Void doInBackground() 
		{
			try {//countdown from GAME_TIME, 1 sec counter
				for(int cdown = GAME_TIME;cdown >= 0; cdown--) {
					countdownLabel.setText(String.format("%d",cdown));
					Thread.sleep(1000);	// 1 sec interval			
				}
			}
			catch(InterruptedException e) {}			
			return null;
		}	
		//=================================================================================
		public void process (Integer cdown) {
			//countdown from background process 'published' to GUI label
			publish(cdown);
		}
		//=================================================================================
		
		//task completed, appropriate response given
		public void done()
		{
			//get time from label
			int time = Integer.parseInt((countdownLabel.getText()));
			if(time==0)//game finished after time ran out.
			{
				submitButton.setEnabled(false);//disable buttons to stop the game
				System.out.println("Sorry, out of time!");//to console
				responseLabel.setText("Sorry, out of time!");//to GUI
			}
			else
			{//time from label taken from total allowed to get time taken for correct guess
				System.out.println("Well done! Completed in " + (GAME_TIME - time) + " seconds.");// to console
				responseLabel.setText("Well done! Completed in " + (GAME_TIME - time) + " seconds.");//to GUI
			}
			countdownLabel.setText("");//clears counter for text			
		}
	}	
	
//==========================================================================================	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new Qu1();
			}
		});
	}
}


