package P_AE2;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;

/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener 
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;

	//application instance variables
	//including the 'core' part of the textfile filename
	//some way of indicating whether encoding or decoding is to be done
	private MonoCipher mCipher;
	private LetterFrequencies lFrequencies;
	//private VCipher vCipher;
	private String key, fname, freqReport;
	private char newend, chout;


	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{
		this.setSize(400,150);
		this.setLocation(100,100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}
	//----------------------------------------------------------
	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//top panel is yellow and contains a text field 
		//of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);

		//middle panel is yellow and contains a text field of
		//10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);

		//bottom panel is green and contains 2 buttons

		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}
	//----------------------------------------------------------
	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e) 
	{
		/**  Obtains cipher keyword.If the keyword is invalid, a message is produced*/
		if (!getKeyword()){
			JOptionPane.showMessageDialog(null, 
					"Please enter a valid keyword. Please use "+
					"UPPERCASE only and no repeat letters",
					"Result summary",JOptionPane.ERROR_MESSAGE);
			keyField.setText("");
		}else {
			JOptionPane.showMessageDialog(null,
					"Your keyword is "+ keyField.getText().trim(),
					"Result summary",
					JOptionPane.INFORMATION_MESSAGE);
		}
		/** obtains filename from textfield. requires capital
		  C or P at the end of the string to be valid*/
		if (!processFileName()){
			JOptionPane.showMessageDialog(null,
					"Please enter a valid filename. Please "
					+ "end the filname with 'C' or'P'.", 
					"Result summary",JOptionPane.ERROR_MESSAGE);
			messageField.setText("");
		}
		else {
			JOptionPane.showMessageDialog(null,
					"Your fileName is "+ messageField.getText().trim()
					+".txt", "Result summary",
					JOptionPane.INFORMATION_MESSAGE);
			System.err.println();
		}

		mCipher = new MonoCipher(key);

		//	VCipher vCipher = new VCipher(key,fname);


		boolean vigenere = false;
		if (e.getSource() == monoButton){

			vigenere = false;
		}
		else if (e.getSource() == vigenereButton){

			vigenere = true;
		}
		/**@param getFileName, 
		 * 
		 */
		boolean ok = processFile(getFileName(),vigenere);
		if(!ok) {
			System.err.println("Error processing file.");
		}
	}
	//----------------------------------------------------------	
	/** 
	 * Obtains cipher keyword, valid keyword requires
	 *  -all uppercase and no repeat letters.
	 * If the keyword is invalid, a message is produced
	 * @param String key from the keyword textfield
	 * @return whether a valid keyword was entered	 */
	private boolean getKeyword() 
	{ 
		key = keyField.getText(); // read textfield
		if (key == null) {
			return false;
		}
		if (key.isEmpty()){// no entry in textfield
			return false;
		}
		// checks for repeating characters inn the keyword,
		//working from start and end, working inwards to compare
		//letters
		key = key.trim();
		for (int i=0;i<key.length();i++){
			for (int j=key.length()-1; j>i;j--){
				if (key.charAt(i)==key.charAt(j))
					return false;
			}
			//checks that only uppercase letters are used.
			if (key.charAt(i)< 'A'|| key.charAt(i)>'Z'){
				return false;
			}
		}
		{
			return true;
		}
	}

	//----------------------------------------------------------
	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are 
	 * extracted. If the filename is invalid - not ending with 
	 * C or P, a message is produced. The details obtained from
	 * the filename must be remembered. In addition a file is to 
	 * be created to store the frequency report, ending in F.
	 * @param String fname, name of file from message field
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName() 
	{
		fname = messageField.getText(); 
		if (done()) {
			System.out.println("read filename "+ fname);
		}
		if (fname == null) {
			return false;
		}
		if (fname.isEmpty()){
			return false;
		}

		fname = fname.trim();
		char end = (fname.charAt(fname.length()-1));
		if (end !='P'&& end!= 'C'){
			return false;
		}
		else
		{
			return true;
		}
	}

	//----------------------------------------------------------
	/** Create filename for retrieving input file
	 * @param String fname - file from messageField add".txt"
	 * Final letter of fname to determine encode/decode
	 * ie P means plain to Cipher(encode), 
	 * C means cipher to plain*(decode)
	 * 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * written to the output text file
	 * @param c=boolean checkChars
	 * @param vigenere whether the encoding is
	 *  Vigenere (true) or Mono (false)
	 * @return true if file has processed without erors
	 */

	private boolean processFile(String fname, boolean vigenere)
	{
		fname = messageField.getText();
		if (fname == null || fname.isEmpty()){//check for file name
			System.err.println("File name supplied is not recognised.");
			return false;
		}
		//-----------------------------------------------------
		File inputFile = new File(fname + ".txt");
		if (!inputFile.exists()) {
			System.err.println(inputFile + " does not exist");
		}
		//-----------------------------------------------------
		/**Prepare to read the inputFile. Try/catch written in a helper
		 * method getFileReader to reduce complexity of this section
		 * @param inputFile
		 */

		FileReader reader = getFileReader(inputFile);
		if (reader == null){
			return false;
		}
		//-----------------------------------------------------
		/** Accesses the end character of the inputfile to use in
		 * naming of the outout file. Boolean allows switch between C & P
		 * First outing of the shortened format for (if? then:or )
		 * @param fname
		 * @return outputf, string for filename */
		char end = fname.charAt(fname.length()-1);
		if (end == 'P'){
			newend = 'C';
		}
		else {
			newend = 'P';
		}

		String outputf = (fname.substring(0,fname.length()-1)+ newend +".txt");
		
		//-----------------------------------------------------
		/**Prepare to write to the outputFile. Try/catch written in a helper
		 * method getFileWriter to reduce complexity of this section
		 * @param outputFile
		 */

		File outputFile = new File(outputf) ;
		FileWriter writer = getFileWriter(outputFile);
		if (writer == null) {
			return false;
		}
		//-----------------------------------------------------
		/**Prepare to write to the freqFile. Try/catch written in a helper
		 * method getFileWriter to reduce complexity of this section
		 * @param outputFile
		 */

		File freqFile = new File(fname.substring(0,fname.length()-1)+ "F.txt") ;
		FileWriter freqwriter = getFileWriter(freqFile);
		if (freqwriter == null) {
			return false;
		}
		//-----------------------------------------------------		
		/**process of reading and writing characters 
		 * */

		if (done()){
			System.out.println("Using Monocipher to read "
					+ inputFile +" and to write to "
					+ "output file " + outputFile);
		}
		try {
			LetterFrequencies lFrequencies = new LetterFrequencies(chout);
			int c =0;
			boolean done = false;	
			while (!done) {  //while still more characters to read
				c = reader.read();//read character as integer
				char ch = (char) c; //casts back to character
				if (-1 == c) {
					done = true;
				}
				/** 
				 * decision to encode or decode
				 */
				if (checkChars(ch)){ //check for no lower case letters
					
					if (end == 'P'){
						//ends in P so plain text file to be coded
						chout = mCipher.encode(ch);
						lFrequencies.addChar(chout);
						writer.write(chout);

					}
					else {
						//must end in C so a coded text to
						//be decoded to plain text
						chout = mCipher.decode(ch);
						lFrequencies.addChar(chout);
						writer.write(chout);
					}
				}
				else 
				{
					writer.write(ch);
					//leave in valid characters unencoded
				}

			}

			freqReport = lFrequencies.getReport();
			freqwriter.write(freqReport);
			reader.close();
			writer.close();
			freqwriter.close();
			System.out.println("Finished writing to output file");

		} catch (IOException e) {
			System.err.println("File Error");
			return false;
		}

		return true;
	}

	//------------------------------------------------------------------------
	/**
	 * Helper method for opening files
	 * @param file
	 * @return
	 */
	private FileReader getFileReader (File file) {
		try{
			return new FileReader(file);
		}
		catch (FileNotFoundException e) {
			System.err.println("Error opening file");
			return null;
		}
	}

	//---------------------------------------------------------------------------	
	/**
	 * Helper method for writing to files
	 * @param file
	 * @return
	 */
	private FileWriter getFileWriter(File file) {

		try{
			return new FileWriter(file);
		}
		catch (IOException e) {
			System.err.println("File error");
			return null;
		}
	}

	//----------------------------------------------------------
	/**
	 * General boolean for completing loops
	 * @return
	 */
	private boolean done() {
		return true;
	}
	//----------------------------------------------------------
	/**
	 * boolean to check that no lower case letters are present
	 * @param a
	 * @return
	 */
	private boolean checkChars(char a){
		if (a >= 'A' && a  <= 'Z'){
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//----------------------------------------------------------
	/**
	 * method for obtaining filename
	 * @return
	 */
	private String getFileName() {
		return fname;
	}
	//----------------------------------------------------------

}





